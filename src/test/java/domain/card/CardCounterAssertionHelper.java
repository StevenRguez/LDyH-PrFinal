package domain.card;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase auxiliar para realizar aserciones relacionadas con los contadores de cartas.
 * Proporciona métodos estáticos para verificar la cantidad y distribución de diferentes tipos de cartas.
 */
public class CardCounterAssertionHelper {

    /**
     * Verifica que las cartas numéricas tienen las cantidades esperadas por número y por color.
     *
     * @param cards la lista de cartas a verificar.
     */
    public static void assertNumberCards(List<Card> cards) {
        var expectedNumberCounts = new int[]{4, 8, 8, 8, 8, 8, 8, 8, 8, 8};
        var expectedColorCounts = new int[]{19, 19, 19, 19};

        var counter = new CardCounter(cards);
        var numberCounts = counter.getNumberCounts();
        var colorCounts = counter.getNumberColorCounts();

        assertEquals(76, Arrays.stream(numberCounts).sum());
        assertArrayEquals(expectedNumberCounts, numberCounts);
        assertArrayEquals(expectedColorCounts, colorCounts);
    }

    /**
     * Verifica que las cartas de tipo "Skip" (saltar turno) tienen las cantidades esperadas.
     *
     * @param cards la lista de cartas a verificar.
     */
    public static void assertSkipCards(List<Card> cards) {
        var counter = new CardCounter(cards);
        var colorCounts = counter.getSkipColorCounts();

        assertEquals(8, Arrays.stream(colorCounts).sum());
        assertTrue(Arrays.stream(colorCounts).allMatch((i) -> i == 2));
    }

    /**
     * Verifica que las cartas de tipo "Reverse" (cambio de dirección) tienen las cantidades esperadas.
     *
     * @param cards la lista de cartas a verificar.
     */
    public static void assertReverseCards(List<Card> cards) {
        var counter = new CardCounter(cards);
        var colorCounts = counter.getReverseColorCounts();

        assertEquals(8, Arrays.stream(colorCounts).sum());
        assertTrue(Arrays.stream(colorCounts).allMatch((i) -> i == 2));
    }

    /**
     * Verifica que las cartas de tipo "Draw Two" (robar dos) tienen las cantidades esperadas.
     *
     * @param cards la lista de cartas a verificar.
     */
    public static void assertDrawTwoCards(List<Card> cards) {
        var counter = new CardCounter(cards);
        var colorCounts = counter.getDrawTwoColorCounts();

        assertEquals(8, Arrays.stream(colorCounts).sum());
        assertTrue(Arrays.stream(colorCounts).allMatch((i) -> i == 2));
    }

    /**
     * Verifica que las cartas de tipo "Wild" (comodín) y "Wild Draw Four" (comodín + robar cuatro) tienen las cantidades esperadas.
     *
     * @param cards la lista de cartas a verificar.
     */
    public static void assertWildCards(List<Card> cards) {
        var counter = new CardCounter(cards);
        var wildColorCardCount = counter.getWildCardCounts(CardType.WILD_COLOR);
        var wildDrawCardCount = counter.getWildCardCounts(CardType.WILD_DRAW_FOUR);

        assertEquals(4, wildColorCardCount);
        assertEquals(4, wildDrawCardCount);
    }
}
