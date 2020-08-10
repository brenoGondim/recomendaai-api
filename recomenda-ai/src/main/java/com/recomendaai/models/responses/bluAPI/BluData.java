package com.recomendaai.models.responses.bluAPI;

import lombok.Data;

@Data
public class BluData {

    private String nome;
    private String contrato;
    private String documento;
    private String url;
    private String credor;
    private String carteira;
    private String produto;
    private String valor_divida;
}
