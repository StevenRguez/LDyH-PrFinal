package domain.game.events;

import domain.card.Card;
import domain.common.DomainEvent;

import java.util.UUID;

/**
 * Evento que representa que un jugador ha jugado una carta.
 */
public class CardPlayed extends DomainEvent {
    /**
     * Almacena el ID del usuario
     */
    private final UUID playerId;

    /**
     * Almacena la carta del usuario
     */
    private final Card playedCard;

    /**
     * Crea una instancia del evento.
     * @param playerId identificador del jugador que ha jugado la carta.
     * @param playedCard carta jugada.
     */
    public CardPlayed(UUID playerId, Card playedCard) {
        this.playerId = playerId;
        this.playedCard = playedCard;
    }

    /**
     * Devuelve el identificador del jugador que ha jugado la carta.
     * @return identificador del jugador que ha jugado la carta.
     */
    public UUID getPlayerId() {
        return playerId;
    }

    /**
     * Devuelve la carta jugada.
     * @return carta jugada.
     */
    public Card getPlayedCard() {
        return playedCard;
    }
}
