package domain.card;
/**
 * Clase abstracta que implementa la interfaz Card.
 * Define los métodos getType y getColor.
 * Implementa los métodos equals, hashCode y toString.
 */
public abstract class AbstractCard implements Card {

    /**
     * Tipo de la carta, que define su función o rol en el juego.
     */
    private final CardType type;

    /**
     * Color de la carta, que puede influir en las reglas del juego.
     */
    private final CardColor color;

    /**
     * Constructor protegido que inicializa una carta con su tipo y color.
     * @param type tipo de la carta.
     * @param color color de la carta.
     */
    protected AbstractCard(CardType type, CardColor color) {
        this.type = type;
        this.color = color;
    }

    // Métodos de la interfaz Card

    /**
     * Devuelve el tipo de la carta.
     * @return tipo de la carta.
     */
    @Override
    public CardType getType() {
        return type;
    }

    /**
     * Devuelve el color de la carta.
     * @return color de la carta.
     */
    @Override
    public CardColor getColor() {
        return color;
    }

    // Métodos de Object

    /**
     * Comprueba si dos cartas son iguales.
     * La comparación se basa en el tipo y el color de la carta.
     * @param o objeto a comparar.
     * @return true si las cartas son iguales, false en caso contrario.
     */
    @Override
    public abstract boolean equals(Object o);

    /**
     * Devuelve el hash code de la carta.
     * El valor del hash se basa en el tipo y el color de la carta.
     * @return hash code de la carta.
     */
    @Override
    public abstract int hashCode();

    /**
     * Devuelve la representación en cadena de la carta.
     * La cadena incluye información sobre el tipo y el color de la carta.
     * @return representación en cadena de la carta.
     */
    @Override
    public abstract String toString();
}