package application;

import application.dto.PlayerInfoDTO;
import domain.card.Card;
import domain.game.Game;
import domain.game.GameBuilder;
import domain.player.ImmutablePlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @class GameAppService
 * @brief Application service that acts as an intermediary between the game domain and the presentation layer.
 *
 * This service provides various methods to interact with and manipulate a game session,
 * including player actions, game state information, and logging.
 */
public class GameAppService implements IGameAppService {
    /**
     * @brief Logger for logging game-related events and debug information.
     */
    private static final Logger logger = LogManager.getLogger("GameAppService");

    /**
     * @brief The core game instance being managed by the application service.
     */
    private final Game game;

    /**
     * @brief Constructs a new GameAppService and initializes a new game with default players.
     */
    public GameAppService() {
        game = new GameBuilder()
            .withPlayer("Player 1")
            .withPlayer("Player 2")
            .build();

        logGameInfo();
    }

    /**
     * @brief Logs initial game information, including players and their hand cards.
     *
     * This method is used internally to provide detailed information about the game
     * state when it is first created.
     */
    private void logGameInfo() {
        logger.info("Game created successfully");
        game.getPlayers().forEach(p -> {
            var joinedCardValues = p.getHandCards()
                .map(Object::toString)
                .collect(Collectors.joining(","));

            logger.debug(String.format("Player %s with %s cards => [%s]", p.getName(), p.getTotalCards(), joinedCardValues));
        });
    }

    /**
     * @brief Retrieves a list of PlayerInfoDTO objects representing each player's basic information.
     *
     * @return List of PlayerInfoDTO objects containing player IDs and names.
     */
    @Override
    public List<PlayerInfoDTO> getPlayerInfos() {
        return game.getPlayers()
            .map(p -> new PlayerInfoDTO(p.getId(), p.getName()))
            .collect(Collectors.toList());
    }

    /**
     * @brief Retrieves the current player's information.
     *
     * @return PlayerInfoDTO representing the current player's ID and name.
     */
    @Override
    public PlayerInfoDTO getCurrentPlayer() {
        var currentPlayer = game.getCurrentPlayer();
        return new PlayerInfoDTO(currentPlayer.getId(), currentPlayer.getName());
    }

    /**
     * @brief Retrieves the hand cards of a specific player by their UUID.
     *
     * @param playerId UUID of the player whose hand cards are to be retrieved.
     * @return Stream of Card objects representing the player's hand cards.
     */
    @Override
    public Stream<Card> getHandCards(UUID playerId) {
        return game.getHandCards(playerId);
    }

    /**
     * @brief Allows a player to play a card, with the option to declare "UNO."
     *
     * @param playerId UUID of the player playing the card.
     * @param card Card object to be played.
     * @param hasSaidUno Boolean indicating if the player has declared "UNO."
     */
    @Override
    public void playCard(UUID playerId, Card card, boolean hasSaidUno) {
        var message = String.format("Player %s plays %s %s", playerId, card, hasSaidUno ? "and said UNO!!!" : "");
        logger.info(message);
        game.playCard(playerId, card, hasSaidUno);
    }

    /**
     * @brief Allows a player to draw a card from the deck.
     *
     * @param playerId UUID of the player drawing a card.
     */
    @Override
    public void drawCard(UUID playerId) {
        var message = String.format("Player %s draws a card", playerId);
        logger.info(message);
        game.drawCard(playerId);
    }

    /**
     * @brief Retrieves the top card of the discard pile without removing it.
     *
     * @return Card object representing the top card of the discard pile.
     */
    @Override
    public Card peekTopCard() {
        return game.peekTopCard();
    }

    /**
     * @brief Checks if the game has ended.
     *
     * @return Boolean value indicating if the game is over.
     */
    @Override
    public boolean isGameOver() {
        return game.isOver();
    }

    /**
     * @brief Retrieves the winning player, if the game has ended.
     *
     * @return ImmutablePlayer object representing the winner, or null if the game is not over.
     */
    @Override
    public ImmutablePlayer getWinner() {
        return game.getWinner();
    }
}