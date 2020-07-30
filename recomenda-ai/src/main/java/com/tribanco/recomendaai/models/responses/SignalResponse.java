package com.tribanco.recomendaai.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignalResponse {

    private String ticketId;
    private Integer code;
    private String message;
}
