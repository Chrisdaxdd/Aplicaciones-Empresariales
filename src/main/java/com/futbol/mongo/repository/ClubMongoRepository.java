package com.futbol.mongo.repository;

import com.futbol.mongo.model.Club;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClubMongoRepository extends MongoRepository<Club, Long> {

    Optional<Club> findByNombreIgnoreCase(String nombre);
}
