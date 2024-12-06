package domain.card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para la clase NumberCard.
 */
public class TestNumberCard {

    /**
     * Verifica que el constructor crea correctamente una carta de número.
     */
    @Test
    void dadoUnValorYUnColor_DebeCrearUnaCartaCorrectamente() {
        var carta = new NumberCard(5, CardColor.RED);

        assertEquals(5, carta.getValue());
        assertEquals(CardColor.RED, carta.getColor());
        assertEquals(CardType.NUMBER, carta.getType());
    }

    /**
     * Verifica que dos cartas con el mismo valor y color son iguales.
     */
    @Test
    void dadoDosCartasConElMismoValorYColor_DebenSerIguales() {
        var carta1 = new NumberCard(7, CardColor.BLUE);
        var carta2 = new NumberCard(7, CardColor.BLUE);

        assertEquals(carta1, carta2);
        assertEquals(carta1.hashCode(), carta2.hashCode());
    }

    /**
     * Verifica que dos cartas con diferente valor no son iguales.
     */
    @Test
    void dadoDosCartasConDiferenteValor_NoDebenSerIguales() {
        var carta1 = new NumberCard(4, CardColor.YELLOW);
        var carta2 = new NumberCard(6, CardColor.YELLOW);

        assertNotEquals(carta1, carta2);
    }

    /**
     * Verifica que dos cartas con diferente color no son iguales.
     */
    @Test
    void dadoDosCartasConDiferenteColor_NoDebenSerIguales() {
        var carta1 = new NumberCard(8, CardColor.RED);
        var carta2 = new NumberCard(8, CardColor.GREEN);

        assertNotEquals(carta1, carta2);
    }

    /**
     * Verifica que la representación en cadena de la carta es la esperada.
     */
    @Test
    void dadoUnaCarta_DebeDevolverLaRepresentacionCorrecta() {
        var carta = new NumberCard(3, CardColor.RED);

        assertEquals("NumberCard{3, RED}", carta.toString());
    }

    /**
     * Verifica que el método equals retorna false al comparar con un objeto de otra clase.
     */
    @Test
    void dadoUnaCartaComparadaConOtroObjeto_NoDebenSerIguales() {
        var carta = new NumberCard(2, CardColor.BLUE);
        assertFalse(carta.getClass().equals(String.class), "Las cartas no deberían ser iguales a un String.");
    }

    /**
     * Verifica que no se permite crear una carta con un valor negativo.
     */
    @Test
    void dadoUnValorNegativo_DebeLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new NumberCard(-1, CardColor.RED));
    }

    /**
     * Verifica que no se permite crear una carta con un valor mayor al permitido.
     */
    @Test
    void dadoUnValorMayorQueElMaximo_DebeLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> new NumberCard(20, CardColor.YELLOW));
    }

    /**
     * Verifica que una carta comparada consigo misma es igual.
     */
    @Test
    void dadoUnaCartaComparadaConSiguiente_DebenSerIguales() {
        var carta = new NumberCard(9, CardColor.GREEN);

        assertEquals(carta, carta);
    }
}
