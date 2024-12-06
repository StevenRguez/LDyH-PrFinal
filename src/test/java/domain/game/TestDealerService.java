package domain.game;

import domain.card.*;
import domain.player.HandCardList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase que prueba los servicios de barajado y distribución de cartas.
 */
class TestDealerService {

    /**
     * Prueba que las cartas barajadas tengan un orden diferente al original.
     */
    @Test
    void cuandoEsBarajado_DebeTenerOrdenDiferente() {
        // Configuración
        var listaOriginal = new CardDeck().getImmutableCards();

        // Acción
        var listaBarajada = DealerService.shuffle(listaOriginal);

        // Verificación
        int totalIguales = 0;
        for (int i = 0; i < listaOriginal.size(); i++) {
            if (listaOriginal.get(i).equals(listaBarajada.get(i))) {
                totalIguales++;
            }
        }
        assertNotEquals(listaOriginal.size(), totalIguales);
        assertTrue(totalIguales < (listaOriginal.size() * 0.2),
            "Las cartas barajadas deberían tener menos del 20% de orden igual (heurísticamente)");
    }

    /**
     * Prueba que las cartas barajadas incluyan todas las cartas numéricas.
     */
    @Test
    void cuandoEsBarajado_DebeIncluirTodasLasCartasNumericas() {
        var listaBarajada = obtenerCartasBarajadas();

        CardCounterAssertionHelper.assertNumberCards(listaBarajada);
    }

    /**
     * Prueba que las cartas barajadas incluyan todas las cartas de tipo "Saltar".
     */
    @Test
    void cuandoEsBarajado_DebeIncluirTodasLasCartasDeSaltar() {
        var listaBarajada = obtenerCartasBarajadas();

        CardCounterAssertionHelper.assertSkipCards(listaBarajada);
    }

    /**
     * Prueba que las cartas barajadas incluyan todas las cartas de tipo "Reversa".
     */
    @Test
    void cuandoEsBarajado_DebeIncluirTodasLasCartasDeReversa() {
        var listaBarajada = obtenerCartasBarajadas();

        CardCounterAssertionHelper.assertReverseCards(listaBarajada);
    }

    /**
     * Prueba que las cartas barajadas incluyan todas las cartas de tipo "Roba dos".
     */
    @Test
    void cuandoEsBarajado_DebeIncluirTodasLasCartasDeRobaDos() {
        var listaBarajada = obtenerCartasBarajadas();

        CardCounterAssertionHelper.assertDrawTwoCards(listaBarajada);
    }

    /**
     * Prueba que las cartas barajadas incluyan todas las cartas comodín.
     */
    @Test
    void cuandoEsBarajado_DebeIncluirTodasLasCartasComodin() {
        var listaBarajada = obtenerCartasBarajadas();

        CardCounterAssertionHelper.assertWildCards(listaBarajada);
    }

    /**
     * Obtiene una lista de cartas barajadas.
     *
     * @return Una lista de cartas después de ser barajadas.
     */
    private List<Card> obtenerCartasBarajadas() {
        var listaOriginal = new CardDeck().getImmutableCards();
        return DealerService.shuffle(listaOriginal);
    }

    /**
     * Prueba que las cartas distribuidas incluyan 7 cartas por jugador.
     */
    @Test
    void cuandoEsRepartido_DebeTener7CartasPorJugador() {
        // Configuración
        var cartasFijas = new ArrayList<Card>();
        for (int i = 0; i < 7; i++) {
            cartasFijas.add(new NumberCard(1, CardColor.RED));
            cartasFijas.add(new NumberCard(2, CardColor.GREEN));
            cartasFijas.add(new NumberCard(3, CardColor.BLUE));
        }
        var pilaDeRobo = new DrawPile(cartasFijas);

        // Acción
        var listasDeCartasMano = DealerService.dealInitialHandCards(pilaDeRobo, 3);

        // Verificación
        assertTrue(verificarMismoValor(listasDeCartasMano[0], 3, CardColor.BLUE));
        assertTrue(verificarMismoValor(listasDeCartasMano[1], 2, CardColor.GREEN));
        assertTrue(verificarMismoValor(listasDeCartasMano[2], 1, CardColor.RED));

        assertEquals(0, pilaDeRobo.getSize());
    }

    /**
     * Verifica si todas las cartas en una lista tienen el mismo valor y color.
     *
     * @param listaCartasMano Lista de cartas de la mano.
     * @param numero El número esperado en las cartas.
     * @param color El color esperado en las cartas.
     * @return Verdadero si todas las cartas tienen el mismo valor y color, falso de lo contrario.
     */
    private boolean verificarMismoValor(HandCardList listaCartasMano, int numero, CardColor color) {
        if (listaCartasMano.size() != 7) {
            return false;
        }

        return listaCartasMano
            .getCardStream()
            .map(c -> (NumberCard) c)
            .allMatch(c -> c.getValue() == numero && c.getColor() == color);
    }
}

