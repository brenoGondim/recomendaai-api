package com.recomendaai.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.recomendaai.exceptions.customExceptions.TicketNotDoneException;
import com.recomendaai.models.responses.offer.OfferResponseDTO;
import com.recomendaai.exceptions.customExceptions.EmptyResponseApiException;
import com.recomendaai.exceptions.customExceptions.EmptyResponseException;
import com.recomendaai.models.requests.partner.PartnerBodyRequest;
import com.recomendaai.models.responses.SignalResponse;
import com.recomendaai.services.OffersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
public class OffersController {

    private final OffersService offersService;
    @Autowired
    public OffersController(OffersService offersService){ this.offersService = offersService; }

    @PostMapping("/signal/{partnerId}")
    public ResponseEntity<SignalResponse> saveSignal(@PathVariable String partnerId, @RequestHeader("x-api-key") String key,
                                                                @Valid @RequestBody PartnerBodyRequest partnerBodyRequest) throws JsonProcessingException, EmptyResponseApiException, InterruptedException {

        String ticketId = UUID.randomUUID().toString();

        offersService.saveBluAPI(partnerId, key, partnerBodyRequest, ticketId);

        return ResponseEntity.ok(new SignalResponse(ticketId, 0, "Sinal recebido com sucesso"));
    }


    @GetMapping("/offers/{partnerId}")
    public CompletableFuture<ResponseEntity<List<OfferResponseDTO>>> getOffer(@PathVariable String partnerId, @RequestHeader("x-api-key") String key, @RequestParam(name = "ticketId") String ticketId,
                                                                              @RequestParam(name = "offerType", required = false) String offerType, Pageable pageable) throws JsonProcessingException, EmptyResponseException, TicketNotDoneException {

        CompletableFuture<List<OfferResponseDTO>> offerResponse = offersService.getOffer(partnerId, key, ticketId, offerType, pageable);


        return offerResponse.thenApplyAsync(ResponseEntity::ok);

        //return ResponseEntity.ok(offerResponse);
    }
}
