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

class TestGameUI {
    private Game game;
    // rellenamos un arraylist de cartas con 7 cartas aleatorias
    private final List<Card> shuffledCards = new ArrayList<>(Arrays.asList(
            new NumberCard(1, CardColor.BLUE),
            new NumberCard(2, CardColor.RED),
            new NumberCard(3, CardColor.GREEN),
            new NumberCard(4, CardColor.YELLOW),
            new NumberCard(5, CardColor.BLUE),
            new NumberCard(6, CardColor.RED),
            new NumberCard(7, CardColor.GREEN)
    ));

    private DrawPile drawPile = new DrawPile(shuffledCards);

    PlayerRoundIterator players;
    GameUI gameUI;

    private final PlayerRoundIterator jugadores = new PlayerRoundIterator(PlayerTestFactory.createPlayers(3));

    @BeforeEach
    void setUp() {
        game = new Game(drawPile, jugadores);
        gameUI = new GameUI(game);
    }

    @Test
    // comprobamos lo del tiempo
    void testUpdateGameDuration() {
        gameUI.updateGameDuration();
        assertEquals("Duración de la partida: 0 minutos y 0 segundos", gameUI.getgameDurationLabel());
    }
}