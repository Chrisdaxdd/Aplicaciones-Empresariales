package com.futbol.mongo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Controlador de vistas para el Módulo 2.
 * Despacha las 11 plantillas HTML, las cuales consumen y persisten los datos
 * de forma asincrónica mediante la API REST (JSON / fetch).
 */
@Controller
public class WebViewController {

    @GetMapping({"/", "/index", "/home"})
    public String index() {
        return "index";
    }

    // --- CLUBES ---
    @GetMapping("/clubes")
    public String listarClubes() {
        return "clubes/listar";
    }

    @GetMapping("/clubes/nuevo")
    public String nuevoClub(Model model) {
        model.addAttribute("clubId", null);
        return "clubes/form";
    }

    @GetMapping("/clubes/editar/{id}")
    public String editarClub(@PathVariable("id") Long id, Model model) {
        model.addAttribute("clubId", id);
        return "clubes/form";
    }

    // --- ENTRENADORES ---
    @GetMapping("/entrenadores")
    public String listarEntrenadores() {
        return "entrenadores/listar";
    }

    @GetMapping("/entrenadores/nuevo")
    public String nuevoEntrenador(Model model) {
        model.addAttribute("entrenadorId", null);
        return "entrenadores/form";
    }

    @GetMapping("/entrenadores/editar/{id}")
    public String editarEntrenador(@PathVariable("id") Long id, Model model) {
        model.addAttribute("entrenadorId", id);
        return "entrenadores/form";
    }

    // --- JUGADORES ---
    @GetMapping("/jugadores")
    public String listarJugadores() {
        return "jugadores/listar";
    }

    @GetMapping("/jugadores/nuevo")
    public String nuevoJugador(Model model) {
        model.addAttribute("jugadorId", null);
        return "jugadores/form";
    }

    @GetMapping("/jugadores/editar/{id}")
    public String editarJugador(@PathVariable("id") Long id, Model model) {
        model.addAttribute("jugadorId", id);
        return "jugadores/form";
    }

    // --- ASOCIACIONES ---
    @GetMapping("/asociaciones")
    public String listarAsociaciones() {
        return "asociaciones/listar";
    }

    @GetMapping("/asociaciones/nuevo")
    public String nuevaAsociacion(Model model) {
        model.addAttribute("asociacionId", null);
        return "asociaciones/form";
    }

    @GetMapping("/asociaciones/editar/{id}")
    public String editarAsociacion(@PathVariable("id") Long id, Model model) {
        model.addAttribute("asociacionId", id);
        return "asociaciones/form";
    }

    // --- COMPETICIONES ---
    @GetMapping("/competiciones")
    public String listarCompeticiones() {
        return "competiciones/listar";
    }

    @GetMapping("/competiciones/nuevo")
    public String nuevaCompeticion(Model model) {
        model.addAttribute("competicionId", null);
        return "competiciones/form";
    }

    @GetMapping("/competiciones/editar/{id}")
    public String editarCompeticion(@PathVariable("id") Long id, Model model) {
        model.addAttribute("competicionId", id);
        return "competiciones/form";
    }
}
