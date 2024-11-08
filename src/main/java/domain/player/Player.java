package domain.player;

import domain.card.Card;
import domain.common.Entity;

import java.util.stream.Stream;

/**
 * Clase que representa un jugador.
 * Un jugador tiene un nombre y una lista de cartas en la mano.
 * @version 1.0
 */
public class Player extends Entity {
    /// Nombre del jugador.
    private final String name;
    /// Lista de cartas en la mano del jugador.
    private final HandCardList handCards;

    /**
     * Constructor de la clase Player dado el nombre y un listado de cartas en la mano.
     * @param name Nombre del jugador.
     * @param handCards Listado de cartas en la mano del jugador.
     */
    public Player(String name, HandCardList handCards){
        super();
        this.name = name;
        this.handCards = handCards;
    }

    /**
     * Devuelve el nombre del jugador.
     * @return Nombre del jugador.
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve el número de cartas en la mano del jugador.
     * @return Número de cartas en la mano del jugador.
     */
    public Stream<Card> getHandCards() {
        return this.handCards.getCardStream();
    }

    /**
     * Añade una carta a la mano del jugador.
     * @param card Carta a añadir.
     */
    public void addToHandCards(Card card){
        handCards.addCard(card);
    }

    /**
     * Elimina una carta de la mano del jugador.
     *
     * @param card Carta a eliminar.
     */
    public void removePlayedCard(Card card) {
        handCards.removeCard(card);
    }

    /**
     * Devuelve si tiene o no una carta en la mano.
     * @return si tiene la carta en la mano.
     */
    public boolean hasHandCard(Card card) {
        return this.handCards.hasCard(card);
    }

    /**
     * Devuelve un jugador inmutable.
     * @return Jugador inmutable.
     */
    public ImmutablePlayer toImmutable() {
        return new ImmutablePlayer(this);
    }
}
