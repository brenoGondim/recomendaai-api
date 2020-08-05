package com.tribanco.recomendaai.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tribanco.recomendaai.exceptions.customExceptions.EmptyResultApiException;
import com.tribanco.recomendaai.models.requests.partner.PartnerBodyRequest;
import com.tribanco.recomendaai.models.responses.SignalResponse;
import com.tribanco.recomendaai.models.responses.offer.ResultOffer;
import com.tribanco.recomendaai.services.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class OffersController {

    private final OffersService offersService;
    @Autowired
    public OffersController(OffersService offersService){ this.offersService = offersService; }


    @PostMapping("/signal/{partnerId}")
    public ResponseEntity<SignalResponse> saveSignal(@PathVariable String partnerId, @RequestHeader("x-api-key") String key,
                                                     @Valid @RequestBody PartnerBodyRequest partnerBodyRequest) throws JsonProcessingException, EmptyResultApiException {

        SignalResponse signalResponse = offersService.saveSignal(partnerId, key, partnerBodyRequest);

        return signalResponse != null ? ResponseEntity.ok(signalResponse) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping("/offers/{partnerId}")
    public ResponseEntity<Page<ResultOffer>> getOffer(@PathVariable String partnerId, @RequestHeader("x-api-key") String key, @RequestParam(name = "ticketId") String ticketId,
                                                        @RequestParam(name = "offerType", required = false) String offerType, Pageable pageable) throws JsonProcessingException {

        Page<ResultOffer> offerResponse = offersService.getOffer(partnerId, key, ticketId, offerType, pageable);

        return offerResponse != null ? ResponseEntity.ok(offerResponse) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
