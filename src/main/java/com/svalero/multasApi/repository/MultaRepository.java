package com.svalero.multasApi.repository;

import com.svalero.multasApi.domain.Multa;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultaRepository extends ReactiveMongoRepository<Multa, String> {
}
