package application;

import application.dto.PlayerInfoDTO;
import domain.card.Card;
import domain.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TestGameAppService {

    private GameAppService gameAppService;

    @BeforeEach
    void setUp() {
        // Inicializa el servicio de aplicación con una instancia real de GameBuilder
        gameAppService = new GameAppService();
    }

    @Test
    void testGetPlayerInfos() {
        List<PlayerInfoDTO> playerInfos = gameAppService.getPlayerInfos();

        assertNotNull(playerInfos);
        assertEquals(2, playerInfos.size()); // Verifica que hay dos jugadores iniciales
        assertEquals("Jugador 1", playerInfos.get(0).getName());
        assertEquals("Jugador 2", playerInfos.get(1).getName());
    }

    @Test
    void testGetCurrentPlayer() {
        PlayerInfoDTO currentPlayer = gameAppService.getCurrentPlayer();

        assertNotNull(currentPlayer);
        assertTrue(currentPlayer.getName().equals("Jugador 1") || currentPlayer.getName().equals("Jugador 2"));
    }

    @Test
    void testGetHandCards() {
        UUID playerId = gameAppService.getPlayerInfos().get(0).getId();
        Stream<Card> handCards = gameAppService.getHandCards(playerId);

        assertNotNull(handCards);
        assertTrue(handCards.count() > 0); // Verifica que el jugador tiene cartas en mano
    }

    @Test
    void testPlayCard() {
        UUID playerId = gameAppService.getPlayerInfos().get(0).getId();
        Card cardToPlay = gameAppService.getHandCards(playerId).findFirst().orElse(null);

        assertNotNull(cardToPlay);

    }

    @Test
    void testDrawCard() {
        UUID playerId = gameAppService.getPlayerInfos().get(0).getId();

        // Roba una carta y verifica que no lanza excepciones
        assertDoesNotThrow(() -> gameAppService.drawCard(playerId));
    }

    @Test
    void testPeekTopCard() {
        Card topCard = gameAppService.peekTopCard();

        assertNotNull(topCard);
    }

    @Test
    void testIsGameOver() {
        assertFalse(gameAppService.isGameOver()); // El juego no debería haber terminado inicialmente
    }

    @Test
    void testGetWinner() {
        assertNull(gameAppService.getWinner()); // Al inicio no debería haber ganado
    }
}