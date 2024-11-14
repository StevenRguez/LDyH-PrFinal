package ui.view;

import application.IGameAppService;
import domain.common.DomainEvent;
import domain.common.DomainEventPublisher;
import domain.common.DomainEventSubscriber;
import domain.game.events.CardDrawn;
import domain.game.events.CardPlayed;
import domain.game.events.GameOver;

import javax.swing.*;
import java.awt.*;

/**
 * Clase que representa la vista del estado del juego en la interfaz gráfica.
 * Se define la impresión de mensajes en la interfaz gráfica.
 * Esta clase es necesaria para mostrar el estado.
 */
public class GameStatusView extends JPanel implements DomainEventSubscriber {
    /// Mensaje de error.
    private String error;
    /// Mensaje de estado del juego.
    private String text;
    /// Centro del panel.
    private int panelCenter;

    /// Servicio de la aplicación del juego.
    private final IGameAppService appService;

    /**
     * Constructor de la clase.
     * @param appService Servicio de la aplicación del juego.
     */
    public GameStatusView(IGameAppService appService) {
        this.appService = appService;

        setPreferredSize(new Dimension(275, 200));
        setOpaque(false);
        error = "";

        updateStatus();

        DomainEventPublisher.subscribe(this);
    }

    /**
     * Metodo que pinta el componente para que se vea mejor de cara al usuario.
     * @param g Graficos.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        panelCenter = getWidth() / 2;

        printMessage(g);
        printError(g);
    }

    /**
     * Imprime, en la configuracion indicada, el mensaje de error en caso de que se dé.
     * @param g Graficos
     */
    private void printError(Graphics g) {
        if (!error.isEmpty()) {
            Font adjustedFont = new Font("Calibri", Font.PLAIN, 25);

            // Determine the width of the word to position
            FontMetrics fm = this.getFontMetrics(adjustedFont);
            int xPos = panelCenter - fm.stringWidth(error) / 2;

            g.setFont(adjustedFont);
            g.setColor(Color.red);
            g.drawString(error, xPos, 35);

            error = "";
        }
    }

    /**
     * Al igual que la funcion printError,se imprime un mensaje que sea necesario en el juego.
     * @param g Graficos
     */
    private void printMessage(Graphics g) {
        Font adjustedFont = new Font("Calibri", Font.BOLD, 25);

        //Determine the width of the word to position
        FontMetrics fm = this.getFontMetrics(adjustedFont);
        int xPos = panelCenter - fm.stringWidth(text) / 2;

        g.setFont(adjustedFont);
        g.setColor(new Color(228, 108, 10));
        g.drawString(text, xPos, 75);
    }

    /**
     * Actualiza el texto del estado para reflejar el estado actual del juego.
     * Si el juego ha terminado, el estado mostrará el nombre del ganador.
     * Si el juego sigue en curso, el estado indicará de quién es el turno.
     * Luego, el estado se actualiza en la interfaz de usuario llamando a repaint().
     */
    private void updateStatus() {
        if (appService.isGameOver()) {
            text = String.format("%s won", appService.getWinner().getName());
        } else {
            text = String.format("%s's turn", appService.getCurrentPlayer().getName());
        }

        repaint();
    }

    /**
     * Establece un mensaje de error.
     * Este método recibe un mensaje de error como parámetro y lo asigna a la variable
     * `error`, que puede ser utilizada posteriormente para mostrar el mensaje de error
     * en la interfaz de usuario o en otro lugar dentro de la aplicación.
     * @param errorMgs El mensaje de error que se va a establecer.
     */
    public void setError(String errorMgs) {
        error = errorMgs;
    }

    /**
     * Maneja los eventos del dominio y actualiza el estado si es necesario.
     * Este metodo recibe un evento y, si el evento es una instancia de `CardPlayed`,
     * `CardDrawn` o `GameOver`, se actualiza el estado llamando al metodo `updateStatus()`.
     * @param event El evento del dominio que se va a manejar.
     */
    @Override
    public void handleEvent(DomainEvent event) {
        if (event instanceof CardPlayed
            || event instanceof CardDrawn
            || event instanceof GameOver) {
            updateStatus();
        }
    }
}
