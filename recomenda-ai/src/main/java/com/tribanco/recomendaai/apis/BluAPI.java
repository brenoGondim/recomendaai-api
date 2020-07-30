package com.tribanco.recomendaai.apis;

import com.tribanco.recomendaai.models.responses.bluAPI.BluAPIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class BluAPI {

    @Autowired
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${api.blu.urlBase}")
    private String urlBlu;

    public BluAPIResponse getBluAPI(String partnerId, String key) {
        final String url = urlBlu + partnerId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("x-api-key", key);

        BluAPIResponse bluAPIResponse = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), BluAPIResponse.class).getBody();
        return bluAPIResponse;

    }
}
