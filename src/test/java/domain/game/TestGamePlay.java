package domain.game;

import domain.card.Card;
import domain.card.CardColor;
import domain.card.CardUtil;
import domain.player.Player;
import domain.player.PlayerRoundIterator;
import domain.testhelper.CardTestFactory;
import domain.testhelper.PlayerTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TestGamePlay {
    private final Player[] jugadores = PlayerTestFactory.createPlayers(4);
    private final PlayerRoundIterator iteradorDeJugadores = new PlayerRoundIterator(jugadores);

    /**
     * @brief Verifica que si un jugador inválido intenta jugar, el juego lo rechace.
     */
    @Test
    void CuandoJugadorInvalidoJuega_DeberiaSerRechazado() {
        // Preparación
        var cartaParaJugar = CardTestFactory.createNumberCard();
        var juego = crearJuego(cartaParaJugar, CardTestFactory.createNumberCard());

        var jugadorParaJugar = jugadores[2].getId();

        // Acción y verificación
        assertThrows(IllegalArgumentException.class, () -> juego.playCard(jugadorParaJugar, cartaParaJugar));
    }

    /**
     * @brief Verifica que si un jugador intenta jugar una carta que no posee, el juego lo rechace.
     */
    @Test
    void CuandoCartaNoExistenteEsJugado_DeberiaSerRechazado() {
        // Preparación
        var cartaEnMano = CardTestFactory.createNumberCard(1, CardColor.RED);
        var cartaParaJugar = CardTestFactory.createNumberCard(2, CardColor.RED);

        var juego = crearJuego(cartaEnMano, CardTestFactory.createNumberCard());

        // Acción y verificación
        assertThrows(IllegalArgumentException.class, () -> jugarCartaDelJugadorActual(juego, cartaParaJugar));
    }

    /**
     * @brief Verifica que las cartas numéricas válidas sean aceptadas.
     */
    @ParameterizedTest
    @MethodSource("proveerCartasNumericasValidas")
    void CuandoCartaNumericaValidaEsJugado_DeberiaSerAceptado(Card cartaSuperior, Card cartaParaJugar) {
        // Preparación
        var juego = crearJuego(cartaParaJugar, cartaSuperior);

        // Acción
        jugarCartaDelJugadorActual(juego, cartaParaJugar);

        // Verificación
        verificarEstadoDelJuego(juego, cartaParaJugar, "2");
    }

    /**
     * @brief Provee un conjunto de combinaciones de cartas numéricas válidas.
     */
    private static Stream<Arguments> proveerCartasNumericasValidas() {
        var cartaParaJugar = CardTestFactory.createNumberCard(4, CardColor.BLUE);

        return Stream.of(
            Arguments.of(CardTestFactory.createNumberCard(5, CardColor.BLUE), cartaParaJugar),
            Arguments.of(CardTestFactory.createWildColorCard(), cartaParaJugar)
        );
    }

    /**
     * @brief Verifica que las cartas de tipo "Skip" válidas sean aceptadas.
     */
    @ParameterizedTest
    @MethodSource("proveerCartasSkipValidas")
    void CuandoCartaSkipValidaEsJugado_DeberiaSerAceptado(Card cartaSuperior, Card cartaParaJugar) {
        // Preparación
        var juego = crearJuego(cartaParaJugar, cartaSuperior);

        // Acción
        jugarCartaDelJugadorActual(juego, cartaParaJugar);

        // Verificación
        verificarEstadoDelJuego(juego, cartaParaJugar, "3");
    }

    /**
     * @brief Provee un conjunto de combinaciones de cartas "Skip" válidas.
     */
    private static Stream<Arguments> proveerCartasSkipValidas() {
        var cartaParaJugar = CardTestFactory.createSkipCard(CardColor.YELLOW);

        return Stream.of(
            Arguments.of(CardTestFactory.createNumberCard(5, CardColor.YELLOW), cartaParaJugar),
            Arguments.of(CardTestFactory.createWildColorCard(), cartaParaJugar)
        );
    }

    /**
     * @brief Verifica que las cartas de tipo "Reverse" válidas sean aceptadas.
     */
    @ParameterizedTest
    @MethodSource("proveerCartasReverseValidas")
    void CuandoCartaReverseValidaEsJugado_DeberiaSerAceptado(Card cartaSuperior, Card cartaParaJugar) {
        // Preparación
        var juego = crearJuego(cartaParaJugar, cartaSuperior);

        // Acción
        jugarCartaDelJugadorActual(juego, cartaParaJugar);

        // Verificación
        verificarEstadoDelJuego(juego, cartaParaJugar, "4");
    }

    /**
     * @brief Provee un conjunto de combinaciones de cartas "Reverse" válidas.
     */
    private static Stream<Arguments> proveerCartasReverseValidas() {
        var cartaParaJugar = CardTestFactory.createReverseCard(CardColor.YELLOW);

        return Stream.of(
            Arguments.of(CardTestFactory.createNumberCard(5, CardColor.YELLOW), cartaParaJugar),
            Arguments.of(CardTestFactory.createWildColorCard(), cartaParaJugar)
        );
    }

    /**
     * @brief Verifica que las cartas de tipo "Draw Two" válidas sean aceptadas.
     */
    @ParameterizedTest
    @MethodSource("proveerCartasDrawTwoValidas")
    void CuandoCartaDrawTwoValidaEsJugado_DeberiaSerAceptado(Card cartaSuperior, Card cartaParaJugar) {
        // Preparación
        var juego = crearJuego(
            cartaParaJugar,
            CardTestFactory.createNumberCard(),
            CardTestFactory.createNumberCard(),
            cartaSuperior);

        // Acción
        jugarCartaDelJugadorActual(juego, cartaParaJugar);

        // Verificación
        verificarEstadoDelJuego(juego, cartaParaJugar, "3");
        assertEquals(2, jugadores[1].getHandCards().count());
    }

    /**
     * @brief Provee un conjunto de combinaciones de cartas "Draw Two" válidas.
     */
    private static Stream<Arguments> proveerCartasDrawTwoValidas() {
        var cartaParaJugar = CardTestFactory.createDrawTwoCard(CardColor.YELLOW);

        return Stream.of(
            Arguments.of(CardTestFactory.createNumberCard(5, CardColor.YELLOW), cartaParaJugar),
            Arguments.of(CardTestFactory.createWildColorCard(), cartaParaJugar)
        );
    }

    /**
     * @brief Verifica que las cartas de tipo "Wild Color" válidas sean aceptadas.
     */
    @ParameterizedTest
    @MethodSource("proveerCartasWildColorValidas")
    void CuandoCartaWildColorValidaEsJugado_DeberiaSerAceptado(Card cartaSuperior, Card cartaParaJugar) {
        // Preparación
        var juego = crearJuego(cartaParaJugar, cartaSuperior);

        // Acción
        jugarCartaDelJugadorActual(juego, cartaParaJugar);

        // Verificación
        verificarEstadoDelJuego(juego, cartaParaJugar, "2");
    }

    /**
     * @brief Provee un conjunto de combinaciones de cartas "Wild Color" válidas.
     */
    private static Stream<Arguments> proveerCartasWildColorValidas() {
        var cartaParaJugar = CardTestFactory.createWildColorCard(CardColor.RED);

        return Stream.of(
            Arguments.of(CardTestFactory.createNumberCard(5, CardColor.YELLOW), cartaParaJugar),
            Arguments.of(CardTestFactory.createWildColorCard(), cartaParaJugar)
        );
    }

    /**
     * @brief Verifica que las cartas de tipo "Wild Draw Four" válidas sean aceptadas.
     */
    @ParameterizedTest
    @MethodSource("proveerCartasWildDrawFourValidas")
    void CuandoCartaWildDrawFourValidaEsJugado_DeberiaSerAceptado(Card cartaSuperior, Card cartaParaJugar) {
        // Preparación
        var juego = crearJuego(
            cartaParaJugar,
            CardTestFactory.createNumberCard(),
            CardTestFactory.createNumberCard(),
            CardTestFactory.createSkipCard(),
            CardTestFactory.createReverseCard(),
            cartaSuperior);

        // Acción
        jugarCartaDelJugadorActual(juego, cartaParaJugar);

        // Verificación
        verificarEstadoDelJuego(juego, cartaParaJugar, "3");
        assertEquals(4, jugadores[1].getHandCards().count());
    }

    /**
     * @brief Provee un conjunto de combinaciones de cartas "Wild Draw Four" válidas.
     */
    private static Stream<Arguments> proveerCartasWildDrawFourValidas() {
        var cartaParaJugar = CardTestFactory.createWildDrawFourCard(CardColor.RED);

        return Stream.of(
            Arguments.of(CardTestFactory.createNumberCard(5, CardColor.YELLOW), cartaParaJugar),
            Arguments.of(CardTestFactory.createWildColorCard(), cartaParaJugar)
        );
    }

    /**
     * @brief Crea un nuevo juego con las cartas proporcionadas.
     * @param cartaParaJugar Carta que el jugador actual puede jugar.
     * @param cartasDelMazo Cartas que forman el mazo del juego.
     * @return Instancia del juego.
     */
    private Game crearJuego(Card cartaParaJugar, Card... cartasDelMazo) {
        var mazo = crearMazo(cartasDelMazo);

        var juego = new Game(mazo, iteradorDeJugadores);

        var cartaAgregar = CardUtil.isWildCard(cartaParaJugar)
            ? CardTestFactory.createWildCard(cartaParaJugar.getType())
            : cartaParaJugar;

        iteradorDeJugadores.getCurrentPlayer().addToHandCards(cartaAgregar);

        return juego;
    }

    /**
     * @brief Crea un mazo de cartas con las cartas proporcionadas.
     * @param cartas Lista de cartas para el mazo.
     * @return Instancia del mazo.
     */
    private DrawPile crearMazo(Card... cartas) {
        return new DrawPile(Arrays.asList(cartas));
    }

    /**
     * @brief Permite que el jugador actual juegue una carta.
     * @param juego Instancia del juego.
     * @param cartaParaJugar Carta que se desea jugar.
     */
    private void jugarCartaDelJugadorActual(Game juego, Card cartaParaJugar) {
        juego.playCard(iteradorDeJugadores.getCurrentPlayer().getId(), cartaParaJugar);
    }

    /**
     * @brief Verifica el estado del juego.
     * @param juego Instancia del juego.
     * @param cartaSuperiorEsperada Carta superior esperada del mazo.
     * @param jugadorActualEsperado Nombre del jugador actual esperado.
     */
    private void verificarEstadoDelJuego(Game juego, Card cartaSuperiorEsperada, String jugadorActualEsperado) {
        assertEquals(cartaSuperiorEsperada, juego.peekTopCard());
        assertEquals(jugadorActualEsperado, juego.getCurrentPlayer().getName());
    }

    /**
     * @brief Verifica que, si una carta robada es jugable, se juegue automáticamente.
     */
    @Test
    void CuandoCartaRobadaEsJugable_DeberiaJugarse() {
        // Preparación
        var cartaParaRobar = CardTestFactory.createWildColorCard();
        var juego = crearJuego(
            CardTestFactory.createSkipCard(CardColor.GREEN),
            cartaParaRobar,
            CardTestFactory.createNumberCard(2, CardColor.RED));

        // Acción
        juego.drawCard(juego.getCurrentPlayer().getId());

        // Verificación
        verificarEstadoDelJuego(juego, CardTestFactory.createWildColorCard(CardColor.RED), "2");
        assertEquals(1, jugadores[0].getHandCards().count());
    }

    /**
     * @brief Verifica que, si una carta robada no es jugable, no se juegue automáticamente.
     */
    @Test
    void CuandoCartaRobadaNoEsJugable_NoDeberiaJugarse() {
        // Preparación
        var cartaParaRobar = CardTestFactory.createNumberCard(3, CardColor.GREEN);
        var cartaSuperior = CardTestFactory.createNumberCard(2, CardColor.RED);

        var juego = crearJuego(
            CardTestFactory.createSkipCard(CardColor.GREEN),
            cartaParaRobar,
            cartaSuperior);

        // Acción
        juego.drawCard(juego.getCurrentPlayer().getId());

        // Verificación
        verificarEstadoDelJuego(juego, cartaSuperior, "2");
        assertEquals(2, jugadores[0].getHandCards().count());
        assertTrue(jugadores[0].hasHandCard(cartaParaRobar));
    }

    /**
     * @brief Verifica que un jugador reciba una penalización si no dice "UNO" al jugar su penúltima carta.
     */
    @Test
    void DadoDosCartas_CuandoJuegaSinDecirUno_DeberiaRecibirPenalizacion() {
        // Preparación
        var jugadorActual = jugadores[0];
        var cartaPenalizacion1 = CardTestFactory.createNumberCard(1, CardColor.BLUE);
        var cartaPenalizacion2 = CardTestFactory.createNumberCard(2, CardColor.BLUE);
        var cartaParaJugar = CardTestFactory.createNumberCard(3, CardColor.GREEN);
        var cartaSuperior = CardTestFactory.createNumberCard(3, CardColor.RED);

        jugadorActual.addToHandCards(CardTestFactory.createSkipCard());

        var juego = crearJuego(cartaParaJugar, cartaPenalizacion1, cartaPenalizacion2, cartaSuperior);

        // Acción
        juego.playCard(jugadorActual.getId(), cartaParaJugar, false);

        // Verificación
        verificarEstadoDelJuego(juego, cartaParaJugar, "2");
        assertEquals(3, jugadores[0].getHandCards().count());
        assertTrue(jugadorActual.hasHandCard(cartaPenalizacion1));
        assertTrue(jugadorActual.hasHandCard(cartaPenalizacion2));
    }

    /**
     * @brief Verifica que un jugador no reciba penalización si dice "UNO" al jugar su penúltima carta.
     */
    @Test
    void DadoDosCartas_CuandoJuegaDiciendoUno_NoDeberiaRecibirPenalizacion() {
        // Preparación
        var jugadorActual = jugadores[0];
        var cartaPenalizacion1 = CardTestFactory.createNumberCard(1, CardColor.BLUE);
        var cartaPenalizacion2 = CardTestFactory.createNumberCard(2, CardColor.BLUE);
        var cartaParaJugar = CardTestFactory.createNumberCard(3, CardColor.GREEN);
        var cartaSuperior = CardTestFactory.createNumberCard(3, CardColor.RED);

        jugadorActual.addToHandCards(CardTestFactory.createSkipCard());

        var juego = crearJuego(cartaParaJugar, cartaPenalizacion1, cartaPenalizacion2, cartaSuperior);

        // Acción
        juego.playCard(jugadorActual.getId(), cartaParaJugar, true);

        // Verificación
        verificarEstadoDelJuego(juego, cartaParaJugar, "2");
        assertEquals(1, jugadorActual.getHandCards().count());
        assertFalse(juego.isOver());
    }

    /**
     * @brief Verifica que, cuando un jugador juega su última carta, el juego finaliza.
     */
    @Test
    void CuandoJuegaUltimaCarta_JuegoDeberiaFinalizar() {
        // Preparación
        var jugadorActual = jugadores[0];
        var cartaParaJugar = CardTestFactory.createNumberCard(3, CardColor.GREEN);
        var cartaSuperior = CardTestFactory.createNumberCard(3, CardColor.RED);

        var juego = crearJuego(cartaParaJugar, cartaSuperior);

        // Acción
        juego.playCard(jugadorActual.getId(), cartaParaJugar);

        // Verificación
        assertEquals(0, jugadorActual.getHandCards().count());
        assertTrue(juego.isOver());
        assertEquals(jugadorActual.getId(), juego.getWinner().getId());
    }

}

