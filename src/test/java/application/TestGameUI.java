package application;

import domain.card.Card;
import domain.card.CardColor;
import domain.card.NumberCard;
import domain.game.DrawPile;
import domain.game.GameUI;
import domain.game.Game;

import javax.swing.*;

import java.util.*;

import domain.player.PlayerRoundIterator;
import domain.testhelper.PlayerTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para la interfaz de usuario del juego (GameUI).
 *
 * Esta clase verifica el comportamiento de la clase GameUI utilizando pruebas unitarias.
 * Se asegura de que las funciones relacionadas con la duración del juego y otras interacciones
 * funcionen correctamente.
 */
class TestGameUI {

    /**
     * Instancia del juego que se utiliza para las pruebas.
     */
    private Game game;

    /**
     * Lista de cartas mezcladas utilizadas para inicializar el mazo de robar.
     */
    private final List<Card> shuffledCards = new ArrayList<>(Arrays.asList(
            new NumberCard(1, CardColor.BLUE),
            new NumberCard(2, CardColor.RED),
            new NumberCard(3, CardColor.GREEN),
            new NumberCard(4, CardColor.YELLOW),
            new NumberCard(5, CardColor.BLUE),
            new NumberCard(6, CardColor.RED),
            new NumberCard(7, CardColor.GREEN)
    ));

    /**
     * Mazo de robar que contiene las cartas mezcladas.
     */
    private DrawPile drawPile = new DrawPile(shuffledCards);

    /**
     * Iterador de los jugadores que participan en la ronda.
     */
    private PlayerRoundIterator players;

    /**
     * Instancia de la interfaz de usuario del juego que se prueba.
     */
    private GameUI gameUI;

    /**
     * Iterador de jugadores, creado con un conjunto de jugadores de prueba.
     */
    private final PlayerRoundIterator jugadores = new PlayerRoundIterator(PlayerTestFactory.createPlayers(3));

    /**
     * Configura el entorno de prueba inicializando las instancias necesarias
     * antes de ejecutar cada caso de prueba.
     */
    @BeforeEach
    void setUp() {
        game = new Game(drawPile, jugadores);
        gameUI = new GameUI(game);
    }

    /**
     * Verifica que la duración del juego se actualice correctamente en la interfaz de usuario.
     *
     * Comprueba que el texto de la etiqueta de duración sea "Duración de la partida: 0 minutos y 0 segundos"
     * al inicio del juego.
     */
    @Test
    void testUpdateGameDuration() {
        gameUI.updateGameDuration();
        assertEquals("Duración de la partida: 0 minutos y 0 segundos", gameUI.getgameDurationLabel());
    }
}