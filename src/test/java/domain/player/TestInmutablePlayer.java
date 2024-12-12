package domain.player;

import domain.card.Card;
import domain.card.CardColor;
import domain.card.CardType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase ImmutablePlayer.
 */
class TestInmutablePlayer {
    // Test para la clase de Inmutable Player que se encarga de devolver el identificador del jugador.
    @Test
    void testGetId() {
        // Creamos un jugador con un identificador aleatorio.
        var player = new Player("TestPlayer", new HandCardList());
        var immutablePlayer = new ImmutablePlayer(player,0);
        // Comprobamos que el identificador del jugador es el mismo que el del jugador inmutable.
        assertEquals(player.getId(), immutablePlayer.getId());
    }

    // Test para la clase de Inmutable Player que se encarga de devolver el nombre del jugador.
    @Test
    void testGetName() {
        // Creamos un jugador con un nombre.
        var player = new Player("TestPlayer", new HandCardList());
        var immutablePlayer = new ImmutablePlayer(player,0);
        // Comprobamos que el nombre del jugador es el mismo que el del jugador inmutable.
        assertEquals(player.getName(), immutablePlayer.getName());
    }
}
