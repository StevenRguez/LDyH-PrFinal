package domain;

import domain.card.CardColor;
import domain.player.HandCardList;
import domain.testhelper.CardTestFactory;
import org.junit.jupiter.api.Test;
import ui.common.StyleUtil;

import static org.junit.jupiter.api.Assertions.*;

public class TestHandCardList {
    @Test
    void GivenSameNumberCard_ShouldExist() {
        var numberCard = CardTestFactory.createNumberCard(1, CardColor.YELLOW);
        var sameNumberCard = CardTestFactory.createNumberCard(1, CardColor.YELLOW);

        var handCards = new HandCardList();
        handCards.addCard(numberCard);

        var result = handCards.hasCard(sameNumberCard);

        assertTrue(result);
    }

    @Test
    void GivenDifferentNumberCard_ShouldNotExist() {
        var numberCard = CardTestFactory.createNumberCard(1, CardColor.YELLOW);
        var anotherNumberCard = CardTestFactory.createNumberCard(2, CardColor.YELLOW);

        var handCards = new HandCardList();
        handCards.addCard(numberCard);

        var result = handCards.hasCard(anotherNumberCard);

        assertFalse(result);
    }

    @Test
    void GivenCardWithDifferentColor_ShouldNotExist() {
        var numberCard = CardTestFactory.createNumberCard(1, CardColor.YELLOW);
        var sameNumberDifferentColor = CardTestFactory.createNumberCard(1, CardColor.RED);

        var handCards = new HandCardList();
        handCards.addCard(numberCard);

        var result = handCards.hasCard(sameNumberDifferentColor);

        assertFalse(result);
    }

    @Test
    void GivenSameCardInstance_ShouldExist() {
        var card = CardTestFactory.createNumberCard(3, CardColor.BLUE);

        var handCards = new HandCardList();
        handCards.addCard(card);

        var result = handCards.hasCard(card);

        assertTrue(result);
    }

    @Test
    void GivenCardNotAdded_ShouldNotExist() {
        var card = CardTestFactory.createNumberCard(7, CardColor.GREEN);

        var handCards = new HandCardList();

        var result = handCards.hasCard(card);

        assertFalse(result);
    }

    @Test
    void AfterRemovingCard_ShouldNotExist() {
        var card = CardTestFactory.createNumberCard(4, CardColor.RED);

        var handCards = new HandCardList();
        handCards.addCard(card);
        handCards.removeCard(card);

        var result = handCards.hasCard(card);

        assertFalse(result);
    }

    @Test
    void AddingDuplicateCards_ShouldStillExistOnce() {
        var card = CardTestFactory.createNumberCard(5, CardColor.YELLOW);

        var handCards = new HandCardList();
        handCards.addCard(card);
        handCards.addCard(card);

        var result = handCards.hasCard(card);

        assertTrue(result); // La carta debe existir aunque se haya añadido varias veces.
    }

    @Test
    void givenRedCardColor_shouldConvertToRedColor() {
        assertEquals(StyleUtil.redColor, StyleUtil.convertCardColor(CardColor.RED));
    }

    @Test
    void givenBlueCardColor_shouldConvertToRedColor() {
        assertEquals(StyleUtil.blueColor, StyleUtil.convertCardColor(CardColor.BLUE));
    }

    @Test
    void givenGreenCardColor_shouldConvertToRedColor() {
        assertEquals(StyleUtil.greenColor, StyleUtil.convertCardColor(CardColor.GREEN));
    }

    @Test
    void givenYellowCardColor_shouldConvertToRedColor() {
        assertEquals(StyleUtil.yellowColor, StyleUtil.convertCardColor(CardColor.YELLOW));
    }

    @Test
     void givenNullCardColor_shouldConvertToBlackColor() {
        assertEquals(StyleUtil.blackColor, StyleUtil.convertCardColor(null));
    }

    @Test
     void givenNumberCard_shouldReturnNumber() {
        var card = CardTestFactory.createNumberCard(5, CardColor.YELLOW);
        assertEquals("5", StyleUtil.getValueToDisplay(card));
    }
}
