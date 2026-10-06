package com.futbol.mongo.service;

import com.futbol.mongo.model.Entrenador;
import com.futbol.mongo.repository.EntrenadorMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntrenadorMongoService {

    private final EntrenadorMongoRepository entrenadorRepository;
    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public EntrenadorMongoService(EntrenadorMongoRepository entrenadorRepository,
                                  SequenceGeneratorService sequenceGenerator) {
        this.entrenadorRepository = entrenadorRepository;
        this.sequenceGenerator = sequenceGenerator;
    }

    public List<Entrenador> listarTodos() {
        return entrenadorRepository.findAll();
    }

    public Optional<Entrenador> buscarPorId(Long id) {
        return entrenadorRepository.findById(id);
    }

    public Entrenador guardar(Entrenador entrenador) {
        if (entrenador.getId() == null) {
            entrenador.setId(sequenceGenerator.generateSequence(Entrenador.SEQUENCE_NAME));
        }
        return entrenadorRepository.save(entrenador);
    }

    public Entrenador actualizar(Long id, Entrenador actualizado) {
        return entrenadorRepository.findById(id).map(ent -> {
            ent.setNombre(actualizado.getNombre());
            ent.setApellido(actualizado.getApellido());
            ent.setEdad(actualizado.getEdad());
            ent.setNacionalidad(actualizado.getNacionalidad());
            return entrenadorRepository.save(ent);
        }).orElse(null);
    }

    public boolean eliminar(Long id) {
        if (entrenadorRepository.existsById(id)) {
            entrenadorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
