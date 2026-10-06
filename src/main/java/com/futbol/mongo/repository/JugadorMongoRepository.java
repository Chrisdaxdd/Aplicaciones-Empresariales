package com.futbol.mongo.repository;

import com.futbol.mongo.model.Jugador;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JugadorMongoRepository extends MongoRepository<Jugador, Long> {

    List<Jugador> findByPosicionIgnoreCase(String posicion);
}
