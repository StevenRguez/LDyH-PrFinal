package domain.testhelper;

import domain.card.*;

/**
 * Clase de utilidad para la creación de objetos de cartas de diferentes tipos.
 * Proporciona métodos estáticos para generar cartas numéricas, cartas de acción
 * y cartas especiales (comodines).
 */
public class CardTestFactory {

    /**
     * Crea una carta numérica con valor 1 y color rojo.
     *
     * @return una instancia de {@link NumberCard}.
     */
    public static NumberCard createNumberCard() {
        return new NumberCard(1, CardColor.RED);
    }

    /**
     * Crea una carta numérica con un valor y color específicos.
     *
     * @param value el valor numérico de la carta.
     * @param color el color de la carta.
     * @return una instancia de {@link NumberCard}.
     */
    public static NumberCard createNumberCard(int value, CardColor color) {
        return new NumberCard(value, color);
    }

    /**
     * Crea una carta de acción con un tipo y color específicos.
     *
     * @param type el tipo de la carta de acción.
     * @param color el color de la carta.
     * @return una instancia de {@link ActionCard}.
     */
    public static ActionCard createActionCard(CardType type, CardColor color){
        return new ActionCard(type, color);
    }

    /**
     * Crea una carta de tipo "saltar" con color azul por defecto.
     *
     * @return una instancia de {@link ActionCard} de tipo SKIP.
     */
    public static ActionCard createSkipCard() {
        return createSkipCard(CardColor.BLUE);
    }

    /**
     * Crea una carta de tipo "saltar" con un color específico.
     *
     * @param color el color de la carta.
     * @return una instancia de {@link ActionCard} de tipo SKIP.
     */
    public static ActionCard createSkipCard(CardColor color) {
        return new ActionCard(CardType.SKIP, color);
    }

    /**
     * Crea una carta de tipo "reversa" con color azul por defecto.
     *
     * @return una instancia de {@link ActionCard} de tipo REVERSE.
     */
    public static ActionCard createReverseCard() {
        return createReverseCard(CardColor.BLUE);
    }

    /**
     * Crea una carta de tipo "reversa" con un color específico.
     *
     * @param color el color de la carta.
     * @return una instancia de {@link ActionCard} de tipo REVERSE.
     */
    public static ActionCard createReverseCard(CardColor color) {
        return new ActionCard(CardType.REVERSE, color);
    }

    /**
     * Crea una carta de tipo "roba dos" con color amarillo por defecto.
     *
     * @return una instancia de {@link ActionCard} de tipo DRAW_TWO.
     */
    public static ActionCard createDrawTwoCard() {
        return createDrawTwoCard(CardColor.YELLOW);
    }

    /**
     * Crea una carta de tipo "roba dos" con un color específico.
     *
     * @param color el color de la carta.
     * @return una instancia de {@link ActionCard} de tipo DRAW_TWO.
     */
    public static ActionCard createDrawTwoCard(CardColor color) {
        return new ActionCard(CardType.DRAW_TWO, color);
    }

    /**
     * Crea un comodín con un tipo específico.
     *
     * @param type el tipo del comodín.
     * @return una instancia de {@link WildCard}.
     */
    public static WildCard createWildCard(CardType type) {
        return new WildCard(type);
    }

    /**
     * Crea un comodín de cambio de color.
     *
     * @return una instancia de {@link WildCard} de tipo WILD_COLOR.
     */
    public static WildCard createWildColorCard() {
        return new WildCard(CardType.WILD_COLOR);
    }

    /**
     * Crea un comodín de cambio de color con un color específico.
     *
     * @param color el color asociado al comodín.
     * @return una instancia de {@link WildCard} de tipo WILD_COLOR.
     */
    public static WildCard createWildColorCard(CardColor color) {
        return new WildCard(CardType.WILD_COLOR, color);
    }

    /**
     * Crea un comodín de "roba cuatro".
     *
     * @return una instancia de {@link WildCard} de tipo WILD_DRAW_FOUR.
     */
    public static WildCard createWildDrawFourCard() {
        return new WildCard(CardType.WILD_DRAW_FOUR);
    }

    /**
     * Crea un comodín de "roba cuatro" con un color específico.
     *
     * @param color el color asociado al comodín.
     * @return una instancia de {@link WildCard} de tipo WILD_DRAW_FOUR.
     */
    public static WildCard createWildDrawFourCard(CardColor color) {
        return new WildCard(CardType.WILD_DRAW_FOUR, color);
    }

    /**
     * Crea una carta genérica dependiendo del tipo, color y valor proporcionados.
     * Si el tipo es {@link CardType#NUMBER}, se crea una {@link NumberCard}.
     * Para otros tipos, se crea una {@link ActionCard}.
     *
     * @param cardType el tipo de la carta.
     * @param cardColor el color de la carta.
     * @param i el valor numérico (solo aplicable para cartas numéricas).
     * @return una instancia de {@link Card} (puede ser NumberCard o ActionCard).
     */
    public static Card createCard(CardType cardType, CardColor cardColor, int i) {
        if (cardType == CardType.NUMBER) {
            return new NumberCard(i, cardColor);
        } else {
            return new ActionCard(cardType, cardColor);
        }
    }
}
