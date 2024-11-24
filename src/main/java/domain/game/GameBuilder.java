package domain.game;

import domain.card.Card;
import domain.card.CardDeck;
import domain.player.Player;
import domain.player.PlayerRoundIterator;

import java.util.ArrayList;
import java.util.List;

/**
 * @brief Clase utilizada para construir una instancia de un juego configurado con jugadores y un mazo de cartas.
 */
public class GameBuilder {
    private List<String> playerNames = new ArrayList<>();

    /**
     * @brief Añade un jugador al juego en construcción.
     * 
     * @param name Nombre del jugador.
     * @return La instancia actual de GameBuilder para encadenamiento de métodos.
     */
    public GameBuilder withPlayer(String name) {
        playerNames.add(name);
        return this;
    }

    /**
     * @brief Construye una instancia del juego con los jugadores y mazo configurados.
     * 
     * @return Una nueva instancia de la clase Game.
     */
    public Game build() {
        var cards = new CardDeck().getImmutableCards(); // Obtiene un mazo de cartas inmutable.

        var drawPile = buildDrawPile(cards); // Construye el mazo de robar.
        var players = buildPlayers(drawPile); // Configura los jugadores.

        return new Game(drawPile, players);
    }

    /**
     * @brief Crea el mazo de robar a partir de un mazo de cartas inicial.
     * 
     * @param cards Lista de cartas inicial.
     * @return Una instancia de DrawPile con las cartas barajadas.
     */
    private DrawPile buildDrawPile(List<Card> cards) {
        var shuffledCards = DealerService.shuffle(cards); // Baraja las cartas.
        return new DrawPile(shuffledCards); // Crea el mazo de robar.
    }

    /**
     * @brief Crea una iteración circular de jugadores, asignándoles cartas iniciales.
     * 
     * @param drawPile Mazo de robar utilizado para repartir cartas.
     * @return Una instancia de PlayerRoundIterator que contiene los jugadores.
     * @throws IllegalStateException Si el número de jugadores es menor a 2 o mayor a 10.
     */
    private PlayerRoundIterator buildPlayers(DrawPile drawPile) {
        if (playerNames.size() < 2 || playerNames.size() > 10) {
            throw new IllegalStateException("Se requieren entre 2 y 10 jugadores para crear un juego");
        }

        // Reparte las cartas iniciales a los jugadores.
        var handCardLists = DealerService.dealInitialHandCards(drawPile, playerNames.size());
        var players = new Player[playerNames.size()];

        // Crea los jugadores con sus nombres y manos iniciales.
        for (int i = 0; i < playerNames.size(); i++) {
            players[i] = new Player(playerNames.get(i), handCardLists[i]);
        }

        // Devuelve el iterador circular de jugadores.
        return new PlayerRoundIterator(players);
    }
}
