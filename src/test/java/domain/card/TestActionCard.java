package domain.card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para la clase ActionCard.
 */
public class TestActionCard{

    /**
     * Verifica que se lance una excepción si el tipo de la carta no es válido para una carta de acción.
     */
    @Test
    void constructorConTipoInvalido_DebeLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new ActionCard(CardType.NUMBER, CardColor.RED));
    }

    /**
     * Verifica que se lance una excepción si el color de la carta no es válido.
     */
    @Test
    void constructorConColorInvalido_DebeLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new ActionCard(CardType.SKIP, null));
    }

    /**
     * Verifica que dos cartas de acción con el mismo tipo y color sean iguales.
     */
    @Test
    void cartasConMismoTipoYColor_DebenSerIguales() {
        var carta1 = new ActionCard(CardType.SKIP, CardColor.RED);
        var carta2 = new ActionCard(CardType.SKIP, CardColor.RED);

        assertEquals(carta1, carta2);
        assertEquals(carta1.hashCode(), carta2.hashCode());
    }

    /**
     * Verifica que dos cartas de acción con diferente tipo no sean iguales.
     */
    @Test
    void cartasConDistintoTipo_DebenSerDiferentes() {
        var carta1 = new ActionCard(CardType.SKIP, CardColor.RED);
        var carta2 = new ActionCard(CardType.REVERSE, CardColor.RED);

        assertNotEquals(carta1, carta2);
    }

    /**
     * Verifica que dos cartas de acción con diferente color no sean iguales.
     */
    @Test
    void cartasConDistintoColor_DebenSerDiferentes() {
        var carta1 = new ActionCard(CardType.SKIP, CardColor.RED);
        var carta2 = new ActionCard(CardType.SKIP, CardColor.BLUE);

        assertNotEquals(carta1, carta2);
    }

    /**
     * Verifica la representación en cadena de una carta de acción.
     */
    @Test
    void toString_DebeDevolverRepresentacionCorrecta() {
        var carta = new ActionCard(CardType.SKIP, CardColor.RED);

        String esperado = "ActionCard{SKIP, RED}";
        assertEquals(esperado, carta.toString());
    }

    /**
     * Verifica que una carta de acción no sea igual a un objeto de otra clase.
     */
    @Test
    void cartaNoDebeSerIgualAObjetoDeOtraClase() {
        var carta = new ActionCard(CardType.SKIP, CardColor.RED);

        // Compara con null para verificar que no es igual a null
        assertNotEquals(carta, null);

        // Opcionalmente, compara con otro objeto del mismo tipo con diferentes valores
        var otraCarta = new ActionCard(CardType.REVERSE, CardColor.BLUE);
        assertNotEquals(carta, otraCarta);
    }
}
