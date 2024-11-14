package domain.card;

/**
 * Clase de utilidad para validar las cartas.
 */
public class CardUtil {
    private CardUtil() {
    }

    /**
     * Valida una carta.
     * @param card carta a validar.
     */
    public static void validateColor(CardColor color) {
        if (color == null) {
            throw new IllegalArgumentException("Card color should be defined");
        }
    }

    /**
     * Valida un número.
     * @param number número a validar.
     */
    public static void validateNumber(int number) {
        if (number < 0 || number > 9) {
            throw new IllegalArgumentException("Card number should between 0 and 9");
        }
    }

    /**
     * Valida el tipo de acción.
     * @param type tipo de acción a validar.
     */
    public static void validateActionType(CardType type) {
        if (type == CardType.SKIP || type == CardType.REVERSE || type == CardType.DRAW_TWO) {
            return;
        }

        throw new IllegalArgumentException("Invalid action type");
    }

    /**
     * Valida el tipo de carta.
     * @param type tipo de carta a validar.
     */

    public static boolean isWildCard(Card card) {
        return card.getType() == CardType.WILD_COLOR || card.getType() == CardType.WILD_DRAW_FOUR;
    }
}
