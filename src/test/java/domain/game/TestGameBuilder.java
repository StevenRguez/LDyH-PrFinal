package domain.game;

import domain.player.ImmutablePlayer;
import domain.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Clase que prueba la funcionalidad del constructor del juego (GameBuilder).
 */
class TestGameBuilder {

    /**
     * Prueba que, al crear un juego con un solo jugador, se lance una excepción de estado ilegal.
     */
    @Test
    void cuandoEsCreadoConUnJugador_DebeLanzarError() {
        // Configuración
        var constructorJuego = new GameBuilder()
            .withPlayer("Jugador 1");

        // Verificación
        assertThrows(IllegalStateException.class, constructorJuego::build);
    }

    /**
     * Prueba que, al tener tres jugadores, se pueda construir un juego correctamente.
     */
    @Test
    void cuandoTieneTresJugadores_DebeConstruirJuego() {
        // Acción
        var juego = new GameBuilder()
            .withPlayer("Jugador 1")
            .withPlayer("Jugador 2")
            .withPlayer("Jugador 3")
            .build();

        // Verificación
        var jugadores = juego.getPlayers().toArray(ImmutablePlayer[]::new);

        assertEquals(3, jugadores.length);
        assertEquals("Jugador 1", jugadores[0].getName());
    }
}
