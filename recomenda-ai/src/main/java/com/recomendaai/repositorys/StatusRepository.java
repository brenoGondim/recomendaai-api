package com.recomendaai.repositorys;

import com.recomendaai.models.responses.StatusDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<StatusDTO, String> {
}
