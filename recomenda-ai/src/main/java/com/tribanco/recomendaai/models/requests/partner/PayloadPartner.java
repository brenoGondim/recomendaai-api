package com.tribanco.recomendaai.models.requests.partner;

import lombok.Data;

@Data
public class PayloadPartner {

    private PersonPartner person;
    private CompanyPartner company;
}
