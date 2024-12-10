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

    /**
     * Verifica que el constructor funcione correctamente para tipos y colores válidos.
     */
    @Test
    void constructorConTipoYColorValidos_NoDebeLanzarExcepcion() {
        assertDoesNotThrow(() -> new ActionCard(CardType.SKIP, CardColor.GREEN));
    }

    /**
     * Verifica que dos cartas de acción con diferentes tipos y colores no sean iguales.
     */
    @Test
    void cartasConDistintoTipoYColor_DebenSerDiferentes() {
        var carta1 = new ActionCard(CardType.SKIP, CardColor.RED);
        var carta2 = new ActionCard(CardType.REVERSE, CardColor.BLUE);

        assertNotEquals(carta1, carta2);
    }

    /**
     * Verifica que dos cartas iguales tengan el mismo hash code.
     */
    @Test
    void cartasIguales_DebenTenerMismoHashCode() {
        var carta1 = new ActionCard(CardType.REVERSE, CardColor.GREEN);
        var carta2 = new ActionCard(CardType.REVERSE, CardColor.GREEN);

        assertEquals(carta1.hashCode(), carta2.hashCode());
    }

    /**
     * Verifica que el método equals cumpla la propiedad reflexiva.
     */
    @Test
    void equals_DebeSerReflexivo() {
        var carta = new ActionCard(CardType.SKIP, CardColor.YELLOW);

        assertEquals(carta, carta);
    }

    /**
     * Verifica que el método equals cumpla la propiedad simétrica.
     */
    @Test
    void equals_DebeSerSimetrico() {
        var carta1 = new ActionCard(CardType.DRAW_TWO, CardColor.BLUE);
        var carta2 = new ActionCard(CardType.DRAW_TWO, CardColor.BLUE);

        assertTrue(carta1.equals(carta2) && carta2.equals(carta1));
    }

    /**
     * Verifica que el método equals cumpla la propiedad transitiva.
     */
    @Test
    void equals_DebeSerTransitivo() {
        var carta1 = new ActionCard(CardType.REVERSE, CardColor.RED);
        var carta2 = new ActionCard(CardType.REVERSE, CardColor.RED);
        var carta3 = new ActionCard(CardType.REVERSE, CardColor.RED);

        assertTrue(carta1.equals(carta2) && carta2.equals(carta3) && carta1.equals(carta3));
    }

    /**
     * Verifica que el método equals devuelva false si se compara con null.
     */
    @Test
    void equals_NoDebeSerIgualANull() {
        var carta = new ActionCard(CardType.SKIP, CardColor.YELLOW);

        assertNotEquals(carta, null);
    }

    /**
     * Verifica que el método equals devuelva false si se compara con un objeto de otra clase.
     */
    @Test
    void equals_NoDebeSerIgualAObjetoDeOtraClase() {
        var carta = new ActionCard(CardType.DRAW_TWO, CardColor.BLUE);

        assertNotEquals(carta, "OtraClase");
    }

    /**
     * Verifica que la carta devuelva correctamente su tipo.
     */
    @Test
    void getType_DebeDevolverTipoCorrecto() {
        var carta = new ActionCard(CardType.SKIP, CardColor.GREEN);

        assertEquals(CardType.SKIP, carta.getType());
    }

    /**
     * Verifica que la carta devuelva correctamente su color.
     */
    @Test
    void getColor_DebeDevolverColorCorrecto() {
        var carta = new ActionCard(CardType.DRAW_TWO, CardColor.YELLOW);

        assertEquals(CardColor.YELLOW, carta.getColor());
    }
}
