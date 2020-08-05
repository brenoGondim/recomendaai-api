package com.tribanco.recomendaai.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tribanco.recomendaai.apis.BluAPI;
import com.tribanco.recomendaai.exceptions.customExceptions.EmptyResultApiException;
import com.tribanco.recomendaai.models.responses.ApiReturnsDTO;
import com.tribanco.recomendaai.models.requests.partner.PartnerBodyRequest;
import com.tribanco.recomendaai.models.responses.offer.*;
import com.tribanco.recomendaai.models.responses.SignalResponse;
import com.tribanco.recomendaai.models.responses.bluAPI.BluAPIResponse;
import com.tribanco.recomendaai.repositorys.ApisRepository;
import com.tribanco.recomendaai.repositorys.OffersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OffersService {

    @Value("${api.blu.urlBase}")
    private String urlBlu;
    private final ObjectMapper mapper = new ObjectMapper();
    private final BluAPI bluAPI;
    private final ApisRepository apisRepository;
    private final OffersRepository offersRepository;

    @Autowired
    public OffersService(BluAPI bluAPI, ApisRepository apisRepository, OffersRepository offersRepository) {
        this.bluAPI = bluAPI;
        this.apisRepository = apisRepository;
        this.offersRepository = offersRepository;
    }

    public SignalResponse saveSignal(String partnerId, String key, PartnerBodyRequest partnerBodyRequest) throws JsonProcessingException, EmptyResultApiException {

        String ticketId = UUID.randomUUID().toString();

        BluAPIResponse bluData = bluAPI.getBluAPI(partnerId, key);

        saveBluAPI(partnerId, partnerBodyRequest, bluData, ticketId);

        SignalResponse signal = new SignalResponse(ticketId, 0, "Sinal recebido com sucesso");

        return signal;
    }

    private void saveBluAPI(String partnerId, PartnerBodyRequest partnerBodyRequest, BluAPIResponse bluData, String ticketId) throws JsonProcessingException {

        String jsonStrPartnerBodyRequest = mapper.writeValueAsString(partnerBodyRequest);
        String jsonStrBluData = mapper.writeValueAsString(bluData);

        ApiReturnsDTO apiBluResponse = new ApiReturnsDTO(partnerId, "API Blu365", urlBlu + partnerId, jsonStrPartnerBodyRequest, jsonStrBluData);
        apisRepository.save(apiBluResponse);

        OfferResponse offerResponse = mountBluOfferResponse(ticketId, partnerBodyRequest, bluData);
        String jsonStrOfferResponse = mapper.writeValueAsString(offerResponse);

        OfferResponseTbl offerResponseTbl = new OfferResponseTbl(partnerId, jsonStrOfferResponse);

        Optional<OfferResponseTbl> result = offersRepository.findById(partnerId);
        result.ifPresent(r -> { throw new IllegalArgumentException(); });                               //ERRO: "Parceiro já cadastrado"

        offersRepository.save(offerResponseTbl);
    }

    private OfferResponse mountBluOfferResponse(String ticketId, PartnerBodyRequest partnerBodyRequest, BluAPIResponse apiReturn){

        List<ResultOffer> results = new ArrayList<>();
        String source = "blu365";
        String title = "Que tal renegociar as dividas com 95% de desconto?";
        String description = "Em nosso parceiro '" + partnerBodyRequest.getPayload().getCompany().getName() + "' você encontra a melhor oferta";

        apiReturn.getData().forEach(data -> results.add(new ResultOffer(ticketId, "", source, title, description,"", 0L, null, new Extra(data.getCredor(),
                                                                        data.getUrl(), data.getDocumento(), data.getProduto(), data.getCarteira(), data.getContrato()))));
        return new OfferResponse(results);
    }




    public Page<ResultOffer> getOffer(String partnerId, String key, String ticketId, String offerType, Pageable pageable) throws JsonProcessingException {

        Optional<OfferResponseTbl> offerResponseTbl = offersRepository.findById(partnerId);

        OfferResponse offerResponse = new OfferResponse();

        if(offerResponseTbl.isPresent() && !offerResponseTbl.get().getResponseJson().isEmpty()){
            offerResponse = mapper.readValue(offerResponseTbl.get().getResponseJson(), OfferResponse.class);
        }

        return findPageadOffer(offerResponse, pageable);
    }

    public Page<ResultOffer> findPageadOffer(OfferResponse offerResponse, Pageable pageable){

        List<ResultOffer> offersResult = offerResponse.getResults();

        int actualPage = pageable.getPageNumber();
        int totalPerPage = pageable.getPageSize();
        int firstOfPage = actualPage * totalPerPage;
        int lastOfPage = Math.min(firstOfPage + totalPerPage, offersResult.size());

        return new PageImpl<>(offersResult.subList(firstOfPage, lastOfPage), pageable, offersResult.size());
    }
}
