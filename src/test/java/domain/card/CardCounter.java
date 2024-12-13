package domain.card;

import java.util.List;
import java.util.stream.Stream;

/**
 * Clase que cuenta y clasifica cartas según su tipo y color en un mazo dado.
 */
public class CardCounter {
    /**
     * Lista de cartas que serán analizadas por el contador.
     */
    private final List<Card> cards;

    /**
     * Constructor que inicializa el contador con una lista de cartas.
     *
     * @param cards la lista de cartas a analizar.
     */
    public CardCounter(List<Card> cards) {
        this.cards = cards;
    }

    /**
     * Obtiene la cantidad de cartas numéricas por valor (0-9).
     *
     * @return un arreglo donde el índice representa el valor de la carta (0-9) y el valor es la cantidad de cartas con ese número.
     */
    public int[] getNumberCounts() {
        var numberCounts = new int[10];

        getNumberStream()
                .forEach(c -> numberCounts[c.getValue()] += 1);

        return numberCounts;
    }

    /**
     * Obtiene la cantidad de cartas numéricas clasificadas por color.
     *
     * @return un arreglo donde el índice representa el color (ordinal del enum) y el valor es la cantidad de cartas de ese color.
     */
    public int[] getNumberColorCounts() {
        var colorCounts = new int[4];

        getNumberStream()
                .forEach(c -> colorCounts[c.getColor().ordinal()] += 1);

        return colorCounts;
    }

    /**
     * Obtiene la cantidad de cartas de tipo "Skip" (saltar turno) clasificadas por color.
     *
     * @return un arreglo donde el índice representa el color (ordinal del enum) y el valor es la cantidad de cartas de ese color.
     */
    public int[] getSkipColorCounts() {
        var colorCounts = new int[4];

        cards.stream()
                .filter(c -> c.getType() == CardType.SKIP)
                .map(Card::getColor)
                .forEach(color -> colorCounts[color.ordinal()] += 1);

        return colorCounts;
    }

    /**
     * Obtiene la cantidad de cartas de tipo "Reverse" (cambio de dirección) clasificadas por color.
     *
     * @return un arreglo donde el índice representa el color (ordinal del enum) y el valor es la cantidad de cartas de ese color.
     */
    public int[] getReverseColorCounts() {
        var colorCounts = new int[4];

        cards.stream()
                .filter(c -> c.getType() == CardType.REVERSE)
                .map(Card::getColor)
                .forEach(color -> colorCounts[color.ordinal()] += 1);

        return colorCounts;
    }

    /**
     * Obtiene la cantidad de cartas de tipo "Draw Two" (robar dos) clasificadas por color.
     *
     * @return un arreglo donde el índice representa el color (ordinal del enum) y el valor es la cantidad de cartas de ese color.
     */
    public int[] getDrawTwoColorCounts() {
        var colorCounts = new int[4];

        cards.stream()
                .filter(c -> c.getType() == CardType.DRAW_TWO)
                .map(Card::getColor)
                .forEach(color -> colorCounts[color.ordinal()] += 1);

        return colorCounts;
    }

    /**
     * Obtiene la cantidad de cartas de tipo comodín, como "Wild" o "Wild Draw Four".
     *
     * @param type el tipo de carta comodín a contar.
     * @return la cantidad de cartas de ese tipo.
     */
    public long getWildCardCounts(CardType type) {
        return cards.stream()
                .filter(c -> c.getType() == type)
                .count();
    }

    /**
     * Obtiene un flujo de cartas numéricas del mazo.
     *
     * @return un flujo de objetos {@link NumberCard}.
     */
    private Stream<NumberCard> getNumberStream() {
        return cards.stream()
                .filter(c -> c.getType() == CardType.NUMBER)
                .map(c -> (NumberCard) c);
    }
}
