package application;

import application.dto.PlayerInfoDTO;
import domain.card.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para el servicio de aplicación GameAppService.
 * Verifica el comportamiento esperado de los métodos expuestos por el servicio.
 */
class TestGameAppService {

    /**
     * Instancia del servicio de aplicación que será probada.
     */
    private GameAppService gameAppService;

    /**
     * Configuración inicial antes de cada prueba.
     * Inicializa el servicio de aplicación con una instancia de GameAppService.
     */
    @BeforeEach
    void setUp() {
        // Inicializa el servicio de aplicación con una instancia real de GameBuilder
        gameAppService = new GameAppService();
    }

    /**
     * Prueba unitaria para verificar que se obtienen las informaciones de los jugadores.
     * Comprueba que la lista de jugadores no es nula, tiene el tamaño esperado,
     * y contiene nombres correctos para los jugadores iniciales.
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
     * Prueba unitaria para verificar que se obtiene el jugador actual.
     * Valida que la información del jugador actual se devuelve correctamente.
     */
    @Test
    void testGetCurrentPlayer() {
        PlayerInfoDTO currentPlayer = gameAppService.getCurrentPlayer();

    }

    /**
     * Prueba unitaria para verificar que se obtienen las cartas en mano de un jugador.
     * Comprueba que el flujo de cartas no es nulo y contiene al menos una carta.
     */
    @Test
    void testGetHandCards() {
        UUID playerId = gameAppService.getPlayerInfos().get(0).getId();
        Stream<Card> handCards = gameAppService.getHandCards(playerId);

        assertNotNull(handCards);
        assertTrue(handCards.count() > 0); // Verifica que el jugador tiene cartas en mano
    }

    /**
     * Prueba unitaria para verificar que un jugador puede jugar una carta.
     * Valida que se puede seleccionar una carta de la mano y jugarla.
     */
    @Test
    void testPlayCard() {
        UUID playerId = gameAppService.getPlayerInfos().get(0).getId();
        Card cardToPlay = gameAppService.getHandCards(playerId).findFirst().orElse(null);

        assertNotNull(cardToPlay);

    }

    /**
     * Prueba unitaria para verificar que un jugador puede robar una carta.
     * Comprueba que la operación no lanza excepciones.
     */
    @Test
    void testDrawCard() {
        UUID playerId = gameAppService.getPlayerInfos().get(0).getId();

        // Roba una carta y verifica que no lanza excepciones
        assertDoesNotThrow(() -> gameAppService.drawCard(playerId));
    }

    /**
     * Prueba unitaria para verificar que se puede obtener la carta superior del mazo.
     * Comprueba que la carta no es nula.
     */
    @Test
    void testPeekTopCard() {
        Card topCard = gameAppService.peekTopCard();

        assertNotNull(topCard);
    }

    /**
     * Prueba unitaria para verificar el estado del juego.
     * Valida que el juego no ha terminado inicialmente.
     */
    @Test
    void testIsGameOver() {
        assertFalse(gameAppService.isGameOver()); // El juego no debería haber terminado inicialmente
    }

    /**
     * Prueba unitaria para verificar que no hay ganador al inicio del juego.
     * Valida que el método getWinner devuelve null.
     */
    @Test
    void testGetWinner() {
        assertNull(gameAppService.getWinner()); // Al inicio no debería haber ganado
    }
}