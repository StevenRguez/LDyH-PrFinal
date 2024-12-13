package domain;

import application.IGameAppService;
import domain.card.CardColor;
import domain.player.HandCardList;
import domain.testhelper.CardTestFactory;
import org.junit.jupiter.api.Test;
import ui.common.StyleUtil;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para verificar el comportamiento de la clase HandCardList.
 * Incluye pruebas para añadir, verificar, eliminar y manipular cartas.
 */
public class TestHandCardList {

    /**
     * Verifica que una carta con el mismo número y color existe en la lista de cartas.
     */
    @Test
    public void GivenSameNumberCard_ShouldExist() {
        var numberCard = CardTestFactory.createNumberCard(1, CardColor.YELLOW);
        var sameNumberCard = CardTestFactory.createNumberCard(1, CardColor.YELLOW);

        var handCards = new HandCardList();
        handCards.addCard(numberCard);

        var result = handCards.hasCard(sameNumberCard);

        assertTrue(result);
    }

    /**
     * Verifica que una carta con un número diferente no existe en la lista de cartas.
     */
    @Test
    public void GivenDifferentNumberCard_ShouldNotExist() {
        var numberCard = CardTestFactory.createNumberCard(1, CardColor.YELLOW);
        var anotherNumberCard = CardTestFactory.createNumberCard(2, CardColor.YELLOW);

        var handCards = new HandCardList();
        handCards.addCard(numberCard);

        var result = handCards.hasCard(anotherNumberCard);

        assertFalse(result);
    }

    /**
     * Verifica que una carta con el mismo número pero diferente color no existe en la lista de cartas.
     */
    @Test
    public void GivenCardWithDifferentColor_ShouldNotExist() {
        var numberCard = CardTestFactory.createNumberCard(1, CardColor.YELLOW);
        var sameNumberDifferentColor = CardTestFactory.createNumberCard(1, CardColor.RED);

        var handCards = new HandCardList();
        handCards.addCard(numberCard);

        var result = handCards.hasCard(sameNumberDifferentColor);

        assertFalse(result);
    }

    /**
     * Verifica que una instancia específica de una carta existe en la lista de cartas.
     */
    @Test
    public void GivenSameCardInstance_ShouldExist() {
        var card = CardTestFactory.createNumberCard(3, CardColor.BLUE);

        var handCards = new HandCardList();
        handCards.addCard(card);

        var result = handCards.hasCard(card);

        assertTrue(result);
    }

    /**
     * Verifica que una carta que no fue añadida no existe en la lista de cartas.
     */
    @Test
    public void GivenCardNotAdded_ShouldNotExist() {
        var card = CardTestFactory.createNumberCard(7, CardColor.GREEN);

        var handCards = new HandCardList();

        var result = handCards.hasCard(card);

        assertFalse(result);
    }

    /**
     * Verifica que después de eliminar una carta, esta ya no existe en la lista de cartas.
     */
    @Test
    public void AfterRemovingCard_ShouldNotExist() {
        var card = CardTestFactory.createNumberCard(4, CardColor.RED);

        var handCards = new HandCardList();
        handCards.addCard(card);
        handCards.removeCard(card);

        var result = handCards.hasCard(card);

        assertFalse(result);
    }

    /**
     * Verifica que añadir una carta duplicada no afecta la existencia de la carta en la lista.
     */
    @Test
    public void AddingDuplicateCards_ShouldStillExistOnce() {
        var card = CardTestFactory.createNumberCard(5, CardColor.YELLOW);

        var handCards = new HandCardList();
        handCards.addCard(card);
        handCards.addCard(card);

        var result = handCards.hasCard(card);

        assertTrue(result); // La carta debe existir aunque se haya añadido varias veces.
    }

    /**
     * Verifica que un color de carta rojo se convierte correctamente al estilo rojo.
     */
    @Test
    public void givenRedCardColor_shouldConvertToRedColor() {
        assertEquals(StyleUtil.redColor, StyleUtil.convertCardColor(CardColor.RED));
    }

    /**
     * Verifica que un color de carta azul se convierte correctamente al estilo azul.
     */
    @Test
    public void givenBlueCardColor_shouldConvertToRedColor() {
        assertEquals(StyleUtil.blueColor, StyleUtil.convertCardColor(CardColor.BLUE));
    }

    /**
     * Verifica que un color de carta verde se convierte correctamente al estilo verde.
     */
    @Test
    public void givenGreenCardColor_shouldConvertToRedColor() {
        assertEquals(StyleUtil.greenColor, StyleUtil.convertCardColor(CardColor.GREEN));
    }

    /**
     * Verifica que un color de carta amarillo se convierte correctamente al estilo amarillo.
     */
    @Test
    public void givenYellowCardColor_shouldConvertToRedColor() {
        assertEquals(StyleUtil.yellowColor, StyleUtil.convertCardColor(CardColor.YELLOW));
    }

    /**
     * Verifica que un color de carta nulo se convierte correctamente al estilo negro.
     */
    @Test
    public void givenNullCardColor_shouldConvertToBlackColor() {
        assertEquals(StyleUtil.blackColor, StyleUtil.convertCardColor(null));
    }

    /**
     * Verifica que el valor mostrado de una carta numérica corresponde al número de la carta.
     */
    @Test
    public void givenNumberCard_shouldReturnNumber() {
        var card = CardTestFactory.createNumberCard(5, CardColor.YELLOW);
        assertEquals("5", StyleUtil.getValueToDisplay(card));
    }
}
