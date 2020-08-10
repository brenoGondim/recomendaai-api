package com.recomendaai.models.responses.offer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultOffer {
    private String partnerId;
    private String offerId;
    private String offerType;
    private String source;
    private String title;
    private String description;
    private String imageURL;
    private Long offerValue;
    private LocalDateTime expireAt;
    private Extra extra;
}
