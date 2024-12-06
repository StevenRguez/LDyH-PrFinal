package domain.game;

import domain.testhelper.CardTestFactory;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Clase que prueba las funcionalidades de la pila de robo (DrawPile).
 */
class TestDrawPile {

    /**
     * Prueba que al robar una carta, se retorne la última carta agregada a la pila.
     */
    @Test
    void cuandoEsRobado_DebeDevolverUltimaCarta() {
        // Configuración
        var cartaNumerica = CardTestFactory.createNumberCard();
        var cartaDeSaltar = CardTestFactory.createSkipCard();

        var pilaDeRobo = new DrawPile(Arrays.asList(cartaNumerica, cartaDeSaltar));

        // Acción
        var cartaRobada = pilaDeRobo.drawCard();

        // Verificación
        assertEquals(cartaRobada, cartaDeSaltar);
    }
}

