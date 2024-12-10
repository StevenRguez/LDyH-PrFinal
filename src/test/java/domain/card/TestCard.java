package domain.card;

import domain.testhelper.CardTestFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para la clase Card y sus subclases.
 */
public class TestCard {



    /**
     * Verifica que dos cartas numéricas con el mismo valor y color sean iguales.
     */
    @Test
    void cartasNumericasConMismoValorYColor_DebenSerIguales() {
        var cartaNumerica = new NumberCard(1, CardColor.RED);
        var otraCartaNumerica = new NumberCard(1, CardColor.RED);

        assertNotSame(cartaNumerica, otraCartaNumerica);
        assertEquals(cartaNumerica, otraCartaNumerica);
    }

    /**
     * Verifica que dos cartas numéricas con valores diferentes no sean iguales.
     */
    @Test
    void cartasNumericasConDistintoValor_DebenSerDiferentes() {
        var cartaNumerica = new NumberCard(1, CardColor.RED);
        var otraCartaNumerica = new NumberCard(2, CardColor.RED);

        assertNotEquals(cartaNumerica, otraCartaNumerica);
    }

    /**
     * Verifica que dos cartas numéricas con colores diferentes no sean iguales.
     */
    @Test
    void cartasNumericasConDistintoColor_DebenSerDiferentes() {
        var cartaNumerica = new NumberCard(1, CardColor.RED);
        var otraCartaNumerica = new NumberCard(1, CardColor.BLUE);

        assertNotEquals(cartaNumerica, otraCartaNumerica);
    }

    /**
     * Verifica que dos cartas de tipo "Saltar turno" con el mismo color sean iguales.
     */
    @Test
    void cartasSaltarTurnoConMismoColor_DebenSerIguales() {
        var cartaSaltarTurno = CardTestFactory.createSkipCard(CardColor.RED);
        var otraCartaSaltarTurno = CardTestFactory.createSkipCard(CardColor.RED);

        assertEquals(cartaSaltarTurno, otraCartaSaltarTurno);
    }

    /**
     * Verifica que dos cartas de tipo "Saltar turno" con colores diferentes no sean iguales.
     */
    @Test
    void cartasSaltarTurnoConDistintoColor_DebenSerDiferentes() {
        var cartaSaltarTurno = CardTestFactory.createSkipCard(CardColor.RED);
        var otraCartaSaltarTurno = CardTestFactory.createSkipCard(CardColor.BLUE);

        assertNotEquals(cartaSaltarTurno, otraCartaSaltarTurno);
    }

    /**
     * Verifica que una carta de tipo "Reversa" y una de tipo "Saltar turno" con el mismo color no sean iguales.
     */
    @Test
    void cartaReversaYCartaSaltarTurnoConMismoColor_DebenSerDiferentes() {
        var cartaReversa = CardTestFactory.createReverseCard(CardColor.RED);
        var cartaSaltarTurno = CardTestFactory.createSkipCard(CardColor.RED);

        assertNotEquals(cartaReversa, cartaSaltarTurno);
    }

    /**
     * Verifica que dos cartas de tipo "Comodín de color" sean iguales.
     */
    @Test
    void comodinesDeColor_Iguales() {
        var comodinColor = CardTestFactory.createWildColorCard();
        var otroComodinColor = CardTestFactory.createWildColorCard();

        assertEquals(comodinColor, otroComodinColor);
    }

    /**
     * Verifica que dos cartas de tipo "Comodín robar cuatro" sean iguales.
     */
    @Test
    void comodinesRobarCuatro_Iguales() {
        var comodinRobarCuatro = CardTestFactory.createWildDrawFourCard();
        var otroComodinRobarCuatro = CardTestFactory.createWildDrawFourCard();

        assertEquals(comodinRobarCuatro, otroComodinRobarCuatro);
    }

    /**
     * Verifica que una carta de tipo "Comodín de color" y una de tipo "Comodín robar cuatro" no sean iguales.
     */
    @Test
    void comodinColorYComodinRobarCuatro_Diferentes() {
        var comodinColor = CardTestFactory.createWildColorCard();
        var comodinRobarCuatro = CardTestFactory.createWildDrawFourCard();

        assertNotEquals(comodinColor, comodinRobarCuatro);
    }

    /**
     * Verifica que una carta de tipo "Comodín de color" y una carta numérica no sean iguales.
     */

    @Test
    void comodinColorYCartaNumerica_Diferentes() {
        var comodinColor = CardTestFactory.createWildColorCard();
        var cartaNumerica = new NumberCard(1, CardColor.RED);

        assertFalse(comodinColor.getClass().equals(cartaNumerica.getClass()), "Los tipos de las cartas no deberían ser iguales.");
    }

    /**
     * Verifica que dos cartas de tipo "Reversa" con el mismo color sean iguales.
     */
    @Test
    void cartasReversaConMismoColor_DebenSerIguales() {
        var cartaReversa = CardTestFactory.createReverseCard(CardColor.YELLOW);
        var otraCartaReversa = CardTestFactory.createReverseCard(CardColor.YELLOW);

        assertEquals(cartaReversa, otraCartaReversa);
    }

    /**
     * Verifica que dos cartas de tipo "Reversa" con colores diferentes no sean iguales.
     */
    @Test
    void cartasReversaConDistintoColor_DebenSerDiferentes() {
        var cartaReversa = CardTestFactory.createReverseCard(CardColor.YELLOW);
        var otraCartaReversa = CardTestFactory.createReverseCard(CardColor.GREEN);

        assertNotEquals(cartaReversa, otraCartaReversa);
    }

    /**
     * Verifica que el hashCode sea consistente para dos cartas iguales.
     */
    @Test
    void hashCode_DebeSerConsistenteParaCartasIguales() {
        var carta1 = new NumberCard(5, CardColor.BLUE);
        var carta2 = new NumberCard(5, CardColor.BLUE);

        assertEquals(carta1.hashCode(), carta2.hashCode());
    }

    /**
     * Verifica que el hashCode sea diferente para cartas con diferentes atributos.
     */
    @Test
    void hashCode_DebeSerDiferenteParaCartasDiferentes() {
        var carta1 = new NumberCard(5, CardColor.RED);
        var carta2 = new NumberCard(7, CardColor.RED);

        assertNotEquals(carta1.hashCode(), carta2.hashCode());
    }

    /**
     * Verifica que el método equals cumpla la propiedad reflexiva.
     */
    @Test
    void equals_DebeSerReflexivo() {
        var carta = CardTestFactory.createDrawTwoCard(CardColor.RED);

        assertEquals(carta, carta);
    }

    /**
     * Verifica que el método equals cumpla la propiedad simétrica.
     */
    @Test
    void equals_DebeSerSimetrico() {
        var carta1 = CardTestFactory.createDrawTwoCard(CardColor.BLUE);
        var carta2 = CardTestFactory.createDrawTwoCard(CardColor.BLUE);

        assertTrue(carta1.equals(carta2) && carta2.equals(carta1));
    }

    /**
     * Verifica que el método equals cumpla la propiedad transitiva.
     */
    @Test
    void equals_DebeSerTransitivo() {
        var carta1 = CardTestFactory.createSkipCard(CardColor.GREEN);
        var carta2 = CardTestFactory.createSkipCard(CardColor.GREEN);
        var carta3 = CardTestFactory.createSkipCard(CardColor.GREEN);

        assertTrue(carta1.equals(carta2) && carta2.equals(carta3) && carta1.equals(carta3));
    }

    /**
     * Verifica que el método toString devuelva la representación correcta de una carta de acción.
     */
    @Test
    void toString_DebeDevolverRepresentacionCorrectaParaCartaDeAccion() {
        var cartaDeAccion = CardTestFactory.createDrawTwoCard(CardColor.YELLOW);

        String esperado = "ActionCard{DRAW_TWO, YELLOW}";
        assertEquals(esperado, cartaDeAccion.toString());
    }

    /**
     * Verifica que una carta numérica no sea igual a null.
     */
    @Test
    void cartaNumerica_NoDebeSerIgualANull() {
        var cartaNumerica = new NumberCard(2, CardColor.GREEN);

        assertNotEquals(cartaNumerica, null);
    }
}
