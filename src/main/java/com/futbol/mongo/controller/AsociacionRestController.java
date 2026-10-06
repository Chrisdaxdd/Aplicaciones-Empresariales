package com.futbol.mongo.controller;

import com.futbol.mongo.model.Asociacion;
import com.futbol.mongo.service.AsociacionMongoService;
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
@RequestMapping("/api/asociaciones")
@CrossOrigin(origins = "*")
public class AsociacionRestController {

    private final AsociacionMongoService asociacionService;

    @Autowired
    public AsociacionRestController(AsociacionMongoService asociacionService) {
        this.asociacionService = asociacionService;
    }

    @GetMapping
    public ResponseEntity<List<Asociacion>> listarTodos() {
        return ResponseEntity.ok(asociacionService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id) {
        Optional<Asociacion> opt = asociacionService.buscarPorId(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensaje", "Asociación no encontrada con ID: " + id));
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Asociacion asociacion) {
        Asociacion nueva = asociacionService.guardar(asociacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable("id") Long id, @Valid @RequestBody Asociacion asociacion) {
        Asociacion actualizada = asociacionService.actualizar(id, asociacion);
        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensaje", "No se pudo actualizar: Asociación no encontrada con ID: " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Long id) {
        boolean eliminado = asociacionService.eliminar(id);
        if (eliminado) {
            return ResponseEntity.ok(Map.of("mensaje", "Asociación eliminada exitosamente", "id", id));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensaje", "Asociación no encontrada con ID: " + id));
    }
}
