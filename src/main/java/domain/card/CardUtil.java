package domain.card;

/**
 * Clase de utilidad para validar las propiedades de las cartas.
 */
public class CardUtil {
    private CardUtil() {
    }

    /**
     * Valida el color de una carta.
     * @param color Color de la carta.
     */
    public static void validateColor(CardColor color) {
        if (color == null) {
            throw new IllegalArgumentException("Card color should be defined");
        }
    }

    /**
     * Valida el número de una carta.
     * @param number Número de la carta.
     */
    public static void validateNumber(int number) {
        if (number < 0 || number > 9) {
            throw new IllegalArgumentException("Card number should between 0 and 9");
        }
    }

    /**
     * Valida el tipo de una carta de acción.
     * @param type Tipo de la carta de acción.
     */
    public static void validateActionType(CardType type) {
        if (type == CardType.SKIP || type == CardType.REVERSE || type == CardType.DRAW_TWO) {
            return;
        }

        throw new IllegalArgumentException("Invalid action type");
    }

    /**
     * Valida el tipo de una carta comodín.
     * @param type Tipo de la carta comodín.
     */
    public static boolean isWildCard(Card card) {
        return card.getType() == CardType.WILD_COLOR || card.getType() == CardType.WILD_DRAW_FOUR;
    }
}
