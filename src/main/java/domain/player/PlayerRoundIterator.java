package domain.player;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Clase que representa un iterador de jugadores en una ronda.
 * Permite recorrer los jugadores en sentido horario o antihorario.
 * @version 1.0
 * @since 04.05.2021
 * @see domain.player.Player
 */
public class PlayerRoundIterator {
    /// Lista de jugadores.
    private final Player[] players;
    /// Índice del jugador actual.
    private int current = 0;
    /// Dirección en la que se mueve el jugador.
    private Direction direction = Direction.CLOCKWISE;

    /**
     * Constructor de la clase PlayerRoundIterator dado un listado de jugadores.
     * @param players Listado de jugadores.
     */
    public PlayerRoundIterator(Player[] players) {
        this.players = players;
    }

    /**
     * Devuelve un stream de los jugadores.
     * @return Stream de jugadores.
     */
    public Stream<Player> stream() {
        return Arrays.stream(players);
    }

    /**
     * Devuelve el jugador actual.
     * @return Jugador actual.
     */
    public Player getCurrentPlayer() {
        return players[current];
    }

    /**
     * Devuelve el jugador siguiente dado su id.
     * @return Jugador siguiente.
     */
    public Player getPlayerById(UUID playerId) {
        /// Para poder devolver el siguiente jugador es necesario recorrer la lista de jugadores e ir comparando identificadores hasta encontrar el esperado.
        for (var player : players) {
            if (player.getId().equals(playerId)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Funcion que cambia el sentido del juego.
     *        Si el sentido actual es horario, lo cambia a antihorario y viceversa.
     *        Por defecto, el sentido es horario.
     */
    public void reverseDirection() {
        direction = Direction.COUNTER_CLOCK_WISE;
    }

    /**
     * Devuelve el índice del jugador siguiente.
     * @return Índice del jugador siguiente.
     */
    public Player next() {
        current = getNextIndex();
        return getCurrentPlayer();
    }

    /**
     * Devuelve el índice del jugador anterior.
     * @return Índice del jugador anterior.
     */
    private int getNextIndex() {
        var increment = direction == Direction.CLOCKWISE ? 1 : -1;
        return (players.length + current + increment) % players.length;
    }
}
