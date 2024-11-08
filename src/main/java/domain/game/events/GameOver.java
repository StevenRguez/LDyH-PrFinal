package domain.game.events;

import domain.common.DomainEvent;
import domain.player.ImmutablePlayer;

/**
 * Evento que representa el final de la partida.
 */
public class GameOver extends DomainEvent {
    private ImmutablePlayer winner;

    /**
     * Crea una instancia del evento.
     * @param winner jugador ganador.
     */
    public GameOver(ImmutablePlayer winner) {
        this.winner = winner;
    }

    /**
     * Devuelve el jugador ganador.
     * @return jugador ganador.
     */
    public ImmutablePlayer getWinner() {
        return winner;
    }
}
