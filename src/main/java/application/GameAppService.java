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
 * @brief Servicio de aplicación que actúa como intermediario entre el dominio del juego y la capa de presentación.
 *
 * Este servicio proporciona diversos métodos para interactuar y manipular una sesión de juego,
 * incluyendo acciones de jugadores, información del estado del juego y registro de eventos.
 */
public class GameAppService implements IGameAppService {
    /**
     * @brief Logger para registrar eventos relacionados con el juego y para fines de depuración.
     */
    private static final Logger logger = LogManager.getLogger("GameAppService");

    /**
     * @brief Instancia principal del juego que está siendo gestionada por el servicio de aplicación.
     */
    private final Game game;

    /**
     * @brief Construye un nuevo GameAppService e inicializa un juego con jugadores predeterminados.
     */
    public GameAppService() {
        game = new GameBuilder()
            .withPlayer("Jugador 1")
            .withPlayer("Jugador 2")
            .build();

        logGameInfo();
    }

    /**
     * @brief Registra información inicial del juego, incluyendo los jugadores y sus cartas en mano.
     *
     * Este método se utiliza internamente para proporcionar información detallada sobre el estado del juego
     * al momento de su creación.
     */
    private void logGameInfo() {
        logger.info("Juego creado exitosamente");
        game.getPlayers().forEach(p -> {
            var joinedCardValues = p.getHandCards()
                .map(Object::toString)
                .collect(Collectors.joining(","));

            logger.debug(String.format("Jugador %s con %s cartas => [%s]", p.getName(), p.getTotalCards(), joinedCardValues));
        });
    }

    /**
     * @brief Recupera una lista de objetos PlayerInfoDTO que representan la información básica de cada jugador.
     *
     * @return Lista de objetos PlayerInfoDTO con los IDs y nombres de los jugadores.
     */
    @Override
    public List<PlayerInfoDTO> getPlayerInfos() {
        return game.getPlayers()
            .map(p -> new PlayerInfoDTO(p.getId(), p.getName()))
            .collect(Collectors.toList());
    }

    /**
     * @brief Recupera la información del jugador actual.
     *
     * @return Objeto PlayerInfoDTO que contiene el ID y el nombre del jugador actual.
     */
    @Override
    public PlayerInfoDTO getCurrentPlayer() {
        var currentPlayer = game.getCurrentPlayer();
        return new PlayerInfoDTO(currentPlayer.getId(), currentPlayer.getName());
    }

    /**
     * @brief Recupera las cartas en mano de un jugador específico a partir de su UUID.
     *
     * @param playerId UUID del jugador cuyas cartas en mano se van a recuperar.
     * @return Stream de objetos Card que representan las cartas en mano del jugador.
     */
    @Override
    public Stream<Card> getHandCards(UUID playerId) {
        return game.getHandCards(playerId);
    }

    /**
     * @brief Permite a un jugador jugar una carta, con la opción de declarar "UNO".
     *
     * @param playerId UUID del jugador que juega la carta.
     * @param card Objeto Card que representa la carta a jugar.
     * @param hasSaidUno Booleano que indica si el jugador ha declarado "UNO".
     */
    @Override
    public void playCard(UUID playerId, Card card, boolean hasSaidUno) {
        var message = String.format("El jugador %s juega %s %s", playerId, card, hasSaidUno ? "y dijo UNO!!!" : "");
        logger.info(message);
        game.playCard(playerId, card, hasSaidUno);
    }

    /**
     * @brief Permite a un jugador robar una carta del mazo.
     *
     * @param playerId UUID del jugador que roba la carta.
     */
    @Override
    public void drawCard(UUID playerId) {
        var message = String.format("El jugador %s roba una carta", playerId);
        logger.info(message);
        game.drawCard(playerId);
    }

    /**
     * @brief Recupera la carta superior de la pila de descarte sin retirarla.
     *
     * @return Objeto Card que representa la carta superior de la pila de descarte.
     */
    @Override
    public Card peekTopCard() {
        return game.peekTopCard();
    }

    /**
     * @brief Verifica si el juego ha finalizado.
     *
     * @return Valor booleano que indica si el juego ha terminado.
     */
    @Override
    public boolean isGameOver() {
        return game.isOver();
    }

    /**
     * @brief Recupera el jugador ganador, si el juego ha finalizado.
     *
     * @return Objeto ImmutablePlayer que representa al ganador, o null si el juego no ha finalizado.
     */
    @Override
    public ImmutablePlayer getWinner() {
        return game.getWinner();
    }
}
