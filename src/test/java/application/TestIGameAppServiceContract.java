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

/**
 * Clase de prueba para verificar el contrato de la interfaz IGameAppService.
 * Asegura que todas las implementaciones cumplen con el comportamiento esperado.
 */
class TestIGameAppServiceContract {

    /**
     * Instancia de la interfaz de servicio de aplicación que será probada.
     * Utiliza una implementación concreta para los tests.
     */
    private IGameAppService gameAppService;

    /**
     * Configuración inicial antes de cada prueba.
     * Se instancia una implementación concreta de IGameAppService.
     */
    @BeforeEach
    void setUp() {
        gameAppService = new GameAppService();
    }

    /**
     * Prueba unitaria para verificar que se obtienen las informaciones de los jugadores.
     * Comprueba que la lista de jugadores no es nula y contiene al menos un jugador.
     */
    @Test
    void testGetPlayerInfos() {
        List<PlayerInfoDTO> playerInfos = gameAppService.getPlayerInfos();

        assertNotNull(playerInfos);
        assertFalse(playerInfos.isEmpty()); // Verifica que hay jugadores en el juego
    }

    /**
     * Prueba unitaria para verificar que se obtiene el jugador actual.
     * Valida que el jugador actual tiene un nombre y un identificador no nulos.
     */
    @Test
    void testGetCurrentPlayer() {
        PlayerInfoDTO currentPlayer = gameAppService.getCurrentPlayer();

        assertNotNull(currentPlayer);
        assertNotNull(currentPlayer.getName());
        assertNotNull(currentPlayer.getId());
    }

    /**
     * Prueba unitaria para verificar que se obtienen las cartas en mano de un jugador.
     * Comprueba que el flujo de cartas no es nulo.
     */
    @Test
    void testGetHandCards() {
        UUID playerId = gameAppService.getPlayerInfos().get(0).getId();
        Stream<Card> handCards = gameAppService.getHandCards(playerId);

        assertNotNull(handCards);
    }

    /**
     * Prueba unitaria para verificar que un jugador puede jugar una carta.
     * Valida que el jugador tiene al menos una carta en mano.
     */
    @Test
    void testPlayCard() {
        UUID playerId = gameAppService.getPlayerInfos().get(0).getId();
        Card card = gameAppService.getHandCards(playerId).findFirst().orElse(null);

        // Verificar que el jugador tiene cartas en mano
        assertNotNull(card);
    }

    /**
     * Prueba unitaria para verificar que un jugador puede robar una carta.
     * Comprueba que la operación no lanza excepciones.
     */
    @Test
    void testDrawCard() {
        UUID playerId = gameAppService.getPlayerInfos().get(0).getId();

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
        boolean isGameOver = gameAppService.isGameOver();

        assertFalse(isGameOver); // Inicialmente el juego no debería haber terminado
    }

    /**
     * Prueba unitaria para verificar que no hay ganador al inicio del juego.
     * Valida que el metodo getWinner devuelve null.
     */
    @Test
    void testGetWinner() {
        ImmutablePlayer winner = gameAppService.getWinner();

        assertNull(winner); // Al inicio no hay ganador
    }
}
