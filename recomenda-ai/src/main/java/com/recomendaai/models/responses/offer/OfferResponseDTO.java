package com.recomendaai.models.responses.offer;

import com.recomendaai.configs.JsonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeDefs({@TypeDef( name= "StringJsonObject", typeClass = JsonType.class)})
@Entity
@Table(name="tbl_offers")
public class OfferResponseDTO {

    @Column(name="partner_id")
    private String partnerId;

    @Id
    @Column(name="ticket_id")
    private String ticketId;

    @Column(name="document_id")
    private String documentId;

    @Type(type = "StringJsonObject")
    @Column(name="response_json", columnDefinition = "json")
    private String responseJson;
}
