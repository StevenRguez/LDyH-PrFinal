package ui;

import application.IGameAppService;
import ui.view.PlayerView;
import ui.view.TableView;

import javax.swing.*;
import java.awt.*;

/**
 * Clase principal de la interfaz gráfica que extiende JFrame.
 * Representa la ventana principal de la aplicación.
 */
public class AppFrame extends JFrame {

    /**
     * Panel principal que contiene el diseño de la interfaz.
     */
    private final JPanel mainLayout;

    /**
     * Servicio de aplicación para manejar la lógica del juego.
     */
    private final IGameAppService appService;

    /**
     * Constructor que inicializa la ventana principal de la aplicación.
     * Configura el diseño de la interfaz y muestra la ventana.
     *
     * @param appService instancia de IGameAppService para interactuar con la lógica del juego.
     */
    public AppFrame(IGameAppService appService) {
        this.appService = appService;

        mainLayout = new JPanel();
        setupLayout();

        showFrame();
    }

    /**
     * Configura las propiedades básicas de la ventana y la hace visible.
     */
    private void showFrame() {
        setVisible(true);
        setResizable(false);
        setLocation(200, 100);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Configura el diseño y los componentes de la interfaz gráfica.
     * Añade las vistas de los jugadores y la vista de la mesa al diseño principal.
     */
    private void setupLayout() {
        mainLayout.setPreferredSize(new Dimension(960, 720));
        mainLayout.setBackground(new Color(30, 36, 40));
        mainLayout.setLayout(new BorderLayout());

        // Esta aplicación de escritorio solo soporta juego para dos jugadores
        var players = appService.getPlayerInfos();
        var playerView1 = new PlayerView(players.get(0), appService);
        var playerView2 = new PlayerView(players.get(1), appService);

        var tableView = new TableView(appService);

        mainLayout.add(playerView1, BorderLayout.SOUTH);
        mainLayout.add(tableView, BorderLayout.CENTER);
        mainLayout.add(playerView2, BorderLayout.NORTH);
        add(mainLayout);
    }
}