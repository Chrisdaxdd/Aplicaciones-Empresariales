package com.futbol.mongo.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "asociaciones")
public class Asociacion {

    @Transient
    public static final String SEQUENCE_NAME = "asociaciones_sequence";

    @Id
    private Long id;

    @NotBlank(message = "El nombre de la asociación es obligatorio")
    private String nombre;

    @NotBlank(message = "El país es obligatorio")
    private String pais;

    @NotBlank(message = "El presidente es obligatorio")
    private String presidente;

    @NotBlank(message = "Las siglas son obligatorias")
    private String siglas;

    public Asociacion() {
    }

    public Asociacion(Long id, String nombre, String pais, String presidente, String siglas) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
        this.presidente = presidente;
        this.siglas = siglas;
    }

    public Asociacion(String nombre, String pais, String presidente, String siglas) {
        this.nombre = nombre;
        this.pais = pais;
        this.presidente = presidente;
        this.siglas = siglas;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getPresidente() {
        return presidente;
    }

    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public String getSiglas() {
        return siglas;
    }

    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    @Override
    public String toString() {
        return "Asociacion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", pais='" + pais + '\'' +
                ", presidente='" + presidente + '\'' +
                ", siglas='" + siglas + '\'' +
                '}';
    }
}
