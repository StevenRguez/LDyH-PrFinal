package ui.view;

import domain.card.Card;
import ui.common.StyleUtil;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

/**
 * Clase que representa la vista de una carta en la interfaz gráfica.
 *     Se define el tamaño de la carta y se dibuja la carta en la interfaz gráfica.
 *     Se define un efecto de hover para la carta.
 *     Se define un manejador de eventos para el click en la carta.
 *     Se define un manejador de eventos para el hover en la carta.
 * La funcion extendida de esta clase es la de mostrar una carta en la interfaz gráfica.
 */
public class CardView extends JPanel {
    /// Carta que se va a mostrar en la vista.
    private final Card card;
    /// Valor de la carta que se va a mostrar en la vista.
    private final String value;

    /// Medidas de la carta: ancho y alto. Margen de la carta.
    private final int cardWidth = 100;
    private final int cardHeight = 150;
    private static final int margin = 5;

    /// Dimension de la carta.
    private final Dimension dimension = new Dimension(cardWidth, cardHeight);

    /// Bordes de la carta para el efecto de hover.
    private final Border defaultBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.white, Color.gray);
    private final Border focusedBorder = BorderFactory.createEtchedBorder(WHEN_FOCUSED, Color.black, Color.gray);

    /// Manejador de eventos para el click en la carta.
    private final Consumer<Card> handleCardClick;

    /**
     * Constructor de la clase.
     * @param card Carta que se va a mostrar en la vista.
     */
    public CardView(Card card) {
        this(card, null);
    }

    /**
     * Constructor de la clase.
     * @param card Carta que se va a mostrar en la vista.
     * @param onCardClick Manejador de eventos para el click en la carta.
     */
    public CardView(Card card, Consumer<Card> onCardClick){
        this.card = card;
        this.handleCardClick = onCardClick;
        this.value = StyleUtil.getValueToDisplay(card);

        initView();
    }

    /**
     * Metodo que devuelve las dimensiones de la carta.
     * @return Dimensiones de la carta.
     */
    public Dimension getDimension() {
        return dimension;
    }

    /**
     * Metodo void que inicializa la vista de la carta.
     */
    private void initView() {
        setPreferredSize(dimension);
        setBorder(defaultBorder);

        addMouseListener(new MouseAdapter() {
            /**
             * Se invoca cuando el cursor entra en los límites de la carta.
             * @param e the event to be processed
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                showHoverEffect();
            }

            /**
             * Se invoca cuando el cursor sale de los límites de la carta.
             * @param e the event to be processed
             */
            @Override
            public void mouseExited(MouseEvent e) {
               super.mouseExited(e);
               removeHoverEffect();
            }

            /**
             * Se invoca cuando se presiona un botón del ratón en los límites de la carta.
             * @param e the event to be processed
             */
            @Override
            public void mousePressed(MouseEvent e) {
                if(handleCardClick != null) {
                    handleCardClick.accept(card);
                }
            }
        });
    }

    /**
     * Metodo que dibuja la carta en la interfaz gráfica.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        var cardColor = StyleUtil.convertCardColor(card.getColor());

        fillBackground(g2, cardColor);
        drawWhiteOvalInCenter(g2);
        drawValueInCenter(g2, cardColor);
        drawValueInCorner(g2);
    }

    /**
     * Metodo que rellena el fondo de la carta.
     * @param g2 Graphics2D para dibujar la carta.
     * @param cardColor Color de la carta.
     */
    private void fillBackground(Graphics2D g2, Color cardColor) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, cardWidth, cardHeight);

        g2.setColor(cardColor);
        g2.fillRect(margin, margin, cardWidth - 2 * margin, cardHeight - 2 * margin);
    }

    /**
     * Metodo que dibuja un óvalo blanco en el centro de la carta.
     * @param g2 Graphics2D para dibujar la carta.
     */
    private void drawWhiteOvalInCenter(Graphics2D g2) {
        var transformer = g2.getTransform();
        g2.setColor(Color.white);
        g2.rotate(45, (double) cardWidth * 3 / 4, (double) cardHeight * 3 / 4);
        g2.fillOval(0, cardHeight / 4, cardWidth * 3 / 5, cardHeight);

        g2.setTransform(transformer);
    }

    /**
     * Metodo que dibuja el valor de la carta en el centro de la carta.
     * @param g2 Graphics2D para dibujar la carta.
     * @param cardColor Color de la carta.
     */
    private void drawValueInCenter(Graphics2D g2, Color cardColor) {
        var defaultFont = new Font(StyleUtil.DEFAULT_FONT, Font.BOLD, cardWidth / 2 + 5);
        var fontMetrics = this.getFontMetrics(defaultFont);
        int stringWidth = fontMetrics.stringWidth(value) / 2;
        int fontHeight = defaultFont.getSize() / 3;

        g2.setColor(cardColor);
        g2.setFont(defaultFont);
        g2.drawString(value, cardWidth / 2 - stringWidth, cardHeight / 2 + fontHeight);
    }

    /**
     * Metodo que dibuja el valor de la carta en la esquina de la carta.
     * @param g2 Graphics2D para dibujar la carta.
     */
    private void drawValueInCorner(Graphics2D g2) {
        var defaultFont = new Font(StyleUtil.DEFAULT_FONT, Font.ITALIC, cardWidth / 5);

        g2.setColor(Color.white);
        g2.setFont(defaultFont);
        g2.drawString(value, margin, 5 * margin);
    }

    /**
     * Metodo que muestra el efecto de hover en la carta.
     */
    private void showHoverEffect(){
        setBorder(focusedBorder);

        Point p = getLocation();
        p.y -= 20;
        setLocation(p);
    }

    /**
     * Metodo que remueve el efecto de hover en la carta.
     */
    private void removeHoverEffect() {
        setBorder(defaultBorder);

        Point p = getLocation();
        p.y += 20;
        setLocation(p);
    }
}
