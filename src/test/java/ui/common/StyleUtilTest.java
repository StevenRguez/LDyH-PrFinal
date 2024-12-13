package ui.common;

import domain.card.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import static org.junit.jupiter.api.Assertions.*;

class StyleUtilTest {

    @Test
    void testConvertCardColor() {
        assertEquals(StyleUtil.redColor, StyleUtil.convertCardColor(CardColor.RED));
        assertEquals(StyleUtil.greenColor, StyleUtil.convertCardColor(CardColor.GREEN));
        assertEquals(StyleUtil.blueColor, StyleUtil.convertCardColor(CardColor.BLUE));
        assertEquals(StyleUtil.yellowColor, StyleUtil.convertCardColor(CardColor.YELLOW));
        assertEquals(StyleUtil.blackColor, StyleUtil.convertCardColor(null));
    }

    @Test
    void testGetValueToDisplay_NumberCard() {
        Card numberCard = new NumberCard(5, CardColor.RED);
        assertEquals("5", StyleUtil.getValueToDisplay(numberCard));
    }

    @Test
    void testGetValueToDisplay_SkipCard() {
        Card skipCard = new WildCard(CardType.SKIP, CardColor.BLUE);
        assertNotEquals(Character.toString((char) 8630), StyleUtil.getValueToDisplay(skipCard));
    }

    @Test
    void testGetValueToDisplay_ReverseCard() {
        Card reverseCard = new WildCard(CardType.REVERSE, CardColor.GREEN);
        assertEquals(Character.toString((char) 8634), StyleUtil.getValueToDisplay(reverseCard));
    }

    @Test
    void testGetValueToDisplay_DrawTwoCard() {
        Card drawTwoCard = new WildCard(CardType.DRAW_TWO, CardColor.YELLOW);
        assertEquals("2+", StyleUtil.getValueToDisplay(drawTwoCard));
    }

    @Test
    void testGetValueToDisplay_WildColorCard() {
        Card wildColorCard = new WildCard(CardType.WILD_COLOR, CardColor.RED);
        assertEquals("W", StyleUtil.getValueToDisplay(wildColorCard));
    }

    @Test
    void testGetValueToDisplay_WildDrawFourCard() {
        Card wildDrawFourCard = new WildCard(CardType.WILD_DRAW_FOUR, CardColor.BLUE);
        assertEquals("4+", StyleUtil.getValueToDisplay(wildDrawFourCard));
    }
}
