package ui.view;

import domain.card.CardColor;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Clase que representa un selector de colores en la interfaz gráfica.
 * La funcionalidad de esta clase es mostrar un selector de colores en la interfaz gráfica.
 */
public class ColorPicker {
    /// Colores que se pueden seleccionar en el selector de colores.
    private final ArrayList<String> colors;

    /**
     * Constructor de la clase.
     */
    private ColorPicker() {
        colors = new ArrayList<>();

        for (var color : CardColor.values()) {
            colors.add(color.name());
        }
    }

    /**
     * Metodo que muestra el selector de colores en la interfaz gráfica.
     * SingletonHelper.INSTANCE es una clase interna que se carga cuando se llama a getInstance().
     */
    // See Bill Pugh Singleton approach
    // Ref: https://www.journaldev.com/1377/java-singleton-design-pattern-best-practices-examples#bill-pugh-singleton
    private static class SingletonHelper {
        private static final ColorPicker INSTANCE = new ColorPicker();
    }

    /**
     * Metodo que devuelve la instancia del selector de colores.
     * @return Instancia del selector de colores.
     */
    public static ColorPicker getInstance() {
        return SingletonHelper.INSTANCE;
    }

    /**
     * Metodo que muestra el selector de colores en la interfaz gráfica.
     * @return Color seleccionado.
     */
    public CardColor show() {
        String pickedColor = (String) JOptionPane.showInputDialog(
            null,
            "Choose a color",
            "Wild Card",
            JOptionPane.PLAIN_MESSAGE,
            null,
            colors.toArray(),
            null
        );

        return CardColor.valueOf(pickedColor);
    }
}
