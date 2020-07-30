package com.tribanco.recomendaai.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tribanco.recomendaai.apis.BluAPI;
import com.tribanco.recomendaai.models.ApiReturnsDTO;
import com.tribanco.recomendaai.models.requests.partner.PartnerBodyRequest;
import com.tribanco.recomendaai.models.responses.SignalResponse;
import com.tribanco.recomendaai.models.responses.bluAPI.BluAPIResponse;
import com.tribanco.recomendaai.repositorys.OffersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OffersService {

    @Autowired
    private final BluAPI bluAPI = new BluAPI();

    @Autowired
    private OffersRepository offersRepository;

    @Value("${api.blu.urlBase}")
    private String urlBlu;

    private ObjectMapper mapper = new ObjectMapper();

    public SignalResponse saveSignal(String partnerId, String key, PartnerBodyRequest partnerBodyRequest) throws JsonProcessingException {

        String jsonStrPartnerBodyRequest = mapper.writeValueAsString(partnerBodyRequest);
        String apiRequest = "partnerId: " + partnerId + ", key: " + key + ", body: "  + jsonStrPartnerBodyRequest;

        ApiReturnsDTO apiBluResponse = getBluAPI(partnerId, key, apiRequest);
        offersRepository.save(apiBluResponse);

        SignalResponse signal = new SignalResponse("8d53e81f-10f6-MOCK-9300-be4436c85605", 0, "Sinal recebido com sucesso");

        return signal;
    }

    private ApiReturnsDTO getBluAPI (String partnerId, String key, String apiRequest) throws JsonProcessingException {

        BluAPIResponse bluData = bluAPI.getBluAPI(partnerId, key);
        String jsonStrBluData = mapper.writeValueAsString(bluData);

        return new ApiReturnsDTO(null, "API Blu365", urlBlu + partnerId, apiRequest, jsonStrBluData);
    }

}
