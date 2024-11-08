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
     * Comprueba si dos cartas de número son iguales.
     * @param o objeto a comparar.
     * @return true si las cartas son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberCard that = (NumberCard) o;
        return value == that.value && getColor() == that.getColor();
    }

    /**
     * Devuelve el hash code de la carta de número.
     * @return hash code de la carta de número.
     */
    @Override
    public int hashCode() {
        return Objects.hash(value, getColor());
    }

    /**
     * Devuelve la representación en cadena de la carta de número.
     * @return representación en cadena de la carta de número.
     */
    @Override
    public String toString() {
        return "NumberCard{" +
            value + ", " + getColor() +
            '}';
    }
}
