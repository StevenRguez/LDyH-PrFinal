package domain.player;

import domain.card.Card;

import java.io.Serializable;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Clase que representa un jugador inmutable, esto es, un jugador cuyos atributos no pueden ser modificados.
 *        Implementa la interfaz Serializable para poder ser almacenado en un fichero.
 *        Se utiliza para enviar información del jugador a los clientes.
 * @version 1.0
 */
public class ImmutablePlayer implements Serializable {
    /**
     * Jugador al que representa.
     */
    private final Player player;

    /**
     * Constructor de la clase ImmutablePlayer dado un jugador.
     * @param player Jugador al que representa.
     */
    public ImmutablePlayer(Player player) {
        this.player = player;
    }

    /**
     * Devuelve el identificador del jugador.
     * @return Identificador del jugador.
     */
    public UUID getId() {
        return player.getId();
    }

    /**
     * Devuelve el nombre del jugador.
     * @return Nombre del jugador.
     */
    public String getName() {
        return player.getName();
    }

    /**
     * Devuelve el número de cartas en la mano del jugador.
     * @return Número de cartas en la mano del jugador.
     */
    public Stream<Card> getHandCards() {
        return this.player.getHandCards();
    }

    /**
     * Devuelve el número de cartas en la mano del jugador.
     * @return Número de cartas en la mano del jugador.
     */
    public int getTotalCards() {
        return (int) getHandCards().count();
    }
}
