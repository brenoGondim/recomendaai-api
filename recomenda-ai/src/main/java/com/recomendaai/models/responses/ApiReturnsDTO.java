package com.recomendaai.models.responses;

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
@Table(name="tbl_api")
public class ApiReturnsDTO {

    @Id
    @Column(name="ticket_id")
    private String ticketId;

    @Column(name="name")
    private String name;

    @Column(name="url")
    private String url;

    @Type(type = "StringJsonObject")
    @Column(name="request_json", columnDefinition = "json")
    private String requestJson;

    @Type(type = "StringJsonObject")
    @Column(name="response_json", columnDefinition = "json")
    private String responseJson;
}
