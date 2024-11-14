package domain.player;

import domain.card.Card;
import domain.card.CardType;
import domain.card.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Clase que representa la lista de cartas en la mano de un jugador.
 * @version 1.0
 */
public class HandCardList {
    /**
     * Lista de cartas en la mano del jugador.
     */
    private final List<Card> handCards = new ArrayList<>();

    /**
     * Añade una carta a la mano del jugador.
     * @param newCard Carta a añadir.
     */
    public void addCard(Card newCard) {
        handCards.add(newCard);
    }

    /**
     * Elimina una carta de la mano del jugador.
     * @param card Carta a eliminar.
     * @return true si la carta se ha eliminado, false en caso contrario.
     */
    public boolean removeCard(Card card) {
        var cardToRemove = CardUtil.isWildCard(card) ? findCardOfType(card.getType()) : card;
        return handCards.remove(cardToRemove);
    }

    /**
     * Encuentra una carta de un tipo específico en la mano del jugador.
     * @param type Tipo de carta a buscar.
     *             Si la encuentra, devuelve la carta, en caso contrario, devuelve null.
     * @return Carta de tipo type si se encuentra, null en caso contrario.
     */
    private Card findCardOfType(CardType type) {
        for (var card : handCards) {
            if (card.getType() == type) {
                return card;
            }
        }
        return null;
    }

    /**
     * Devuelve un stream de las cartas en la mano del jugador.
     * @return Stream de cartas en la mano del jugador.
     */
    public Stream<Card> getCardStream() {
        return handCards.stream();
    }

    /**
     * Comprueba si la mano del jugador contiene una carta específica.
     * @param card Carta a comprobar.
     * @return true si la mano contiene la carta, false en caso contrario.
     */
    public boolean hasCard(Card card) {
        return CardUtil.isWildCard(card)
            ? getCardStream().anyMatch(c -> c.getType() == card.getType())
            : getCardStream().anyMatch(c -> c.equals(card));
    }

    /**
     * Devuelve el número de cartas en la mano del jugador.
     * @return Número de cartas en la mano del jugador.
     */
    public int size() {
        return handCards.size();
    }
}
