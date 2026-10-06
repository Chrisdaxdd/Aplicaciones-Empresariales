package com.futbol.mongo.service;

import com.futbol.mongo.model.Jugador;
import com.futbol.mongo.repository.JugadorMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JugadorMongoService {

    private final JugadorMongoRepository jugadorRepository;
    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public JugadorMongoService(JugadorMongoRepository jugadorRepository,
                               SequenceGeneratorService sequenceGenerator) {
        this.jugadorRepository = jugadorRepository;
        this.sequenceGenerator = sequenceGenerator;
    }

    public List<Jugador> listarTodos() {
        return jugadorRepository.findAll();
    }

    public Optional<Jugador> buscarPorId(Long id) {
        return jugadorRepository.findById(id);
    }

    public Jugador guardar(Jugador jugador) {
        if (jugador.getId() == null) {
            jugador.setId(sequenceGenerator.generateSequence(Jugador.SEQUENCE_NAME));
        }
        return jugadorRepository.save(jugador);
    }

    public Jugador actualizar(Long id, Jugador actualizado) {
        return jugadorRepository.findById(id).map(j -> {
            j.setNombre(actualizado.getNombre());
            j.setApellido(actualizado.getApellido());
            j.setNumero(actualizado.getNumero());
            j.setPosicion(actualizado.getPosicion());
            return jugadorRepository.save(j);
        }).orElse(null);
    }

    public boolean eliminar(Long id) {
        if (jugadorRepository.existsById(id)) {
            jugadorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
