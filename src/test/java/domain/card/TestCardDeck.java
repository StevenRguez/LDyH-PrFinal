package domain.card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Clase de pruebas unitarias para la clase CardDeck.
 */
class TestCardDeck {

    /**
     * Atributo que representa un conjunto de cartas
     */
    private CardDeck mazo;

    /**
     * Configuración inicial antes de cada prueba.
     * Se inicializa un nuevo mazo de cartas.
     */
    @BeforeEach
    void setUp() {
        mazo = new CardDeck();
    }

    /**
     * Verifica que las cartas obtenidas sean inmutables.
     * Intenta modificar la lista y espera una excepción UnsupportedOperationException.
     */
    @Test
    void alInicializar_DebeSerInmutable() {
        var cartas = mazo.getImmutableCards();

        Assertions.assertThrows(UnsupportedOperationException.class, () -> cartas.remove(0));
    }

    /**
     * Verifica que el mazo contenga exactamente 108 cartas al inicializarse.
     */
    @Test
    void alInicializar_DebeTener108Cartas() {
        assertEquals(108, mazo.getImmutableCards().size());
    }

    /**
     * Verifica que el mazo contenga exactamente 76 cartas numéricas al inicializarse.
     */
    @Test
    void alInicializar_DebeTenerCartasNumericas() {
        var cartas = mazo.getImmutableCards();

        CardCounterAssertionHelper.assertNumberCards(cartas);
    }

    /**
     * Verifica que el mazo contenga exactamente 8 cartas de tipo "Saltar turno" al inicializarse.
     */
    @Test
    void alInicializar_DebeTener8CartasSaltarTurno() {
        var cartas = mazo.getImmutableCards();

        CardCounterAssertionHelper.assertSkipCards(cartas);
    }

    /**
     * Verifica que el mazo contenga exactamente 8 cartas de tipo "Reversa" al inicializarse.
     */
    @Test
    void alInicializar_DebeTener8CartasReversa() {
        var cartas = mazo.getImmutableCards();

        CardCounterAssertionHelper.assertReverseCards(cartas);
    }

    /**
     * Verifica que el mazo contenga exactamente 8 cartas de tipo "Robar dos" al inicializarse.
     */
    @Test
    void alInicializar_DebeTenerCartasRobarDos() {
        var cartas = mazo.getImmutableCards();

        CardCounterAssertionHelper.assertDrawTwoCards(cartas);
    }

    /**
     * Verifica que el mazo contenga exactamente 8 cartas comodín al inicializarse.
     */
    @Test
    void alInicializar_DebeTenerCartasComodin() {
        var cartas = mazo.getImmutableCards();

        CardCounterAssertionHelper.assertWildCards(cartas);
    }
}
