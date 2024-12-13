package domain.game;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.UUID;

public class GameUI {
    private final Game game;
    private final JFrame frame;
    private final JPanel playerMovePanel;
    private final JLabel gameDurationLabel;

    public GameUI(Game game) {
        this.game = game;

        // Crear ventana principal
        frame = new JFrame("Estado del Juego");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Crear panel para movimientos de jugadores
        playerMovePanel = new JPanel();
        playerMovePanel.setLayout(new GridLayout(0, 1)); // Una fila por jugador

        // Crear etiqueta para duración del juego
        gameDurationLabel = new JLabel("Duración de la partida: 0 minutos y 0 segundos", SwingConstants.CENTER);

        // Añadir componentes a la ventana
        frame.add(playerMovePanel, BorderLayout.CENTER);
        frame.add(gameDurationLabel, BorderLayout.SOUTH);

        // Mostrar la ventana
        frame.setVisible(true);
    }

    // Método para actualizar los movimientos de cada jugador
    public void updatePlayerMoves() {
        SwingUtilities.invokeLater(() -> {
            playerMovePanel.removeAll(); // Limpiar el panel
            Map<UUID, Integer> playerMoves = game.getPlayerMoves(); // Obtener movimientos
            game.getPlayers().forEach(player -> {
                String playerName = player.getName();
                int moves = playerMoves.getOrDefault(player.getId(), 0);
                JLabel playerMoveLabel = new JLabel(playerName + ": " + moves + " movimientos", SwingConstants.CENTER);
                playerMovePanel.add(playerMoveLabel);
            });
            playerMovePanel.revalidate();
            playerMovePanel.repaint();
        });
    }

    // Método para actualizar el tiempo de la partida
    public void updateGameDuration() {
        SwingUtilities.invokeLater(() -> {
            String durationText = game.getGameDuration();
            gameDurationLabel.setText("Duración de la partida: " + durationText);
        });
    }
    public String getgameDurationLabel() {
        return gameDurationLabel.getText();
    }
}
