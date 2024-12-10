package domain.player;

import application.dto.PlayerInfoDTO;
import domain.card.Card;
import domain.card.CardType;
import domain.player.HandCardList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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

    @Test
    public void givenDifferentIdOrName_shouldNotBeEqual() {
        var player1 = new PlayerInfoDTO(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), "Alice");
        var player2 = new PlayerInfoDTO(UUID.fromString("223e4567-e89b-12d3-a456-426614174000"), "Bob");

        assertNotEquals(player1, player2);
    }
}
