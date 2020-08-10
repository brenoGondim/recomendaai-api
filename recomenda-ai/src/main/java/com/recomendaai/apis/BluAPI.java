package com.recomendaai.apis;

import com.recomendaai.exceptions.customExceptions.EmptyResponseApiException;
import com.recomendaai.models.responses.bluAPI.BluAPIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Component
public class BluAPI {

    private final RestTemplate restTemplate;
    @Autowired
    public BluAPI(RestTemplate restTemplate){ this.restTemplate = restTemplate; }

    @Value("${api.blu.urlBase}")
    private String urlBlu;


    public BluAPIResponse getBluAPI(String partnerId, String key) throws EmptyResponseApiException, InterruptedException {
        final String url = urlBlu + partnerId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("x-api-key", key);

        Optional<BluAPIResponse> bluAPIResponse = Optional.ofNullable(restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), BluAPIResponse.class).getBody());

        if (bluAPIResponse.isPresent() && bluAPIResponse.get().getData().isEmpty()) {
            throw new EmptyResponseApiException();                                        //ERRO: "Sem retorno de api parceira"
        }

        return bluAPIResponse.orElse(new BluAPIResponse());
    }
}
