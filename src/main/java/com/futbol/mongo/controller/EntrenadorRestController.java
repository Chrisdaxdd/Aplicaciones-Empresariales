package com.futbol.mongo.controller;

import com.futbol.mongo.model.Entrenador;
import com.futbol.mongo.service.EntrenadorMongoService;
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
@RequestMapping("/api/entrenadores")
@CrossOrigin(origins = "*")
public class EntrenadorRestController {

    private final EntrenadorMongoService entrenadorService;

    @Autowired
    public EntrenadorRestController(EntrenadorMongoService entrenadorService) {
        this.entrenadorService = entrenadorService;
    }

    @GetMapping
    public ResponseEntity<List<Entrenador>> listarTodos() {
        return ResponseEntity.ok(entrenadorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id) {
        Optional<Entrenador> opt = entrenadorService.buscarPorId(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensaje", "Entrenador no encontrado con ID: " + id));
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Entrenador entrenador) {
        Entrenador nuevo = entrenadorService.guardar(entrenador);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable("id") Long id, @Valid @RequestBody Entrenador entrenador) {
        Entrenador actualizado = entrenadorService.actualizar(id, entrenador);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensaje", "No se pudo actualizar: Entrenador no encontrado con ID: " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Long id) {
        boolean eliminado = entrenadorService.eliminar(id);
        if (eliminado) {
            return ResponseEntity.ok(Map.of("mensaje", "Entrenador eliminado exitosamente", "id", id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensaje", "Entrenador no encontrado con ID: " + id));
    }
}
