package ui.view;

import application.IGameAppService;
import domain.card.Card;
import domain.common.DomainEvent;
import domain.common.DomainEventPublisher;
import domain.common.DomainEventSubscriber;
import domain.game.events.CardPlayed;
import ui.common.StyleUtil;

import javax.swing.*;
import java.awt.*;

/**
 * Representa la vista de la mesa de juego en la interfaz de usuario.
 * Esta clase se encarga de mostrar la carta superior de la pila de descarte y
 * proporciona información sobre el estado actual del juego.
 * **Diseño:**
 * La clase utiliza un `GridBagLayout` para organizar los componentes de la vista.
 * El panel `table` contiene la carta superior, mientras que `GameStatusView` muestra
 * información adicional sobre el juego.
 * **Interacciones:**
 * La clase se suscribe a eventos de tipo `CardPlayed` para actualizar la vista
 * cada vez que se juega una carta.
 */
public class TableView extends JPanel implements DomainEventSubscriber {
    /// Panel que representa la mesa
    private final JPanel table;
    /// Esta variable almacena una referencia al servicio de aplicación del juego.
    private final IGameAppService appService;

    /**
     * Constructor de la clase
     * @param appService servicio
     */
    public TableView(IGameAppService appService){
        this.appService = appService;

        setOpaque(false);
        setLayout(new GridBagLayout());
        table = new JPanel();
        table.setBackground(new Color(64,64,64));

        initTable();
        initInfoView();

        DomainEventPublisher.subscribe(this);
    }
    /**
     * Inicializa el panel de la mesa con la carta superior de la pila de descarte.
     * **Precondición:** El servicio de aplicación del juego debe estar inicializado.
     * **Postcondición:** El panel `table` contiene una instancia de `CardView` que muestra
     * la carta superior.
     */
    private void initTable(){
        table.removeAll();

        table.setPreferredSize(new Dimension(500,200));
        table.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;

        Card topCard = appService.peekTopCard();
        Color background = StyleUtil.convertCardColor(topCard.getColor());

        var cardView = new CardView(topCard);
        table.add(cardView, c);

        table.setBackground(background);
        table.revalidate();
    }

    private void initInfoView() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 130, 0, 45);
        add(table,c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_END;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 1, 0, 1);

        add(new GameStatusView(appService), c);
    }

    @Override
    public void handleEvent(DomainEvent event) {
        if(event instanceof CardPlayed) {
            initTable();
        }
    }
}
