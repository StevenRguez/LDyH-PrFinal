package domain.card;

import java.util.Objects;

/**
 * Clase que representa una carta de número.
 * Implementa la interfaz Card.
 */
public class NumberCard extends AbstractCard {
    private final int value;

    public NumberCard(int value, CardColor color) {
        super(CardType.NUMBER, color);

        CardUtil.validateColor(color);

        CardUtil.validateNumber(value);
        this.value = value;
    }

    /**
     * Devuelve el valor de la carta.
     * @return valor de la carta.
     */
    public int getValue() {
        return value;
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
        NumberCard that = (NumberCard) o;
        return value == that.value && getColor() == that.getColor();
    }

    /**
     * Devuelve el hash code de la carta.
     * @return hash code de la carta.
     */
    @Override
    public int hashCode() {
        return Objects.hash(value, getColor());
    }

    /**
     * Devuelve la representación en cadena de la carta.
     * @return representación en cadena de la carta.
     */
    @Override
    public String toString() {
        return "NumberCard{" +
            value + ", " + getColor() +
            '}';
    }
}
