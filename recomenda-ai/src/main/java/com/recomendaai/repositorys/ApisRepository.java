package com.recomendaai.repositorys;

import com.recomendaai.models.responses.ApiReturnsDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApisRepository extends JpaRepository<ApiReturnsDTO, String> {

}
