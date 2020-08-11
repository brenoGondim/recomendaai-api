package com.recomendaai.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recomendaai.apis.GenericAPI;
import com.recomendaai.exceptions.customExceptions.EmptyResponseException;
import com.recomendaai.exceptions.customExceptions.TicketNotDoneException;
import com.recomendaai.models.responses.StatusDTO;
import com.recomendaai.models.responses.offer.Extra;
import com.recomendaai.models.responses.offer.OfferResponse;
import com.recomendaai.models.responses.offer.OfferResponseDTO;
import com.recomendaai.models.responses.offer.ResultOffer;
import com.recomendaai.apis.BluAPI;
import com.recomendaai.exceptions.customExceptions.EmptyResponseApiException;
import com.recomendaai.models.responses.ApiReturnsDTO;
import com.recomendaai.models.requests.partner.PartnerBodyRequest;
import com.recomendaai.models.responses.bluAPI.BluAPIResponse;
import com.recomendaai.repositorys.ApisRepository;
import com.recomendaai.repositorys.OffersRepository;
import com.recomendaai.repositorys.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.concurrent.CompletableFuture;

//        31342205200
//        86355171649
//        73998508791
//        07054561295
//        03145687948
//        80922350078
//        43046292805
//        71649352204
@Service
public class OffersService {

    @Value("${api.blu.urlBase}")
    private String urlBlu;
    private final ObjectMapper mapper = new ObjectMapper();
    private final BluAPI bluAPI;
    private final GenericAPI genericAPI;
    private final ApisRepository apisRepository;
    private final OffersRepository offersRepository;
    private final StatusRepository statusRepository;
    Logger log = LoggerFactory.getLogger(OffersService.class);

    @Autowired
    public OffersService(BluAPI bluAPI, GenericAPI genericAPI, ApisRepository apisRepository, OffersRepository offersRepository, StatusRepository statusRepository) {
        this.bluAPI = bluAPI;
        this.genericAPI = genericAPI;
        this.apisRepository = apisRepository;
        this.offersRepository = offersRepository;
        this.statusRepository = statusRepository;
    }

    @Async
    public void saveBluAPI(String partnerId, String key, PartnerBodyRequest partnerBodyRequest, String ticketId) throws JsonProcessingException, EmptyResponseApiException, InterruptedException {

        statusRepository.save(new StatusDTO(ticketId, "PROCESSANDO"));
        log.info("PROCESSANDO POST by " + Thread.currentThread().getName());

        String documentId = partnerBodyRequest.getPayload().getPerson().getDocumentId();
        BluAPIResponse apiResponse = bluAPI.getBluAPI(documentId, key);

        //BluAPIResponse apiResponse = (BluAPIResponse) genericAPI.getAPI(documentId, key, partnerBodyRequest.getCallbackUrl());

        String jsonStrPartnerBodyRequest = mapper.writeValueAsString(partnerBodyRequest);
        String jsonStrBluData = mapper.writeValueAsString(apiResponse);

        ApiReturnsDTO apiBluResponse = new ApiReturnsDTO(ticketId, "API Blu365", urlBlu + partnerId, jsonStrPartnerBodyRequest, jsonStrBluData);
        apisRepository.save(apiBluResponse);
        Thread.sleep(10000);
        OfferResponse offerResponse = mountBluOfferResponse(partnerId, ticketId, partnerBodyRequest, apiResponse);
        String jsonStrOfferResponse = mapper.writeValueAsString(offerResponse);

        OfferResponseDTO offerResponseDTO = new OfferResponseDTO(partnerId, ticketId, documentId, jsonStrOfferResponse);

//        Optional<offerResponseDTO> result = offersRepository.findById(partnerId);
//        result.ifPresent(r -> { throw new IllegalArgumentException(); });                               //ERRO: "Parceiro já cadastrado"

        offersRepository.save(offerResponseDTO);
        log.info("FINALIZADO POST by " + Thread.currentThread().getName());
        statusRepository.save(new StatusDTO(ticketId, "FINALIZADO"));
    }


    private OfferResponse mountBluOfferResponse(String partnerId, String ticketId, PartnerBodyRequest partnerBodyRequest, BluAPIResponse apiResponse){

        List<ResultOffer> results = new ArrayList<>();
        String source = "blu365";
        String title = "Que tal renegociar as dividas com 95% de desconto?";
        String description = "Em nosso parceiro '" + partnerBodyRequest.getPayload().getCompany().getName() + "' você encontra a melhor oferta";

        apiResponse.getData().forEach(data -> results.add(new ResultOffer(partnerId, ticketId, "", source, title, description,"", 0L, null, new Extra(data.getCredor(),
                                                                        data.getUrl(), data.getDocumento(), data.getProduto(), data.getCarteira(), data.getContrato()))));
        return new OfferResponse(results);
    }



    public Page<ResultOffer> getOffer(String partnerId, String key, String ticketId, String offerType, Pageable pageable) throws JsonProcessingException, EmptyResponseException, TicketNotDoneException {

        log.info(Thread.getAllStackTraces().keySet() + "PROCESSANDO GET list by " + Thread.currentThread().getName());

        verifyStatus(ticketId);
        OfferResponse offerResponse = getOfferResponse(ticketId);

        log.info("FINALIZADO GET list by " + Thread.currentThread().getName());

        return pageadOffer(offerResponse, pageable);
    }


    private void verifyStatus(String ticketId) throws TicketNotDoneException {

        Optional<StatusDTO> status = statusRepository.findById(ticketId);

        if (status.isPresent() && "PROCESSANDO".equals(status.get().getDescription())){
            throw new TicketNotDoneException();                                       //ERRO: "O ticket ainda está em processamento. Favor aguardar..."
        }
    }

    private OfferResponse getOfferResponse(String ticketId) throws EmptyResponseException, JsonProcessingException {

        Optional<OfferResponseDTO> offerResponseDTO = offersRepository.findById(ticketId);

        OfferResponse offerResponse = new OfferResponse();

        if (offerResponseDTO.isEmpty() || offerResponseDTO.get().getResponseJson().isEmpty()) {
            throw new EmptyResponseException();                                        //ERRO: "Processamento finalizado. Não foram encontrados registros"
        }
        else if(!offerResponseDTO.get().getResponseJson().isEmpty()){
            offerResponse = mapper.readValue(offerResponseDTO.get().getResponseJson(), OfferResponse.class);
        }
        return offerResponse;
    }

    public Page<ResultOffer> pageadOffer(OfferResponse offerResponse, Pageable pageable) throws EmptyResponseException {

        List<ResultOffer> offersResult = offerResponse.getResults();

        int actualPage = pageable.getPageNumber();
        int totalPerPage = pageable.getPageSize();
        int firstOfPage = actualPage * totalPerPage;
        int lastOfPage = Math.min(firstOfPage + totalPerPage, offersResult.size());

        if((actualPage > 0 &&  offersResult.size() < totalPerPage / actualPage) || firstOfPage > offersResult.size()){
            throw new EmptyResponseException();                                        //ERRO: "Processamento finalizado. Não foram encontrados registros"
        }

        Page<ResultOffer> pageadOffer = new PageImpl<>(offersResult.subList(firstOfPage, lastOfPage), pageable, offersResult.size());

        if(pageadOffer.getContent().isEmpty()){ throw new EmptyResponseException(); }   //ERRO: "Processamento finalizado. Não foram encontrados registros"

        return pageadOffer;
    }
}
