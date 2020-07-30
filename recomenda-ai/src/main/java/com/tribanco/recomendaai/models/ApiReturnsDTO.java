package com.tribanco.recomendaai.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_api_returns")
public class ApiReturnsDTO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String apiName;
    private String apiUrl;
    private String requestParameters;
    private String responseJson;
}
