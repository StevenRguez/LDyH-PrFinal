package domain.player;

import domain.card.Card;
import domain.card.CardType;

import domain.card.CardColor;
import domain.testhelper.CardTestFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para la clase HandCardList.
 * Proporciona una cobertura completa para verificar el comportamiento de la lista de cartas en mano
 * con diferentes tipos de cartas.
 *
 * @version 1.0
 * @see HandCardList
 */
public class TestCompleteFileHandCardList {

    /**
     * Prueba el comportamiento básico de HandCardList con una carta normal.
     * Se verifica que la carta se pueda añadir, encontrar y eliminar correctamente.
     */
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

    /**
     * Prueba el comportamiento de HandCardList con una carta comodín.
     * Se asegura de que las cartas comodín puedan añadirse, encontrarse y eliminarse correctamente.
     */
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

    /**
     * Prueba el comportamiento de HandCardList con una carta comodín de color.
     * Asegura que las cartas comodín de color funcionen como se espera.
     */
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

    /**
     * Prueba el comportamiento de HandCardList con una carta comodín de robar cuatro.
     * Valida que estas cartas sean correctamente manejadas en la lista.
     */
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

    /**
     * Prueba el comportamiento de HandCardList con una carta de acción.
     * Comprueba que las cartas de acción puedan añadirse, encontrarse y eliminarse adecuadamente.
     */
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

    /**
     * Prueba el comportamiento de HandCardList con una carta numérica.
     * Asegura que las cartas numéricas funcionen como se espera en la lista.
     */
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
