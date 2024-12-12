package domain.card;

/**
 * Enumerado que representa los tipos de las cartas.
 */
public enum CardType {
    /**
     * Carta de número
     */
    NUMBER,
    /**
     * Carta de prohibido
     */
    SKIP,
    /**
     * Carta de reverso
     */
    REVERSE,
    /**
     * Carta de extraer dos cartas del mazo
     */
    DRAW_TWO,
    /**
     * Carta de cambio de color
     */
    WILD_COLOR,
    /**
     * Carta de cambio de color + extraer cuatro del mazo
     */
    WILD_DRAW_FOUR
}
