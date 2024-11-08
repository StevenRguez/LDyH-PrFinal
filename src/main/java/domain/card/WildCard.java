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

    public WildCard(CardType type, CardColor color) {
        super(type, color);
        CardUtil.validateColor(color);
    }

    /**
     * Comprueba si la carta es válida para ser jugada.
     * @param topCard carta superior de la pila.
     * @return true si la carta es válida, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WildCard wildCard = (WildCard) o;
        return getType() == wildCard.getType() && getColor() == wildCard.getColor();
    }

    /**
     * Devuelve el hash code de la carta.
     * @return hash code de la carta.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getType(), getColor());
    }

    /**
     * Devuelve la representación en cadena de la carta.
     * @return representación en cadena de la carta.
     */
    @Override
    public String toString() {
        return "WildCard{" +
            getType() + ", " + getColor() +
            '}';
    }
}
