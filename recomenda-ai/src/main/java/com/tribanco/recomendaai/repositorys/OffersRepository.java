package com.tribanco.recomendaai.repositorys;

import com.tribanco.recomendaai.models.responses.offer.OfferResponseTbl;
import com.tribanco.recomendaai.repositorys.query.OffersRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffersRepository extends JpaRepository<OfferResponseTbl, String>, OffersRepositoryQuery {

}
