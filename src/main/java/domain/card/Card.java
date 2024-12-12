package domain.card;

import java.io.Serializable;

/**
 * Interfaz que representa una carta.
 */
public interface Card extends Serializable {
    /**
     * Metodo para obtener el tipo de una carta
     * @return tipo de la carta
     */
    CardType getType();

    /**
     * Metodo para obtener el color de una carta
     * @return color de la carta
     */
    CardColor getColor();
}
