package domain.game;

import domain.card.*;

/**
 * @class CardRules
 * @brief Clase de utilidad que contiene las reglas para validar el juego de cartas en una partida.
 */
public class CardRules {

    /**
     * @brief Constructor privado para evitar la instanciación de esta clase de utilidad.
     * @throws IllegalStateException siempre que se intente instanciar la clase.
     */
    private CardRules(){
        throw new IllegalStateException("Utility class");
    }

    /**
     * @brief Valida si una carta de número puede ser jugada sobre otra carta.
     * 
     * @param topCard La carta en la parte superior del mazo de descarte.
     * @param playedCard La carta de número que se desea jugar.
     * @return true si la carta jugada tiene el mismo color que la carta superior o el mismo valor si la carta superior es también de número; false en caso contrario.
     */
    public static boolean isValidNumberCard(Card topCard, NumberCard playedCard) {
        if(isSameColor(topCard, playedCard)){
            return true;
        }

        if (topCard.getType() == CardType.NUMBER) {
            return ((NumberCard) topCard).getValue() == playedCard.getValue();
        }

        return false;
    }

    /**
     * @brief Valida si una carta de acción puede ser jugada sobre otra carta.
     * 
     * @param topCard La carta en la parte superior del mazo de descarte.
     * @param playedCard La carta de acción que se desea jugar.
     * @return true si la carta jugada tiene el mismo color que la carta superior o es del mismo tipo que la carta superior; false en caso contrario.
     */
    public static boolean isValidActionCard(Card topCard, ActionCard playedCard) {
        if(isSameColor(topCard, playedCard)){
            return true;
        }

        return topCard.getType() == playedCard.getType();
    }

    /**
     * @brief Valida si una carta comodín puede ser jugada.
     * 
     * @param playedCard La carta comodín que se desea jugar.
     * @return true si la carta comodín tiene un color asignado; false en caso contrario.
     */
    public static boolean isValidWildCard(WildCard playedCard) {
        return playedCard.getColor() != null;
    }

    /**
     * @brief Comprueba si dos cartas tienen el mismo color.
     * 
     * @param topCard La carta en la parte superior del mazo de descarte.
     * @param playedCard La carta que se desea jugar.
     * @return true si ambas cartas tienen el mismo color; false en caso contrario.
     */
    private static boolean isSameColor(Card topCard, Card playedCard) {
        return topCard.getColor() == playedCard.getColor();
    }
}
