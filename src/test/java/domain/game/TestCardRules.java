package domain.game;

import domain.card.ActionCard;
import domain.card.Card;
import domain.card.CardColor;
import domain.card.CardType;
import domain.testhelper.CardTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Clase que prueba las reglas de validación de las cartas en el juego.
 */
class TestCardRules {

    /**
     * Prueba si jugar una carta numérica es válido según las reglas del juego.
     *
     * @param topCard La carta en la parte superior del mazo.
     */
    @ParameterizedTest
    @MethodSource("proveerCartasSuperioresValidasParaCartaNumerica")
    void cuandoCartaNumericaEsJugada_DeberiaSerValida(Card topCard) {
        var cartaAJugar = CardTestFactory.createNumberCard(5, CardColor.RED);

        var resultado = CardRules.isValidNumberCard(topCard, cartaAJugar);

        assertTrue(resultado, crearMensajePrueba(topCard, cartaAJugar));
    }

    /**
     * Proporciona una lista de cartas superiores válidas para probar cartas numéricas.
     *
     * @return Un Stream de argumentos con cartas superiores válidas.
     */
    private static Stream<Arguments> proveerCartasSuperioresValidasParaCartaNumerica() {
        return Stream.of(
            Arguments.of(CardTestFactory.createNumberCard(5, CardColor.RED)),
            Arguments.of(CardTestFactory.createNumberCard(4, CardColor.RED)),
            Arguments.of(CardTestFactory.createNumberCard(5, CardColor.BLUE)),
            Arguments.of(CardTestFactory.createSkipCard(CardColor.RED)),
            Arguments.of(CardTestFactory.createReverseCard(CardColor.RED)),
            Arguments.of(CardTestFactory.createDrawTwoCard(CardColor.RED)),
            Arguments.of(CardTestFactory.createWildColorCard(CardColor.RED)),
            Arguments.of(CardTestFactory.createWildDrawFourCard(CardColor.RED))
        );
    }

    /**
     * Prueba si jugar una carta numérica no coincidente es inválido según las reglas del juego.
     *
     * @param topCard La carta en la parte superior del mazo.
     */
    @ParameterizedTest
    @MethodSource("proveerCartasSuperioresInvalidasParaCartaNumerica")
    void cuandoCartaNumericaNoCoincidenteEsJugada_DeberiaSerInvalida(Card topCard) {
        var cartaAJugar = CardTestFactory.createNumberCard(5, CardColor.RED);

        var resultado = CardRules.isValidNumberCard(topCard, cartaAJugar);

        assertFalse(resultado, crearMensajePrueba(topCard, cartaAJugar));
    }

    /**
     * Proporciona una lista de cartas superiores inválidas para probar cartas numéricas.
     *
     * @return Un Stream de argumentos con cartas superiores inválidas.
     */
    private static Stream<Arguments> proveerCartasSuperioresInvalidasParaCartaNumerica() {
        return Stream.of(
            Arguments.of(CardTestFactory.createNumberCard(4, CardColor.BLUE)),
            Arguments.of(CardTestFactory.createSkipCard(CardColor.BLUE)),
            Arguments.of(CardTestFactory.createReverseCard(CardColor.BLUE)),
            Arguments.of(CardTestFactory.createDrawTwoCard(CardColor.BLUE)),
            Arguments.of(CardTestFactory.createWildColorCard(CardColor.BLUE)),
            Arguments.of(CardTestFactory.createWildDrawFourCard(CardColor.BLUE)),
            Arguments.of(CardTestFactory.createWildColorCard()),
            Arguments.of(CardTestFactory.createWildColorCard(CardColor.BLUE)),
            Arguments.of(CardTestFactory.createWildDrawFourCard()),
            Arguments.of(CardTestFactory.createWildDrawFourCard(CardColor.BLUE))
        );
    }

    /**
     * Prueba si jugar una carta de acción es válido según las reglas del juego.
     *
     * @param cardToPlay La carta que se intenta jugar.
     * @param topCard La carta en la parte superior del mazo.
     */
    @ParameterizedTest
    @MethodSource("proveerCartasSuperioresValidasParaCartaAccion")
    void cuandoCartaAccionEsJugada_DeberiaSerValida(Card cardToPlay, Card topCard) {
        var resultado = CardRules.isValidActionCard(topCard, (ActionCard) cardToPlay);

        assertTrue(resultado, crearMensajePrueba(topCard, cardToPlay));
    }

    /**
     * Proporciona una lista de cartas superiores válidas para probar cartas de acción.
     *
     * @return Un Stream de argumentos con cartas superiores válidas.
     */
    private static Stream<Arguments> proveerCartasSuperioresValidasParaCartaAccion() {
        var argumentos = new ArrayList<Arguments>();
        var tiposAccion = new CardType[]{CardType.SKIP, CardType.REVERSE, CardType.DRAW_TWO};

        for (var accion : tiposAccion) {
            var cartaAJugar = CardTestFactory.createActionCard(accion, CardColor.YELLOW);

            argumentos.add(Arguments.of(cartaAJugar, CardTestFactory.createActionCard(accion, CardColor.RED)));
            argumentos.add(Arguments.of(cartaAJugar, CardTestFactory.createNumberCard(5, CardColor.YELLOW)));
            argumentos.add(Arguments.of(cartaAJugar, CardTestFactory.createSkipCard(CardColor.YELLOW)));
            argumentos.add(Arguments.of(cartaAJugar, CardTestFactory.createReverseCard(CardColor.YELLOW)));
            argumentos.add(Arguments.of(cartaAJugar, CardTestFactory.createDrawTwoCard(CardColor.YELLOW)));
            argumentos.add(Arguments.of(cartaAJugar, CardTestFactory.createWildColorCard(CardColor.YELLOW)));
            argumentos.add(Arguments.of(cartaAJugar, CardTestFactory.createWildDrawFourCard(CardColor.YELLOW)));
        }

        return argumentos.stream();
    }

    /**
     * Prueba si jugar una carta de acción no coincidente es inválido según las reglas del juego.
     *
     * @param cardToPlay La carta que se intenta jugar.
     * @param topCard La carta en la parte superior del mazo.
     */
    @ParameterizedTest
    @MethodSource("proveerCartasSuperioresInvalidasParaCartaAccion")
    void cuandoCartaAccionNoCoincidenteEsJugada_DeberiaSerInvalida(Card cardToPlay, Card topCard) {
        var resultado = CardRules.isValidActionCard(topCard, (ActionCard) cardToPlay);

        assertFalse(resultado, crearMensajePrueba(topCard, cardToPlay));
    }

    /**
     * Proporciona una lista de cartas superiores inválidas para probar cartas de acción.
     *
     * @return Un Stream de argumentos con cartas superiores inválidas.
     */
    private static Stream<Arguments> proveerCartasSuperioresInvalidasParaCartaAccion() {
        var argumentos = new ArrayList<Arguments>();
        var tiposAccion = new CardType[]{CardType.SKIP, CardType.REVERSE, CardType.DRAW_TWO};

        for (var accion : tiposAccion) {
            var cartaAJugar = CardTestFactory.createActionCard(accion, CardColor.YELLOW);

            argumentos.add(Arguments.of(cartaAJugar, CardTestFactory.createNumberCard(5, CardColor.BLUE)));

            for (var otraAccion : tiposAccion) {
                if (otraAccion != accion) {
                    argumentos.add(Arguments.of(cartaAJugar, CardTestFactory.createActionCard(otraAccion, CardColor.BLUE)));
                }
            }

            argumentos.add(Arguments.of(cartaAJugar, CardTestFactory.createWildColorCard()));
            argumentos.add(Arguments.of(cartaAJugar, CardTestFactory.createWildColorCard(CardColor.BLUE)));
            argumentos.add(Arguments.of(cartaAJugar, CardTestFactory.createWildDrawFourCard()));
            argumentos.add(Arguments.of(cartaAJugar, CardTestFactory.createWildDrawFourCard(CardColor.BLUE)));
        }

        return argumentos.stream();
    }

    /**
     * Prueba si una carta comodín con un color elegido es válida.
     */
    @Test
    void cuandoCartaComodinConColorElegido_DeberiaSerValida() {
        var cartaComodin = CardTestFactory.createWildColorCard(CardColor.RED);

        var resultado = CardRules.isValidWildCard(cartaComodin);

        assertTrue(resultado, cartaComodin.toString());
    }

    /**
     * Prueba si una carta comodín sin un color elegido es inválida.
     */
    @Test
    void cuandoCartaComodinSinColor_DeberiaSerInvalida() {
        var cartaComodin = CardTestFactory.createWildColorCard();

        var resultado = CardRules.isValidWildCard(cartaComodin);

        assertFalse(resultado, cartaComodin.toString());
    }

    /**
     * Crea un mensaje de prueba detallado para depuración.
     *
     * @param topCard La carta en la parte superior del mazo.
     * @param playedCard La carta que se intenta jugar.
     * @return Mensaje con detalles de las cartas involucradas.
     */
    private String crearMensajePrueba(Card topCard, Card playedCard) {
        return String.format("Carta Superior: %s, Carta Jugada: %s", topCard, playedCard);
    }
}