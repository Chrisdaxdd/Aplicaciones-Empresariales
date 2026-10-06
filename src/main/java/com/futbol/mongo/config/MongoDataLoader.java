package com.futbol.mongo.config;

import com.futbol.mongo.model.Asociacion;
import com.futbol.mongo.model.Club;
import com.futbol.mongo.model.Competicion;
import com.futbol.mongo.model.Entrenador;
import com.futbol.mongo.model.Jugador;
import com.futbol.mongo.service.AsociacionMongoService;
import com.futbol.mongo.service.ClubMongoService;
import com.futbol.mongo.service.CompeticionMongoService;
import com.futbol.mongo.service.EntrenadorMongoService;
import com.futbol.mongo.service.JugadorMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class MongoDataLoader implements CommandLineRunner {

    private final ClubMongoService clubService;
    private final EntrenadorMongoService entrenadorService;
    private final JugadorMongoService jugadorService;
    private final AsociacionMongoService asociacionService;
    private final CompeticionMongoService competicionService;

    @Autowired
    public MongoDataLoader(ClubMongoService clubService,
                           EntrenadorMongoService entrenadorService,
                           JugadorMongoService jugadorService,
                           AsociacionMongoService asociacionService,
                           CompeticionMongoService competicionService) {
        this.clubService = clubService;
        this.entrenadorService = entrenadorService;
        this.jugadorService = jugadorService;
        this.asociacionService = asociacionService;
        this.competicionService = competicionService;
    }

    @Override
    public void run(String... args) {
        try {
            if (!asociacionService.listarTodos().isEmpty()) {
                return;
            }

            // 1. Asociaciones
            Asociacion fcf = asociacionService.guardar(
                    new Asociacion("Federación Colombiana de Fútbol", "Colombia", "Ramón Jesurún", "FCF"));
            Asociacion conmebol = asociacionService.guardar(
                    new Asociacion("Confederación Sudamericana de Fútbol", "Paraguay", "Alejandro Domínguez", "CONMEBOL"));

            // 2. Competiciones
            Competicion liga = competicionService.guardar(
                    new Competicion("Liga BetPlay Dimayor", 500000.0, LocalDate.of(2026, 1, 20), LocalDate.of(2026, 6, 25)));
            Competicion libertadores = competicionService.guardar(
                    new Competicion("Copa CONMEBOL Libertadores", 23000000.0, LocalDate.of(2026, 2, 6), LocalDate.of(2026, 11, 30)));

            // 3. Entrenadores
            Entrenador gamero = entrenadorService.guardar(
                    new Entrenador("Alberto", "Gamero", 60, "Colombiana"));
            Entrenador peirano = entrenadorService.guardar(
                    new Entrenador("Pablo", "Peirano", 49, "Uruguaya"));

            // 4. Jugadores
            Jugador j1 = jugadorService.guardar(new Jugador("Radamel", "Falcao", 9, "Delantero"));
            Jugador j2 = jugadorService.guardar(new Jugador("David", "Silva", 14, "Mediocampista"));
            Jugador j3 = jugadorService.guardar(new Jugador("Hugo", "Rodallega", 11, "Delantero"));

            // 5. Clubes con @DocumentReference
            Club millonarios = new Club("Millonarios FC");
            millonarios.setEntrenador(gamero);
            millonarios.setAsociacion(fcf);
            millonarios.getCompeticiones().addAll(List.of(liga, libertadores));
            millonarios.getJugadores().addAll(List.of(j1, j2));
            clubService.guardar(millonarios);

            Club santafe = new Club("Independiente Santa Fe");
            santafe.setEntrenador(peirano);
            santafe.setAsociacion(fcf);
            santafe.getCompeticiones().addAll(List.of(liga));
            santafe.getJugadores().addAll(List.of(j3));
            clubService.guardar(santafe);

        } catch (Exception e) {
            // Manejo preventivo si la conexión remota a Mongo tiene timeout o latencia
            System.err.println("Aviso DataLoader MongoDB: " + e.getMessage());
        }
    }
}
