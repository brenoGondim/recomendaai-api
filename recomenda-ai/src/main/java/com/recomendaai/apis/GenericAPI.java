package com.recomendaai.apis;

import com.recomendaai.exceptions.customExceptions.EmptyResponseApiException;
import com.recomendaai.models.responses.bluAPI.BluAPIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class GenericAPI {

    private final RestTemplate restTemplate;
    @Autowired
    public GenericAPI(RestTemplate restTemplate){ this.restTemplate = restTemplate; }

    public Object getAPI(String documentId, String key, String apiUrl) throws EmptyResponseApiException {
        final String url = apiUrl + documentId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("x-api-key", key);

        Optional<Object> response = Optional.ofNullable(restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), Object.class).getBody());

        if (response.isEmpty()) {
            throw new EmptyResponseApiException();                                        //ERRO: "Sem retorno de api parceira"
        }

        return response.orElse(new Object());
    }
}
