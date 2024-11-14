package domain.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase que representa una baraja de cartas.
 */
public class CardDeck {
    private final List<Card> cards = new ArrayList<>(108);

    /**
     * Crea una baraja de cartas.
     */
    public CardDeck() {
        createCards();
    }

    /**
     * Devuelve una lista de cartas.
     * @return lista de cartas.
     */
    public List<Card> getImmutableCards() {
        return Collections.unmodifiableList(cards);
    }

    /**
     * Baraja las cartas de la baraja.
     */
    private void createCards() {
        createNumberCards();
        createActionCards();
        createWildCards();
    }

    /**
     * Crea las cartas numeradas.
     */
    private void createNumberCards() {
        for (var color : CardColor.values()) {
            cards.add(new NumberCard(0, color));

            for (var i = 1; i <= 9; i++) {
                cards.add(new NumberCard(i, color));
                cards.add(new NumberCard(i, color));
            }
        }
    }

    /**
     * Crea las cartas de acción.
     */
    private void createActionCards() {
        for (var color : CardColor.values()) {
            for (var i = 0; i < 2; i++) {
                cards.add(new ActionCard(CardType.SKIP, color));
                cards.add(new ActionCard(CardType.REVERSE, color));
                cards.add(new ActionCard(CardType.DRAW_TWO, color));
            }
        }
    }
    /**
     * Crea las cartas comodín.
     */

    private void createWildCards() {
        for (var i = 0; i < 4; i++) {
            cards.add(new WildCard(CardType.WILD_COLOR));
            cards.add(new WildCard(CardType.WILD_DRAW_FOUR));
        }
    }
}
