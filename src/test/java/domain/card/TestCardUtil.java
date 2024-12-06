package domain.card;

import domain.testhelper.CardTestFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Clase de pruebas unitarias para los métodos utilitarios de la clase CardUtil.
 */
public class TestCardUtil {

    /**
     * Verifica que una carta de color comodín sea reconocida como carta comodín.
     */
    @Test
    void dadoUnaCartaColorComodin_DebeSerCartaComodin() {
        var resultado = CardUtil.isWildCard(CardTestFactory.createWildColorCard());

        assertTrue(resultado);
    }

    /**
     * Verifica que una carta de robar cuatro sea reconocida como carta comodín.
     */
    @Test
    void dadoUnaCartaRobarCuatro_DebeSerCartaComodin() {
        var resultado = CardUtil.isWildCard(CardTestFactory.createWildDrawFourCard());

        assertTrue(resultado);
    }

    /**
     * Verifica que una carta numérica no sea reconocida como carta comodín.
     */
    @Test
    void dadoUnaCartaNumerica_NoDebeSerCartaComodin() {
        var resultado = CardUtil.isWildCard(CardTestFactory.createNumberCard());

        assertFalse(resultado);
    }

    /**
     * Verifica que una carta de acción "Saltar turno" no sea reconocida como carta comodín.
     */
    @Test
    void dadoUnaCartaSaltarTurno_NoDebeSerCartaComodin() {
        var resultado = CardUtil.isWildCard(CardTestFactory.createSkipCard(CardColor.RED));

        assertFalse(resultado);
    }

    /**
     * Verifica que una carta de acción "Reversa" no sea reconocida como carta comodín.
     */
    @Test
    void dadoUnaCartaReversa_NoDebeSerCartaComodin() {
        var resultado = CardUtil.isWildCard(CardTestFactory.createReverseCard(CardColor.BLUE));

        assertFalse(resultado);
    }

    /**
     * Verifica que una carta de acción "Toma dos" no sea reconocida como carta comodín.
     */
    @Test
    void dadoUnaCartaTomaDos_NoDebeSerCartaComodin() {
        var resultado = CardUtil.isWildCard(CardTestFactory.createDrawTwoCard(CardColor.GREEN));

        assertFalse(resultado);
    }
}
