package domain.game;

import domain.card.*;
import domain.player.Player;
import domain.player.PlayerRoundIterator;
import domain.testhelper.CardTestFactory;
import domain.testhelper.PlayerTestFactory;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Pruebas relacionadas con el inicio del juego.
 */
class TestGameStart {
    private final PlayerRoundIterator jugadores = new PlayerRoundIterator(PlayerTestFactory.createPlayers(3));

    /**
     * Verifica que cuando se juega una carta numérica, no haya efectos adicionales.
     */
    @Test
    void CuandoCartaNumericaEsJugada_NoDeberiaHaberEfecto() {
        var juego = crearJuego(CardTestFactory.createNumberCard());

        verificarEstadoDelJuego(juego, CardType.NUMBER, "1");
    }

    /**
     * Verifica que cuando se juega una carta "Skip", el jugador actual sea omitido.
     */
    @Test
    void CuandoCartaSkipEsJugado_ElJugadorActualDeberiaSerOmitido() {
        var juego = crearJuego(CardTestFactory.createSkipCard());

        verificarEstadoDelJuego(juego, CardType.SKIP, "2");
    }

    /**
     * Verifica que cuando se juega una carta "Reverse", la dirección del juego se invierta.
     */
    @Test
    void CuandoCartaReverseEsJugado_LaDireccionDeberiaInvertirse() {
        var juego = crearJuego(CardTestFactory.createReverseCard());

        verificarEstadoDelJuego(juego, CardType.REVERSE, "3");
    }

    /**
     * Verifica que cuando se juega una carta "Draw Two", el primer jugador reciba dos cartas.
     */
    @Test
    void CuandoCartaDrawTwoEsJugado_ElPrimerJugadorDeberiaRecibirDosCartas() {
        var juego = crearJuego(
            CardTestFactory.createNumberCard(),
            CardTestFactory.createSkipCard(),
            CardTestFactory.createDrawTwoCard());

        var jugadorAnterior = jugadores.stream().toArray(Player[]::new)[0];

        verificarEstadoDelJuego(juego, CardType.DRAW_TWO, "2");
        assertEquals(2, jugadorAnterior.getHandCards().count());
    }

    /**
     * Verifica que cuando se juega una carta "Wild Color", no haya efectos adicionales.
     */
    @Test
    void CuandoCartaWildColorEsJugado_NoDeberiaHaberEfecto() {
        var juego = crearJuego(CardTestFactory.createWildColorCard());

        verificarEstadoDelJuego(juego, CardType.WILD_COLOR, "1");
    }

    /**
     * Verifica que si solo hay una carta y se intenta jugar una carta "Wild Draw Four", se arroje un error.
     */
    @Test
    void DadoSoloUnaCarta_CuandoCartaWildDrawFourEsJugado_DeberiaArrojarError() {
        assertThrows(IllegalStateException.class, () -> crearJuego(CardTestFactory.createWildDrawFourCard()));
    }

    /**
     * Verifica que cuando se juega una carta "Wild Draw Four", se baraje hasta encontrar una carta jugable.
     */
    @Test
    void CuandoCartaWildDrawFourEsJugado_DeberiaBarajarHastaEncontrarCartaJugable() {
        var juego = crearJuego(CardTestFactory.createNumberCard(), CardTestFactory.createWildDrawFourCard());

        verificarEstadoDelJuego(juego, CardType.NUMBER, "1");
    }

    /**
     * Crea un juego utilizando una pila de robo con las cartas especificadas.
     * @param cartas Las cartas que se añadirán a la pila de robo.
     * @return Una instancia del juego inicializada.
     */
    private Game crearJuego(Card... cartas) {
        var pilaDeRobo = crearPilaDeRobo(cartas);

        return new Game(pilaDeRobo, jugadores);
    }

    /**
     * Crea una pila de robo a partir de un conjunto de cartas.
     * @param cartas Las cartas que formarán la pila de robo.
     * @return Una instancia de DrawPile con las cartas especificadas.
     */
    private DrawPile crearPilaDeRobo(Card... cartas) {
        return new DrawPile(Arrays.asList(cartas));
    }

    /**
     * Verifica el estado actual del juego.
     * @param juego El juego que se está verificando.
     * @param tipoDeCartaJugado El tipo de carta que se espera que esté en la parte superior de la pila de descarte.
     * @param jugadorActualEsperado El nombre del jugador que se espera que tenga el turno actual.
     */
    private void verificarEstadoDelJuego(Game juego, CardType tipoDeCartaJugado, String jugadorActualEsperado) {
        assertEquals(tipoDeCartaJugado, juego.peekTopCard().getType());
        assertEquals(jugadorActualEsperado, juego.getCurrentPlayer().getName());
    }

    /**
     * Arroja una excepción cuando la pila de robo está vacía
     */
    @Test
    void CuandoPilaDeRoboEstaVacia_DeberiaArrojarExcepcion() {
        var pilaVacia = new DrawPile(Collections.emptyList());
        assertThrows(EmptyStackException.class, () -> new Game(pilaVacia, jugadores));
    }

    /**
     * Aplica efectos en cadena cuando se juega una carta DrawTwo
     */
    @Test
    void CuandoSeJuegaDrawTwoSeguidoDeSkip_DeberiaAplicarEfectosEnCadena() {
        var juego = crearJuego(
            CardTestFactory.createDrawTwoCard(),
            CardTestFactory.createSkipCard()
        );

        verificarEstadoDelJuego(juego, CardType.SKIP, "2");
        assertEquals(0, jugadores.getCurrentPlayer().getHandCards().count());
    }
}
