package com.futbol.mongo.service;

import com.futbol.mongo.model.Asociacion;
import com.futbol.mongo.model.Club;
import com.futbol.mongo.model.Competicion;
import com.futbol.mongo.model.Entrenador;
import com.futbol.mongo.model.Jugador;
import com.futbol.mongo.repository.AsociacionMongoRepository;
import com.futbol.mongo.repository.ClubMongoRepository;
import com.futbol.mongo.repository.CompeticionMongoRepository;
import com.futbol.mongo.repository.EntrenadorMongoRepository;
import com.futbol.mongo.repository.JugadorMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClubMongoService {

    private final ClubMongoRepository clubRepository;
    private final EntrenadorMongoRepository entrenadorRepository;
    private final JugadorMongoRepository jugadorRepository;
    private final AsociacionMongoRepository asociacionRepository;
    private final CompeticionMongoRepository competicionRepository;
    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public ClubMongoService(ClubMongoRepository clubRepository,
                            EntrenadorMongoRepository entrenadorRepository,
                            JugadorMongoRepository jugadorRepository,
                            AsociacionMongoRepository asociacionRepository,
                            CompeticionMongoRepository competicionRepository,
                            SequenceGeneratorService sequenceGenerator) {
        this.clubRepository = clubRepository;
        this.entrenadorRepository = entrenadorRepository;
        this.jugadorRepository = jugadorRepository;
        this.asociacionRepository = asociacionRepository;
        this.competicionRepository = competicionRepository;
        this.sequenceGenerator = sequenceGenerator;
    }

    public List<Club> listarTodos() {
        return clubRepository.findAll();
    }

    public Optional<Club> buscarPorId(Long id) {
        return clubRepository.findById(id);
    }

    public Club guardar(Club club) {
        if (club.getId() == null) {
            club.setId(sequenceGenerator.generateSequence(Club.SEQUENCE_NAME));
        }
        resolverReferencias(club);
        return clubRepository.save(club);
    }

    public Club actualizar(Long id, Club clubActualizado) {
        return clubRepository.findById(id).map(club -> {
            club.setNombre(clubActualizado.getNombre());
            resolverReferencias(clubActualizado);
            club.setEntrenador(clubActualizado.getEntrenador());
            club.setAsociacion(clubActualizado.getAsociacion());
            if (clubActualizado.getJugadores() != null) {
                club.setJugadores(clubActualizado.getJugadores());
            }
            club.setCompeticiones(clubActualizado.getCompeticiones());
            return clubRepository.save(club);
        }).orElse(null);
    }

    private void resolverReferencias(Club club) {
        if (club.getEntrenador() != null && club.getEntrenador().getId() != null) {
            entrenadorRepository.findById(club.getEntrenador().getId()).ifPresent(club::setEntrenador);
        }
        if (club.getAsociacion() != null && club.getAsociacion().getId() != null) {
            asociacionRepository.findById(club.getAsociacion().getId()).ifPresent(club::setAsociacion);
        }
        if (club.getCompeticiones() != null && !club.getCompeticiones().isEmpty()) {
            java.util.List<Competicion> comps = new java.util.ArrayList<>();
            for (Competicion c : club.getCompeticiones()) {
                if (c.getId() != null) {
                    competicionRepository.findById(c.getId()).ifPresent(comps::add);
                }
            }
            club.setCompeticiones(comps);
        }
    }

    public boolean eliminar(Long id) {
        if (clubRepository.existsById(id)) {
            clubRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Club> asignarEntrenador(Long clubId, Long entrenadorId) {
        Optional<Club> clubOpt = clubRepository.findById(clubId);
        Optional<Entrenador> entrenadorOpt = entrenadorRepository.findById(entrenadorId);

        if (clubOpt.isPresent() && entrenadorOpt.isPresent()) {
            Club club = clubOpt.get();
            club.setEntrenador(entrenadorOpt.get());
            return Optional.of(clubRepository.save(club));
        }
        return Optional.empty();
    }

    public Optional<Club> asignarJugador(Long clubId, Long jugadorId) {
        Optional<Club> clubOpt = clubRepository.findById(clubId);
        Optional<Jugador> jugadorOpt = jugadorRepository.findById(jugadorId);

        if (clubOpt.isPresent() && jugadorOpt.isPresent()) {
            Club club = clubOpt.get();
            club.agregarJugador(jugadorOpt.get());
            return Optional.of(clubRepository.save(club));
        }
        return Optional.empty();
    }

    public Optional<Club> removerJugador(Long clubId, Long jugadorId) {
        Optional<Club> clubOpt = clubRepository.findById(clubId);
        if (clubOpt.isPresent()) {
            Club club = clubOpt.get();
            club.getJugadores().removeIf(j -> j.getId().equals(jugadorId));
            return Optional.of(clubRepository.save(club));
        }
        return Optional.empty();
    }

    public Optional<Club> asignarAsociacion(Long clubId, Long asociacionId) {
        Optional<Club> clubOpt = clubRepository.findById(clubId);
        Optional<Asociacion> asociacionOpt = asociacionRepository.findById(asociacionId);

        if (clubOpt.isPresent() && asociacionOpt.isPresent()) {
            Club club = clubOpt.get();
            club.setAsociacion(asociacionOpt.get());
            return Optional.of(clubRepository.save(club));
        }
        return Optional.empty();
    }

    public Optional<Club> asignarCompeticion(Long clubId, Long competicionId) {
        Optional<Club> clubOpt = clubRepository.findById(clubId);
        Optional<Competicion> compOpt = competicionRepository.findById(competicionId);

        if (clubOpt.isPresent() && compOpt.isPresent()) {
            Club club = clubOpt.get();
            club.agregarCompeticion(compOpt.get());
            return Optional.of(clubRepository.save(club));
        }
        return Optional.empty();
    }

    public Optional<Club> removerCompeticion(Long clubId, Long competicionId) {
        Optional<Club> clubOpt = clubRepository.findById(clubId);
        if (clubOpt.isPresent()) {
            Club club = clubOpt.get();
            club.getCompeticiones().removeIf(c -> c.getId().equals(competicionId));
            return Optional.of(clubRepository.save(club));
        }
        return Optional.empty();
    }
}
