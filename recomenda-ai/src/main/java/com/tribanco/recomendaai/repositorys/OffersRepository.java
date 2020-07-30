package com.tribanco.recomendaai.repositorys;

import com.tribanco.recomendaai.models.ApiReturnsDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OffersRepository extends JpaRepository<ApiReturnsDTO, Long> {


}
