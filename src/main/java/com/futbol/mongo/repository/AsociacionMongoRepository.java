package com.futbol.mongo.repository;

import com.futbol.mongo.model.Asociacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AsociacionMongoRepository extends MongoRepository<Asociacion, Long> {

    Optional<Asociacion> findBySiglasIgnoreCase(String siglas);
}
