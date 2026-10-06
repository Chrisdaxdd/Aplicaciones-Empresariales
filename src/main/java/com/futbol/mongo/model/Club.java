package com.futbol.mongo.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo de documento MongoDB para Club.
 * Utiliza @DocumentReference para mantener las referencias a las otras colecciones,
 * modelando asociaciones normalizadas en bases de datos NoSQL sin anidar duplicados.
 */
@Document(collection = "clubes")
public class Club {

    @Transient
    public static final String SEQUENCE_NAME = "clubes_sequence";

    @Id
    private Long id;

    @NotBlank(message = "El nombre del club es obligatorio")
    private String nombre;

    /**
     * Referencia a la colección 'entrenadores'
     */
    @DocumentReference
    private Entrenador entrenador;

    /**
     * Referencias a la colección 'jugadores'
     */
    @DocumentReference
    private List<Jugador> jugadores = new ArrayList<>();

    /**
     * Referencia a la colección 'asociaciones'
     */
    @DocumentReference
    private Asociacion asociacion;

    /**
     * Referencias a la colección 'competiciones'
     */
    @DocumentReference
    private List<Competicion> competiciones = new ArrayList<>();

    public Club() {
    }

    public Club(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Club(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores != null ? jugadores : new ArrayList<>();
    }

    public Asociacion getAsociacion() {
        return asociacion;
    }

    public void setAsociacion(Asociacion asociacion) {
        this.asociacion = asociacion;
    }

    public List<Competicion> getCompeticiones() {
        return competiciones;
    }

    public void setCompeticiones(List<Competicion> competiciones) {
        this.competiciones = competiciones != null ? competiciones : new ArrayList<>();
    }

    public void agregarJugador(Jugador jugador) {
        if (jugadores == null) {
            jugadores = new ArrayList<>();
        }
        if (!jugadores.contains(jugador)) {
            jugadores.add(jugador);
        }
    }

    public void removerJugador(Jugador jugador) {
        if (jugadores != null) {
            jugadores.remove(jugador);
        }
    }

    public void agregarCompeticion(Competicion competicion) {
        if (competiciones == null) {
            competiciones = new ArrayList<>();
        }
        if (!competiciones.contains(competicion)) {
            competiciones.add(competicion);
        }
    }

    public void removerCompeticion(Competicion competicion) {
        if (competiciones != null) {
            competiciones.remove(competicion);
        }
    }

    @Override
    public String toString() {
        return "Club{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
