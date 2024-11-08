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
 * @brief Interfaz que define el servicio de aplicación para interactuar con una sesión de juego.
 *
 * Proporciona métodos para recuperar información de los jugadores, gestionar acciones de los mismos y consultar el estado del juego.
 * Este servicio está diseñado para separar la capa de aplicación de la capa de dominio.
 */
public interface IGameAppService extends Serializable {

    /**
     * @brief Recupera una lista de objetos PlayerInfoDTO que contiene información básica de cada jugador.
     *
     * @return Lista de objetos PlayerInfoDTO con los IDs y nombres de los jugadores.
     */
    List<PlayerInfoDTO> getPlayerInfos();

    /**
     * @brief Recupera la información del jugador actual.
     *
     * @return Objeto PlayerInfoDTO que contiene el ID y el nombre del jugador actual.
     */
    PlayerInfoDTO getCurrentPlayer();

    /**
     * @brief Recupera las cartas en mano de un jugador específico.
     *
     * @param playerId UUID del jugador cuyas cartas en mano se van a recuperar.
     * @return Stream de objetos Card que representan las cartas en mano del jugador.
     */
    Stream<Card> getHandCards(UUID playerId);

    /**
     * @brief Permite a un jugador jugar una carta, con la opción de declarar "UNO".
     *
     * @param playerId UUID del jugador que juega la carta.
     * @param card Objeto Card que representa la carta a jugar.
     * @param hasSaidUno Booleano que indica si el jugador ha declarado "UNO".
     */
    void playCard(UUID playerId, Card card, boolean hasSaidUno);

    /**
     * @brief Permite a un jugador robar una carta del mazo.
     *
     * @param playerId UUID del jugador que roba la carta.
     */
    void drawCard(UUID playerId);

    /**
     * @brief Recupera la carta superior de la pila de descarte sin retirarla.
     *
     * @return Objeto Card que representa la carta superior de la pila de descarte.
     */
    Card peekTopCard();

    /**
     * @brief Verifica si el juego ha terminado.
     *
     * @return Valor booleano que indica si el juego ha finalizado.
     */
    boolean isGameOver();

    /**
     * @brief Recupera el jugador ganador, si el juego ha terminado.
     *
     * @return Objeto ImmutablePlayer que representa al ganador, o null si el juego no ha finalizado.
     */
    ImmutablePlayer getWinner();
}
