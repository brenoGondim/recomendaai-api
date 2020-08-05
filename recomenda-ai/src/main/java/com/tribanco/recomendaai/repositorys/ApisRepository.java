package com.tribanco.recomendaai.repositorys;

import com.tribanco.recomendaai.models.responses.ApiReturnsDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApisRepository extends JpaRepository<ApiReturnsDTO, String> {

}
