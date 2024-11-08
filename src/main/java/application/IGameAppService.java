package application;

import application.dto.PlayerInfoDTO;
import domain.card.Card;
import domain.player.ImmutablePlayer;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @interface IGameAppService
 * @brief Interface that defines the application service for interacting with a game session.
 *
 * Provides methods to retrieve player information, manage player actions, and query game status.
 * This service is designed to separate the application layer from the domain layer.
 */
public interface IGameAppService extends Serializable {

    /**
     * @brief Retrieves a list of PlayerInfoDTO objects containing basic information of each player.
     *
     * @return List of PlayerInfoDTO objects with player IDs and names.
     */
    List<PlayerInfoDTO> getPlayerInfos();

    /**
     * @brief Retrieves the current player's information.
     *
     * @return PlayerInfoDTO containing the current player's ID and name.
     */
    PlayerInfoDTO getCurrentPlayer();

    /**
     * @brief Retrieves the hand cards of a specific player.
     *
     * @param playerId UUID of the player whose hand cards are to be retrieved.
     * @return Stream of Card objects representing the player's hand cards.
     */
    Stream<Card> getHandCards(UUID playerId);

    /**
     * @brief Allows a player to play a card, with the option to declare "UNO."
     *
     * @param playerId UUID of the player playing the card.
     * @param card Card object to be played.
     * @param hasSaidUno Boolean indicating if the player has declared "UNO."
     */
    void playCard(UUID playerId, Card card, boolean hasSaidUno);

    /**
     * @brief Allows a player to draw a card from the deck.
     *
     * @param playerId UUID of the player drawing a card.
     */
    void drawCard(UUID playerId);

    /**
     * @brief Retrieves the top card from the discard pile without removing it.
     *
     * @return Card object representing the top card of the discard pile.
     */
    Card peekTopCard();

    /**
     * @brief Checks if the game has ended.
     *
     * @return Boolean value indicating if the game is over.
     */
    boolean isGameOver();

    /**
     * @brief Retrieves the winning player, if the game has ended.
     *
     * @return ImmutablePlayer object representing the winner, or null if the game is not over.
     */
    ImmutablePlayer getWinner();
}
