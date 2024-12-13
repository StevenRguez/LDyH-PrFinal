/**
 * Clase de utilidad para la creación de objetos de jugadores para pruebas.
 * Proporciona métodos estáticos para generar jugadores con manos de cartas inicializadas.
 */
package domain.testhelper;

import domain.player.HandCardList;
import domain.player.Player;

public class PlayerTestFactory {

    /**
     * Crea un arreglo de jugadores con manos de cartas vacías.
     *
     * @param total el número total de jugadores a crear.
     * @return un arreglo de instancias de {@link Player} con manos de cartas inicializadas.
     */
    public static Player[] createPlayers(int total) {
        Player[] players = new Player[total];

        for (int i = 0; i < players.length; i++) {
            var handCards = new HandCardList();
            players[i] = new Player(String.format("%s", i + 1), handCards);
        }

        return players;
    }
}
