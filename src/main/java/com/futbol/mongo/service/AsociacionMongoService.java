package com.futbol.mongo.service;

import com.futbol.mongo.model.Asociacion;
import com.futbol.mongo.repository.AsociacionMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsociacionMongoService {

    private final AsociacionMongoRepository asociacionRepository;
    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public AsociacionMongoService(AsociacionMongoRepository asociacionRepository,
                                  SequenceGeneratorService sequenceGenerator) {
        this.asociacionRepository = asociacionRepository;
        this.sequenceGenerator = sequenceGenerator;
    }

    public List<Asociacion> listarTodos() {
        return asociacionRepository.findAll();
    }

    public Optional<Asociacion> buscarPorId(Long id) {
        return asociacionRepository.findById(id);
    }

    public Asociacion guardar(Asociacion asociacion) {
        if (asociacion.getId() == null) {
            asociacion.setId(sequenceGenerator.generateSequence(Asociacion.SEQUENCE_NAME));
        }
        return asociacionRepository.save(asociacion);
    }

    public Asociacion actualizar(Long id, Asociacion actualizado) {
        return asociacionRepository.findById(id).map(asc -> {
            asc.setNombre(actualizado.getNombre());
            asc.setPais(actualizado.getPais());
            asc.setPresidente(actualizado.getPresidente());
            asc.setSiglas(actualizado.getSiglas());
            return asociacionRepository.save(asc);
        }).orElse(null);
    }

    public boolean eliminar(Long id) {
        if (asociacionRepository.existsById(id)) {
            asociacionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
