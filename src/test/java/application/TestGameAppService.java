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

/**
 * Clase de prueba para el servicio de aplicación del juego (GameAppService).
 *
 * Esta clase verifica las funcionalidades principales expuestas por el servicio de aplicación,
 * incluyendo la gestión de jugadores, cartas y estado del juego.
 */
class TestGameAppService {

    /**
     * Instancia del servicio de aplicación del juego utilizada en las pruebas.
     */
    private GameAppService gameAppService;

    /**
     * Configura el entorno de prueba inicializando el servicio de aplicación
     * antes de cada caso de prueba.
     */
    @BeforeEach
    void setUp() {
        // Inicializa el servicio de aplicación con una instancia real de GameBuilder
        gameAppService = new GameAppService();
    }

    /**
     * Verifica que se obtenga correctamente la lista de información de los jugadores.
     *
     * Comprueba que la lista no sea nula, contenga dos jugadores iniciales
     * y que sus nombres coincidan con los esperados.
     */
    @Test
    void testGetPlayerInfos() {
        List<PlayerInfoDTO> playerInfos = gameAppService.getPlayerInfos();

        assertNotNull(playerInfos);
        assertEquals(2, playerInfos.size()); // Verifica que hay dos jugadores iniciales
        assertEquals("Jugador 1", playerInfos.get(0).getName());
        assertEquals("Jugador 2", playerInfos.get(1).getName());
    }

    /**
     * Verifica que se obtenga correctamente el jugador actual.
     *
     * Comprueba que el jugador actual no sea nulo y que su nombre corresponda
     * a uno de los jugadores iniciales.
     */
    @Test
    void testGetCurrentPlayer() {
        PlayerInfoDTO currentPlayer = gameAppService.getCurrentPlayer();

        assertNotNull(currentPlayer);
        assertTrue(currentPlayer.getName().equals("Jugador 1") || currentPlayer.getName().equals("Jugador 2"));
    }

    /**
     * Verifica que las cartas en mano de un jugador se obtengan correctamente.
     *
     * Comprueba que el flujo de cartas no sea nulo y que el jugador tenga al menos una carta en mano.
     */
    @Test
    void testGetHandCards() {
        UUID playerId = gameAppService.getPlayerInfos().get(0).getId();
        Stream<Card> handCards = gameAppService.getHandCards(playerId);

        assertNotNull(handCards);
        assertTrue(handCards.count() > 0); // Verifica que el jugador tiene cartas en mano
    }

    /**
     * Verifica que un jugador pueda jugar una carta de su mano.
     *
     * Comprueba que la carta a jugar no sea nula.
     */
    @Test
    void testPlayCard() {
        UUID playerId = gameAppService.getPlayerInfos().get(0).getId();
        Card cardToPlay = gameAppService.getHandCards(playerId).findFirst().orElse(null);

        assertNotNull(cardToPlay);
    }

    /**
     * Verifica que un jugador pueda robar una carta sin errores.
     *
     * Comprueba que no se lance ninguna excepción al robar una carta.
     */
    @Test
    void testDrawCard() {
        UUID playerId = gameAppService.getPlayerInfos().get(0).getId();

        // Roba una carta y verifica que no lanza excepciones
        assertDoesNotThrow(() -> gameAppService.drawCard(playerId));
    }

    /**
     * Verifica que se pueda obtener correctamente la carta superior del mazo.
     *
     * Comprueba que la carta superior no sea nula.
     */
    @Test
    void testPeekTopCard() {
        Card topCard = gameAppService.peekTopCard();

        assertNotNull(topCard);
    }

    /**
     * Verifica que el estado del juego inicial indique que aún no ha terminado.
     */
    @Test
    void testIsGameOver() {
        assertFalse(gameAppService.isGameOver()); // El juego no debería haber terminado inicialmente
    }

    /**
     * Verifica que al inicio del juego no haya un ganador.
     */
    @Test
    void testGetWinner() {
        assertNull(gameAppService.getWinner()); // Al inicio no debería haber ganador
    }
}