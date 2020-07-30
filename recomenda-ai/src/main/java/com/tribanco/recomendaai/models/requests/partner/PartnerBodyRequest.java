package com.tribanco.recomendaai.models.requests.partner;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PartnerBodyRequest {

    @NotNull
    private String eventType;

    @NotNull
    private String dateTime;

    @NotNull
    private String callbackUrl;

    @NotNull
    private PayloadPartner payload;
}
