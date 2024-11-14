package ui.common;

import domain.card.Card;
import domain.card.CardColor;
import domain.card.NumberCard;

import java.awt.*;

/**
 * Clase que sirve para definir estilos comunes a lo largo de la aplicación.
 *     Se definen colores y fuentes comunes a lo largo de la aplicación.
 *     Se definen también métodos para convertir colores de cartas a colores de la interfaz gráfica.
 *     Se define un metodo para convertir el valor de una carta a un valor a mostrar en la carta.
 *     Se definen caracteres especiales para las cartas especiales.
 */
public class StyleUtil {
    /**
     * Constructor privado para evitar instanciamiento de la clase.
     */
    private StyleUtil() {
    }

    /// Colores comunes a lo largo de la aplicación del juego UNO.
    public static final Color redColor = new Color(192, 80, 77);
    public static final Color blueColor = new Color(31, 73, 125);
    public static final Color greenColor = new Color(0, 153, 0);
    public static final Color yellowColor = new Color(255, 204, 0);
    public static final Color blackColor = new Color(0, 0, 0);

    /// Fuentes comunes a lo largo de la aplicación del juego UNO.
    public static final String DEFAULT_FONT = "Helvetica";

    /// Caracteres especiales para las cartas especiales.
    private static final Character REVERSE_CHAR = (char) 8634;
    private static final Character SKIP_CHAR = (char) Integer.parseInt("2718", 16);

    /**
     * Metodo que convierte un color de carta a un color de la interfaz grafica.
     * @param color Color de la carta.
     * @return Color de la interfaz gráfica.
     */
    public static Color convertCardColor(CardColor color) {
        if (color == null) {
            return blackColor;
        }

        switch (color) {
            case RED -> {
                return redColor;
            }
            case GREEN -> {
                return greenColor;
            }
            case BLUE -> {
                return blueColor;
            }
            case YELLOW -> {
                return yellowColor;
            }
            default -> {
                throw new IllegalArgumentException("Unsupported card color " + color);
            }
        }
    }

    /**
     * Metodo que obtiene el valor a mostrar en la carta.
     * @param card Carta de la cual se quiere obtener el valor a mostrar.
     * @return Valor a mostrar en la carta.
     */
    public static String getValueToDisplay(Card card) {
        switch (card.getType()) {
            case NUMBER -> {
                return Integer.toString(((NumberCard) card).getValue());
            }
            case SKIP -> {
                return SKIP_CHAR.toString();
            }
            case REVERSE -> {
                return REVERSE_CHAR.toString();
            }
            case DRAW_TWO -> {
                return "2+";
            }
            case WILD_COLOR -> {
                return "W";
            }
            case WILD_DRAW_FOUR -> {
                return "4+";
            }
            default -> throw new IllegalArgumentException("Unsupported card type " + card.getType());
        }
    }
}
