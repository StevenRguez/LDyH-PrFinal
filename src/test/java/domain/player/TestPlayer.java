package domain.player;

import domain.card.Card;
import domain.card.CardType;
import domain.player.HandCardList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Player.
 */
class TestPlayer {
    // test para ver q son instancias de la clase, para ver si se crean correctamente
    @Test
    void testPlayer() {
        var handCardList = new HandCardList();
        var player = new Player("TestPlayer", handCardList);
        assertNotNull(player);
    }

    // test para ver si se obtiene el nombre del jugador
    @Test
    void testGetName() {
        var handCardList = new HandCardList();
        var player = new Player("TestPlayer", handCardList);
        assertEquals("TestPlayer", player.getName());
    }

    // test para ver si se obtienen las cartas de la mano
    @Test
    void testGetHandCards() {
        var handCardList = new HandCardList();
        var player = new Player("TestPlayer", handCardList);
        assertNotNull(player.getHandCards());
    }
}
