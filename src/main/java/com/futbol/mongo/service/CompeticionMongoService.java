package com.futbol.mongo.service;

import com.futbol.mongo.model.Competicion;
import com.futbol.mongo.repository.CompeticionMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompeticionMongoService {

    private final CompeticionMongoRepository competicionRepository;
    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public CompeticionMongoService(CompeticionMongoRepository competicionRepository,
                                   SequenceGeneratorService sequenceGenerator) {
        this.competicionRepository = competicionRepository;
        this.sequenceGenerator = sequenceGenerator;
    }

    public List<Competicion> listarTodos() {
        return competicionRepository.findAll();
    }

    public Optional<Competicion> buscarPorId(Long id) {
        return competicionRepository.findById(id);
    }

    public Competicion guardar(Competicion competicion) {
        if (competicion.getId() == null) {
            competicion.setId(sequenceGenerator.generateSequence(Competicion.SEQUENCE_NAME));
        }
        return competicionRepository.save(competicion);
    }

    public Competicion actualizar(Long id, Competicion actualizado) {
        return competicionRepository.findById(id).map(comp -> {
            comp.setNombre(actualizado.getNombre());
            comp.setMontoPremio(actualizado.getMontoPremio());
            comp.setFechaInicio(actualizado.getFechaInicio());
            comp.setFechaFin(actualizado.getFechaFin());
            return competicionRepository.save(comp);
        }).orElse(null);
    }

    public boolean eliminar(Long id) {
        if (competicionRepository.existsById(id)) {
            competicionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
