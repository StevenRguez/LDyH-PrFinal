package domain.player;

import domain.card.CardColor;
import domain.testhelper.CardTestFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestHandCardList {

    /**
     * Prueba que no se puede eliminar una carta de la mano cuando está vacía.
     * Verifica que el intento de eliminar una carta de una lista vacía falle.
     */
    @Test
    void WhenEmpty_ShouldNotRemove() {
        var handCards = new HandCardList();

        var result = handCards.removeCard(CardTestFactory.createSkipCard());

        assertFalse(result);
    }

    /**
     * Prueba que solo se elimine una vez cuando hay dos cartas con el mismo valor.
     * Verifica que la carta se elimina correctamente solo una vez, incluso si existen duplicados.
     */
    @Test
    void GivenTwoCardsWithSameValues_ShouldRemoveOnlyOnce() {
        var handCards = new HandCardList();
        var numberCard1 = CardTestFactory.createNumberCard(1, CardColor.RED);
        var numberCard2 = CardTestFactory.createNumberCard(1, CardColor.RED);
        handCards.addCard(numberCard1);
        handCards.addCard(numberCard2);

        var result = handCards.removeCard(numberCard1);

        assertTrue(result);
        assertEquals(1, handCards.size());
    }

    /**
     * Prueba que solo se elimine una vez cuando ambas cartas son la misma instancia.
     * Verifica que, aunque haya referencias duplicadas a la misma carta, solo se elimine una vez.
     */
    @Test
    void GivenTwoCardsWithSameReference_ShouldRemoveOnlyOnce() {
        var handCards = new HandCardList();
        var numberCard1 = CardTestFactory.createNumberCard(1, CardColor.RED);
        handCards.addCard(numberCard1);
        handCards.addCard(numberCard1);

        var result = handCards.removeCard(numberCard1);

        assertTrue(result);
        assertEquals(1, handCards.size());
    }

    /**
     * Prueba que solo se elimine una vez una carta comodín de color, incluso si se añaden múltiples instancias de la misma carta.
     * Verifica que, aunque haya varias cartas del mismo tipo, solo se elimine una cuando se intente eliminar una carta específica.
     */
    @Test
    void GivenWildColorWithColor_ShouldRemoveOnlyOnce() {
        var handCards = new HandCardList();
        handCards.addCard(CardTestFactory.createWildColorCard());
        handCards.addCard(CardTestFactory.createWildColorCard());

        var result = handCards.removeCard(CardTestFactory.createWildColorCard(CardColor.GREEN));

        assertTrue(result);
        assertEquals(1, handCards.size());
    }

    /**
     * Prueba que una carta comodín de robar cuatro no se elimine si no existe una carta del tipo adecuado.
     * Verifica que, si el tipo de la carta que se intenta eliminar no coincide con el tipo de carta presente en la lista, la eliminación no tiene éxito.
     */
    @Test
    void GivenWildDrawFourWithColor_WhenTypeDoesNotExist_ShouldNotRemove() {
        var handCards = new HandCardList();
        handCards.addCard(CardTestFactory.createWildColorCard());
        handCards.addCard(CardTestFactory.createWildColorCard());

        var result = handCards.removeCard(CardTestFactory.createWildDrawFourCard(CardColor.GREEN));

        assertFalse(result);
        assertEquals(2, handCards.size());
    }
}
