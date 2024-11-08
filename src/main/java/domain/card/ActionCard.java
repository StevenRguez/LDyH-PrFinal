package domain.card;

import java.util.Objects;

/**
 * Clase que representa una carta de acción.
 * Implementa la interfaz Card.
 */
public class ActionCard extends AbstractCard {
    public ActionCard(CardType type, CardColor color) {
        super(type, color);
        CardUtil.validateActionType(type);
        CardUtil.validateColor(color);
    }

    /**
     * Comprueba si dos cartas de acción son iguales.
     * @param o objeto a comparar.
     * @return true si las cartas son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionCard that = (ActionCard) o;
        return getType() == that.getType() && getColor() == that.getColor();
    }

    /**
     * Devuelve el hash code de la carta de acción.
     * @return hash code de la carta de acción.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getType(), getColor());
    }

    /**
     * Devuelve la representación en cadena de la carta de acción.
     * @return representación en cadena de la carta de acción.
     */
    @Override
    public String toString() {
        return "ActionCard{" +
            getType() + ", " + getColor() +
            '}';
    }
}
