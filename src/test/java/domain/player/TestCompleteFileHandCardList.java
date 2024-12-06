package domain.player;

import domain.card.Card;
import domain.card.CardType;

import domain.card.CardColor;
import domain.testhelper.CardTestFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para la clase HandCardList.
 * @version 1.0
 * @see HandCardList
 */
public class TestCompleteFileHandCardList {
    /// Test para comprobar la clase HandCardList
    @Test
    void testHandCardList() {
        HandCardList handCardList = new HandCardList();
        Card card = CardTestFactory.createCard(CardType.NUMBER, CardColor.BLUE, 1);
        handCardList.addCard(card);
        assertTrue(handCardList.getCardStream().anyMatch(c -> c.equals(card)));
        assertTrue(handCardList.hasCard(card));
        handCardList.removeCard(card);
        assertFalse(handCardList.getCardStream().anyMatch(c -> c.equals(card)));
        assertFalse(handCardList.hasCard(card));
    }

    /// Test para comprobar la clase HandCardList con una carta comodín
    @Test
    void testHandCardListWithWildCard() {
        HandCardList handCardList = new HandCardList();
        Card card = CardTestFactory.createWildCard(CardType.WILD_COLOR);
        handCardList.addCard(card);
        assertTrue(handCardList.getCardStream().anyMatch(c -> c.getType() == card.getType()));
        assertTrue(handCardList.hasCard(card));
        handCardList.removeCard(card);
        assertFalse(handCardList.getCardStream().anyMatch(c -> c.getType() == card.getType()));
        assertFalse(handCardList.hasCard(card));
    }

    /// Test para comprobar la clase HandCardList con una carta comodín de color
    @Test
    void testHandCardListWithWildColorCard() {
        HandCardList handCardList = new HandCardList();
        Card card = CardTestFactory.createWildColorCard(CardColor.RED);
        handCardList.addCard(card);
        assertTrue(handCardList.getCardStream().anyMatch(c -> c.getType() == card.getType()));
        assertTrue(handCardList.hasCard(card));
        handCardList.removeCard(card);
        assertFalse(handCardList.getCardStream().anyMatch(c -> c.getType() == card.getType()));
        assertFalse(handCardList.hasCard(card));
    }

    /// Test para comprobar la clase HandCardList con una carta comodín de robar cuatro
    @Test
    void testHandCardListWithWildDrawFourCard() {
        HandCardList handCardList = new HandCardList();
        Card card = CardTestFactory.createWildDrawFourCard(CardColor.GREEN);
        handCardList.addCard(card);
        assertTrue(handCardList.getCardStream().anyMatch(c -> c.getType() == card.getType()));
        assertTrue(handCardList.hasCard(card));
        handCardList.removeCard(card);
        assertFalse(handCardList.getCardStream().anyMatch(c -> c.getType() == card.getType()));
        assertFalse(handCardList.hasCard(card));
    }

    /// Test para comprobar la clase HandCardList con una carta de acción
    @Test
    void testHandCardListWithActionCard() {
        HandCardList handCardList = new HandCardList();
        Card card = CardTestFactory.createActionCard(CardType.SKIP, CardColor.YELLOW);
        handCardList.addCard(card);
        assertTrue(handCardList.getCardStream().anyMatch(c -> c.equals(card)));
        assertTrue(handCardList.hasCard(card));
        handCardList.removeCard(card);
        assertFalse(handCardList.getCardStream().anyMatch(c -> c.equals(card)));
        assertFalse(handCardList.hasCard(card));
    }

    /// Test para comprobar la clase HandCardList con una carta numérica
    @Test
    void testHandCardListWithNumberCard() {
        HandCardList handCardList = new HandCardList();
        Card card = CardTestFactory.createNumberCard(5, CardColor.RED);
        handCardList.addCard(card);
        assertTrue(handCardList.getCardStream().anyMatch(c -> c.equals(card)));
        assertTrue(handCardList.hasCard(card));
        handCardList.removeCard(card);
        assertFalse(handCardList.getCardStream().anyMatch(c -> c.equals(card)));
        assertFalse(handCardList.hasCard(card));
    }
}
