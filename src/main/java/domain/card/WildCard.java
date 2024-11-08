package domain.card;

import java.util.Objects;

/**
 * Clase que representa una carta comodín.
 * Implementa la interfaz Card.
 */
public class WildCard extends AbstractCard {
    public WildCard(CardType type) {
        super(type, null);
    }

    /**
     * Crea una carta comodín.
     * @param type tipo de carta.
     * @param color color de la carta.
     */
    public WildCard(CardType type, CardColor color) {
        super(type, color);
        CardUtil.validateColor(color);
    }

    /**
     * Comprueba si dos cartas comodín son iguales.
     * @param o objeto a comparar.
     * @return true si las cartas son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WildCard wildCard = (WildCard) o;
        return getType() == wildCard.getType() && getColor() == wildCard.getColor();
    }

    /**
     * Devuelve el hash code de la carta comodín.
     * @return hash code de la carta comodín.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getType(), getColor());
    }

    /**
     * Devuelve la representación en cadena de la carta comodín.
     * @return representación en cadena de la carta comodín.
     */
    @Override
    public String toString() {
        return "WildCard{" +
            getType() + ", " + getColor() +
            '}';
    }
}
