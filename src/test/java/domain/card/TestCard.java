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
}
