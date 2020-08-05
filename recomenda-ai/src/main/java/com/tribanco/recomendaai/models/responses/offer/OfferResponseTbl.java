package com.tribanco.recomendaai.models.responses.offer;

import com.tribanco.recomendaai.configs.JsonType;
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
public class OfferResponseTbl {

    @Id
    @Column(name="partner_id")
    private String partnerId;

    @Type(type = "StringJsonObject")
    @Column(name="response_json", columnDefinition = "json")
    private String responseJson;
}
