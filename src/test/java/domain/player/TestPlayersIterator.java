package domain.player;

import domain.testhelper.PlayerTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de test para verificar la interacción entre jugadores
 */
class TestPlayersIterator {

    /**
     * Arreglo de jugadores de prueba
     */
    private final Player[] players = PlayerTestFactory.createPlayers(4);
    /**
     * Atributo de la clase TestPlayersIterator
     */
    private PlayerRoundIterator playerIterator;

    /**
     * Configura el entorno de pruebas antes de cada test.
     * Inicializa un iterador de jugadores para las pruebas.
     */
    @BeforeEach
    void setUp() {
        playerIterator = new PlayerRoundIterator(players);
    }

    /**
     * Prueba que, al inicializar el iterador, el primer jugador sea el actual.
     * Verifica que el primer jugador en la secuencia sea el correcto cuando se inicializa el iterador.
     */
    @Test
    void WhenInitialized_FirstPlayerShouldBeCurrent() {
        var current = playerIterator.getCurrentPlayer();

        assertPlayer(current, "1");
    }

    /**
     * Prueba que al iterar de manera estándar (en orden horario), los jugadores sean devueltos en orden ascendente.
     * Verifica que los jugadores se devuelvan en el orden correcto al iterar de manera normal.
     */
    @Test
    void WhenClockwise_ShouldHaveAscendingOrder() {
        var current = playerIterator.next();
        assertPlayer(current, "2");

        current = playerIterator.next();
        assertPlayer(current, "3");

        current = playerIterator.next();
        assertPlayer(current, "4");
    }

    /**
     * Prueba que al invertir la dirección del iterador, los jugadores se devuelvan en orden descendente.
     * Verifica que el orden de los jugadores cambie correctamente al invertir la dirección del iterador.
     */
    @Test
    void WhenReversed_ShouldHaveDescendingOrder() {
        playerIterator.reverseDirection();

        var current = playerIterator.getCurrentPlayer();
        assertPlayer(current, "1");

        current = playerIterator.next();
        assertPlayer(current, "4");

        current = playerIterator.next();
        assertPlayer(current, "3");

        current = playerIterator.next();
        assertPlayer(current, "2");
    }

    /**
     * Prueba que se pueda encontrar un jugador por su ID válido.
     * Verifica que, al proporcionar un ID de jugador válido, el jugador correcto sea encontrado.
     */
    @Test
    void GivenValidPlayerId_ShouldFind() {
        var playerToFind = players[2];

        var foundPlayer = playerIterator.getPlayerById(playerToFind.getId());

        assertEquals(playerToFind.getName(), foundPlayer.getName());
    }

    /**
     * Prueba que no se pueda encontrar un jugador si se proporciona un ID inválido.
     * Verifica que al proporcionar un ID no existente, no se encuentre ningún jugador.
     */
    @Test
    void GivenInvalidPlayerId_ShouldNotFind() {
        var foundPlayer = playerIterator.getPlayerById(UUID.randomUUID());

        assertNull(foundPlayer);
    }

    /**
     * Metodo auxiliar para comparar el nombre de un jugador con el valor esperado.
     * @param player El jugador a verificar.
     * @param expectedName El nombre esperado para el jugador.
     */
    private void assertPlayer(Player player, String expectedName){
        assertEquals(expectedName, player.getName());
    }
}
