package domain.game.events;

import domain.common.DomainEvent;

import java.util.UUID;

/**
 * Evento que representa que un jugador ha robado una carta.
 */
public class CardDrawn extends DomainEvent {
    private final UUID playerId;

    /**
     * Crea una instancia del evento.
     * @param playerId identificador del jugador que ha robado la carta.
     */
    public CardDrawn(UUID playerId){
        this.playerId = playerId;
    }

    /**
     * Devuelve el identificador del jugador que ha robado la carta.
     * @return identificador del jugador que ha robado la carta.
     */
    public UUID getPlayerId() {
        return playerId;
    }
}
