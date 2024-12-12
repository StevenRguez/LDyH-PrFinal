package application;

import application.dto.PlayerInfoDTO;
import domain.card.Card;
import domain.player.ImmutablePlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TestIGameAppServiceContract {

    private IGameAppService gameAppService;

    @BeforeEach
    void setUp() {
        // Usar una instancia de la implementación concreta
        gameAppService = new GameAppService();
    }

    @Test
    void testGetPlayerInfos() {
        List<PlayerInfoDTO> playerInfos = gameAppService.getPlayerInfos();

        assertNotNull(playerInfos);
        assertFalse(playerInfos.isEmpty()); // Verifica que hay jugadores en el juego
    }

    @Test
    void testGetCurrentPlayer() {
        PlayerInfoDTO currentPlayer = gameAppService.getCurrentPlayer();

        assertNotNull(currentPlayer);
        assertNotNull(currentPlayer.getName());
        assertNotNull(currentPlayer.getId());
    }

    @Test
    void testGetHandCards() {
        UUID playerId = gameAppService.getPlayerInfos().get(0).getId();
        Stream<Card> handCards = gameAppService.getHandCards(playerId);

        assertNotNull(handCards);
    }

    @Test
    void testPlayCard() {
        UUID playerId = gameAppService.getPlayerInfos().get(0).getId();
        Card card = gameAppService.getHandCards(playerId).findFirst().orElse(null);
        // Verificar que el jugador tiene cartas en mano
        assertNotNull(card);
    }

    @Test
    void testDrawCard() {
        UUID playerId = gameAppService.getPlayerInfos().get(0).getId();

        assertDoesNotThrow(() -> gameAppService.drawCard(playerId));
    }

    @Test
    void testPeekTopCard() {
        Card topCard = gameAppService.peekTopCard();

        assertNotNull(topCard);
    }

    @Test
    void testIsGameOver() {
        boolean isGameOver = gameAppService.isGameOver();

        assertFalse(isGameOver); // Inicialmente el juego no debería haber terminado
    }

    @Test
    void testGetWinner() {
        ImmutablePlayer winner = gameAppService.getWinner();

        assertNull(winner); // Al inicio no hay ganador
    }
}
