package ui.view;

import application.IGameAppService;
import application.dto.PlayerInfoDTO;
import domain.card.Card;
import domain.card.CardType;
import domain.card.WildCard;
import domain.common.DomainEvent;
import domain.common.DomainEventPublisher;
import domain.common.DomainEventSubscriber;
import domain.game.DealerService;
import domain.game.events.CardDrawn;
import domain.game.events.CardPlayed;
import domain.game.events.GameOver;
import ui.common.StyleUtil;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Collectors;

/**
 * Vista del jugador que gestiona la interfaz de usuario para mostrar las cartas en mano,
 * los controles del jugador y manejar los eventos relacionados con el juego.
 * Esta clase es responsable de mostrar las cartas en mano del jugador, así como de proporcionar
 * botones para que el jugador pueda dibujar una carta o decir "UNO" cuando corresponda.
 * También maneja los eventos del dominio relacionados con el juego, como el juego terminado,
 * el turno del jugador y la acción de jugar una carta.
 */
public class PlayerView extends JPanel implements DomainEventSubscriber {
    /// Panel donde se visualizan las cartas en mano del jugador.
    private JLayeredPane handCardsView;
    /// Panel de controles del jugador, que incluye botones y el nombre.
    private Box controlPanel;
    /// Etiqueta que muestra el nombre del jugador.
    private JLabel nameLabel;
    /// Botón que permite al jugador dibujar una carta.
    private JButton drawButton;
    /// Botón que permite al jugador decir "UNO".
    private JButton sayUnoButton;
    /// Indica si el jugador ha dicho "UNO".
    private boolean hasSaidUno = false;

    private final PlayerInfoDTO player;

    private final IGameAppService appService;

    /**
     * Constructor de la vista del jugador.
     *
     * @param player Información del jugador.
     * @param appService Servicio que gestiona la lógica del juego.
     */
    public PlayerView(PlayerInfoDTO player, IGameAppService appService) {
        this.player = player;
        this.appService = appService;

        initView();
        DomainEventPublisher.subscribe(this);
    }

    /**
     * Inicializa la vista del jugador, incluyendo las cartas en mano y los controles.
     * Configura el diseño de los componentes y los agrega al panel.
     */
    private void initView() {
        Box layout = Box.createHorizontalBox();

        initHandCardsView();
        initControlPanel();

        layout.add(handCardsView);
        layout.add(Box.createHorizontalStrut(20));
        layout.add(controlPanel);
        add(layout);

        setOpaque(false);

        refresh();
    }

    /**
     * Inicializa el panel donde se muestran las cartas en mano del jugador.
     */
    private void initHandCardsView() {
        handCardsView = new JLayeredPane();
        handCardsView.setPreferredSize(new Dimension(600, 175));
        handCardsView.setOpaque(false);
    }

    /**
     * Renderiza las cartas en mano en el panel correspondiente.
     * Ajusta la posición y el desplazamiento de las cartas según el número de cartas en mano.
     */
    private void renderHandCardsView() {
        handCardsView.removeAll();

        var handCards = appService.getHandCards(player.getId()).collect(Collectors.toList());

        Point originPoint = getFirstCardPoint(handCards.size());
        int offset = calculateOffset(handCardsView.getWidth(), handCards.size());

        int i = 0;
        for (var card : handCards) {
            var cardView = new CardView(card, this::playCard);

            cardView.setBounds(originPoint.x, originPoint.y,
                cardView.getDimension().width, cardView.getDimension().height);
            handCardsView.add(cardView, i++);
            handCardsView.moveToFront(cardView);

            originPoint.x += offset;
        }

        handCardsView.revalidate();
    }

    /**
     * Obtiene la posición inicial de la primera carta según el número total de cartas.
     *
     * @param totalCards El número total de cartas en mano.
     * @return La posición de la primera carta.
     */
    private Point getFirstCardPoint(int totalCards) {
        Point p = new Point(0, 20);
        if (totalCards < DealerService.TOTAL_INITIAL_HAND_CARDS) {
            var width = handCardsView.getWidth() == 0 ? handCardsView.getPreferredSize().width : handCardsView.getWidth();

            var offset = calculateOffset(width, totalCards);
            p.x = (width - offset * totalCards) / 2;
        }
        return p;
    }

    /**
     * Calcula el desplazamiento entre las cartas en función del número total de cartas
     * y el ancho disponible para mostrar las cartas.
     *
     * @param width El ancho disponible para las cartas.
     * @param totalCards El número total de cartas en mano.
     * @return El desplazamiento calculado entre las cartas.
     */
    private int calculateOffset(int width, int totalCards) {
        if (totalCards <= DealerService.TOTAL_INITIAL_HAND_CARDS) {
            return 71;
        } else {
            return (width - 100) / (totalCards - 1);
        }
    }

    /**
     * Inicializa el panel de controles del jugador, incluyendo el botón para dibujar
     * una carta, el botón para decir "UNO" y la etiqueta con el nombre del jugador.
     */
    private void initControlPanel() {
        initDrawButton();
        initSayNoButton();
        initNameLabel();

        controlPanel = Box.createVerticalBox();
        controlPanel.add(nameLabel);
        controlPanel.add(drawButton);
        controlPanel.add(Box.createVerticalStrut(15));
        controlPanel.add(sayUnoButton);
    }

    /**
     * Muestra u oculta el panel de controles en función de si es el turno del jugador
     * y si el juego ha terminado.
     */
    private void toggleControlPanel() {
        var isMyTurn = appService.getCurrentPlayer().getId().equals(player.getId());

        if (appService.isGameOver()) {
            isMyTurn = false;
        }

        drawButton.setVisible(isMyTurn);
        sayUnoButton.setVisible(isMyTurn);

        controlPanel.revalidate();
    }

    /**
     * Inicializa la etiqueta que muestra el nombre del jugador.
     */
    private void initNameLabel() {
        nameLabel = new JLabel(player.getName());
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font(StyleUtil.DEFAULT_FONT, Font.BOLD, 15));
    }

    /**
     * Inicializa el botón para decir "UNO".
     */
    private void initSayNoButton() {
        sayUnoButton = new JButton("Say UNO");
        sayUnoButton.setBackground(new Color(149, 55, 53));
        sayUnoButton.setFont(new Font(StyleUtil.DEFAULT_FONT, Font.BOLD, 20));
        sayUnoButton.setFocusable(false);

        sayUnoButton.addActionListener(e -> hasSaidUno = true);
    }

    /**
     * Inicializa el botón para dibujar una carta.
     */
    private void initDrawButton() {
        drawButton = new JButton("Draw");

        drawButton.setBackground(new Color(79, 129, 189));
        drawButton.setFont(new Font(StyleUtil.DEFAULT_FONT, Font.BOLD, 20));
        drawButton.setFocusable(false);

        drawButton.addActionListener(e -> appService.drawCard(player.getId()));
    }

    /**
     * Metodo que permite jugar una carta seleccionada por el jugador.
     * Si la carta es una carta comodín, permite elegir un color antes de jugarla.
     *
     * @param selectedCard La carta seleccionada por el jugador.
     */
    private void playCard(Card selectedCard) {
        Card cardToPlay = selectedCard;

        if (selectedCard.getType() == CardType.WILD_COLOR || selectedCard.getType() == CardType.WILD_DRAW_FOUR) {
            var chosenColor = ColorPicker.getInstance().show();
            cardToPlay = new WildCard(selectedCard.getType(), chosenColor);
        }

        appService.playCard(player.getId(), cardToPlay, hasSaidUno);
        hasSaidUno = false;
    }

    /**
     * Refresca la vista actualizando las cartas en mano y el estado de los controles.
     */
    private void refresh() {
        renderHandCardsView();
        toggleControlPanel();

        repaint();
    }

    /**
     * Maneja los eventos del dominio relacionados con el juego, como cuando se juega
     * una carta, se roba una carta o se termina el juego.
     *
     * @param event El evento del dominio que se ha producido.
     */
    @Override
    public void handleEvent(DomainEvent event) {
        if (event instanceof CardPlayed
            || event instanceof CardDrawn
            || event instanceof GameOver) {
            refresh();
        }
    }
}
