package com.recomendaai.repositorys;

import com.recomendaai.repositorys.query.OffersRepositoryQuery;
import com.recomendaai.models.responses.offer.OfferResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffersRepository extends JpaRepository<OfferResponseDTO, String>, OffersRepositoryQuery {

}
