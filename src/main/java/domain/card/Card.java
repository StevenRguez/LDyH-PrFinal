package domain.card;

import java.io.Serializable;

/**
 * Interfaz que representa una carta.
 */
public interface Card extends Serializable {
    CardType getType();
    CardColor getColor();
}
