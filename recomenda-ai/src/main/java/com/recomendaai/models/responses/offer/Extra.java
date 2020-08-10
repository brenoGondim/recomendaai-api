package com.recomendaai.models.responses.offer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Extra {
    private String credor;
    private String url;
    private String cpf;
    private String produto;
    private String carteira;
    private String contrato;
}
