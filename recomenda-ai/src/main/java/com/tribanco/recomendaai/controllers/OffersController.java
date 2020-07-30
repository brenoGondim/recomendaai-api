package com.tribanco.recomendaai.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tribanco.recomendaai.constants.ErrorMessages;
import com.tribanco.recomendaai.models.requests.partner.PartnerBodyRequest;
import com.tribanco.recomendaai.models.responses.OfferResponse;
import com.tribanco.recomendaai.models.responses.SignalResponse;
import com.tribanco.recomendaai.services.OffersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class OffersController {

    @Autowired
    OffersService offersService = new OffersService();

    @PostMapping("/signal/{partnerId}")
    public ResponseEntity<SignalResponse> saveSignal(@PathVariable String partnerId, @RequestHeader("x-api-key") String key, @Valid @RequestBody PartnerBodyRequest partnerBodyRequest) throws JsonProcessingException {

        SignalResponse signalResponse = offersService.saveSignal(partnerId, key, partnerBodyRequest);


        return ResponseEntity.ok(signalResponse);
    }



    @GetMapping("/offers/{partnerId}")
    public ResponseEntity<OfferResponse> getOffer(@PathVariable String partnerId, @RequestHeader("x-api-key") String key, @RequestParam(name = "ticketId") String ticketId,
                                                  @RequestParam(name = "offerType") String offerType, @RequestParam(name = "offset") Integer offset,
                                                  @RequestParam(name = "limit") Integer limit){
        return ResponseEntity.ok(new OfferResponse());
    }
}
