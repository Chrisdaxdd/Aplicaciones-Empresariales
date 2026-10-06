package com.futbol.mongo.controller;

import com.futbol.mongo.model.Club;
import com.futbol.mongo.service.ClubMongoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/clubes")
@CrossOrigin(origins = "*")
public class ClubRestController {

    private final ClubMongoService clubService;

    @Autowired
    public ClubRestController(ClubMongoService clubService) {
        this.clubService = clubService;
    }

    @GetMapping
    public ResponseEntity<List<Club>> listarTodos() {
        return ResponseEntity.ok(clubService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id) {
        Optional<Club> opt = clubService.buscarPorId(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensaje", "Club no encontrado con ID: " + id));
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Club club) {
        Club nuevo = clubService.guardar(club);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable("id") Long id, @Valid @RequestBody Club club) {
        Club actualizado = clubService.actualizar(id, club);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensaje", "No se pudo actualizar: Club no encontrado con ID: " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Long id) {
        boolean eliminado = clubService.eliminar(id);
        if (eliminado) {
            return ResponseEntity.ok(Map.of("mensaje", "Club eliminado exitosamente", "id", id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensaje", "Club no encontrado con ID: " + id));
    }

    // Endpoints complementarios para gestionar relaciones NoSQL @DocumentReference
    @PutMapping("/{id}/entrenador/{entrenadorId}")
    public ResponseEntity<?> asignarEntrenador(@PathVariable("id") Long id, @PathVariable("entrenadorId") Long entrenadorId) {
        return clubService.asignarEntrenador(id, entrenadorId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("mensaje", "Club o Entrenador no encontrado")));
    }

    @PostMapping("/{id}/jugadores/{jugadorId}")
    public ResponseEntity<?> asignarJugador(@PathVariable("id") Long id, @PathVariable("jugadorId") Long jugadorId) {
        return clubService.asignarJugador(id, jugadorId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("mensaje", "Club o Jugador no encontrado")));
    }

    @DeleteMapping("/{id}/jugadores/{jugadorId}")
    public ResponseEntity<?> removerJugador(@PathVariable("id") Long id, @PathVariable("jugadorId") Long jugadorId) {
        return clubService.removerJugador(id, jugadorId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("mensaje", "Club no encontrado")));
    }

    @PutMapping("/{id}/asociacion/{asociacionId}")
    public ResponseEntity<?> asignarAsociacion(@PathVariable("id") Long id, @PathVariable("asociacionId") Long asociacionId) {
        return clubService.asignarAsociacion(id, asociacionId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("mensaje", "Club o Asociación no encontrada")));
    }

    @PostMapping("/{id}/competiciones/{competicionId}")
    public ResponseEntity<?> asignarCompeticion(@PathVariable("id") Long id, @PathVariable("competicionId") Long competicionId) {
        return clubService.asignarCompeticion(id, competicionId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("mensaje", "Club o Competición no encontrada")));
    }

    @DeleteMapping("/{id}/competiciones/{competicionId}")
    public ResponseEntity<?> removerCompeticion(@PathVariable("id") Long id, @PathVariable("competicionId") Long competicionId) {
        return clubService.removerCompeticion(id, competicionId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("mensaje", "Club no encontrado")));
    }
}
