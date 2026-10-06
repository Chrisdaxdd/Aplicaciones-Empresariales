package com.futbol.mongo.controller;

import com.futbol.mongo.model.Competicion;
import com.futbol.mongo.service.CompeticionMongoService;
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
@RequestMapping("/api/competiciones")
@CrossOrigin(origins = "*")
public class CompeticionRestController {

    private final CompeticionMongoService competicionService;

    @Autowired
    public CompeticionRestController(CompeticionMongoService competicionService) {
        this.competicionService = competicionService;
    }

    @GetMapping
    public ResponseEntity<List<Competicion>> listarTodas() {
        return ResponseEntity.ok(competicionService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id) {
        Optional<Competicion> opt = competicionService.buscarPorId(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensaje", "Competición no encontrada con ID: " + id));
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Competicion competicion) {
        Competicion nueva = competicionService.guardar(competicion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable("id") Long id, @Valid @RequestBody Competicion competicion) {
        Competicion actualizada = competicionService.actualizar(id, competicion);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensaje", "No se pudo actualizar: Competición no encontrada con ID: " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Long id) {
        boolean eliminada = competicionService.eliminar(id);
        if (eliminada) {
            return ResponseEntity.ok(Map.of("mensaje", "Competición eliminada exitosamente", "id", id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensaje", "Competición no encontrada con ID: " + id));
    }
}
