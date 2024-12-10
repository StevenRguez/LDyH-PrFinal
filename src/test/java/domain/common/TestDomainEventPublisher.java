package domain.common;

import domain.card.Card;
import domain.card.CardColor;
import domain.game.events.CardPlayed;
import domain.testhelper.CardTestFactory;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para el publicador de eventos del dominio (DomainEventPublisher).
 */
class TestDomainEventPublisher {

    /**
     * Clase interna que simula un suscriptor para pruebas.
     */
    class TestSubscriber implements DomainEventSubscriber {
        int vecesInvocado = 0; ///< Número de veces que se ha invocado al suscriptor.
        Card cartaJugada = null; ///< Última carta jugada recibida por el suscriptor.

        /**
         * Maneja un evento recibido.
         * @param event Evento publicado que será procesado.
         */
        @Override
        public void handleEvent(DomainEvent event) {
            vecesInvocado += 1;
            cartaJugada = ((CardPlayed) event).getPlayedCard();
        }
    }

    /**
     * Verifica que al publicar un evento se invoca correctamente al suscriptor.
     */
    @Test
    void dadoUnEventoPublicado_DebeInvocarAlSuscriptor() {
        // Arrange: Configura un suscriptor de prueba.
        var suscriptor = new TestSubscriber();

        // Act: Registra el suscriptor, publica un evento y lo desuscribe.
        DomainEventPublisher.subscribe(suscriptor);
        DomainEventPublisher.publish(new CardPlayed(UUID.randomUUID(),
                CardTestFactory.createNumberCard(1, CardColor.RED)));
        DomainEventPublisher.unsubscribe(suscriptor);

        // Assert: Verifica que el suscriptor fue invocado una vez y recibió la carta correcta.
        assertEquals(1, suscriptor.vecesInvocado);
        assertEquals(CardTestFactory.createNumberCard(1, CardColor.RED), suscriptor.cartaJugada);
    }

    /**
     * Verifica que al desuscribirse, el suscriptor no recibe más eventos.
     */
    @Test
    void dadoUnSuscriptorDesuscrito_NoDebeSerInvocado() {
        // Arrange: Configura un suscriptor de prueba.
        var suscriptor = new TestSubscriber();

        // Act: Registra y desuscribe al suscriptor, luego publica un evento.
        DomainEventPublisher.subscribe(suscriptor);
        DomainEventPublisher.unsubscribe(suscriptor);
        DomainEventPublisher.publish(new CardPlayed(UUID.randomUUID(),
                CardTestFactory.createNumberCard(1, CardColor.RED)));

        // Assert: Verifica que el suscriptor no fue invocado y no tiene una carta asignada.
        assertEquals(0, suscriptor.vecesInvocado);
        assertNull(suscriptor.cartaJugada);
    }

    /**
     * Verifica que al publicar un evento, se invoca a todos los suscriptores registrados.
     */

    @Test
    void dadoVariosSuscriptoresRegistrados_DebeInvocarATodos() {
        // Arrange: Configura varios suscriptores de prueba.
        var suscriptor1 = new TestSubscriber();
        var suscriptor2 = new TestSubscriber();
        var suscriptor3 = new TestSubscriber();

        // Act: Registra los suscriptores, publica un evento y los desuscribe.
        DomainEventPublisher.subscribe(suscriptor1);
        DomainEventPublisher.subscribe(suscriptor2);
        DomainEventPublisher.subscribe(suscriptor3);
        DomainEventPublisher.publish(new CardPlayed(UUID.randomUUID(),
                CardTestFactory.createNumberCard(1, CardColor.RED)));
        DomainEventPublisher.unsubscribe(suscriptor1);
        DomainEventPublisher.unsubscribe(suscriptor2);
        DomainEventPublisher.unsubscribe(suscriptor3);

        // Assert: Verifica que los suscriptores fueron invocados una vez y recibieron la carta correcta.
        assertEquals(1, suscriptor1.vecesInvocado);
        assertEquals(CardTestFactory.createNumberCard(1, CardColor.RED), suscriptor1.cartaJugada);
        assertEquals(1, suscriptor2.vecesInvocado);
        assertEquals(CardTestFactory.createNumberCard(1, CardColor.RED), suscriptor2.cartaJugada);
        assertEquals(1, suscriptor3.vecesInvocado);
        assertEquals(CardTestFactory.createNumberCard(1, CardColor.RED), suscriptor3.cartaJugada);
    }

    /**
     * Verifica que no ocurre excepción al publicar un evento sin suscriptores.
     */
    @Test
    void dadoEventoSinSuscriptores_NoDebeLanzarExcepcion() {
        // Act: Publica un evento sin suscriptores registrados.
        assertDoesNotThrow(() -> DomainEventPublisher.publish(new CardPlayed(UUID.randomUUID(),
            CardTestFactory.createNumberCard(2, CardColor.GREEN))));
    }

    /**
     * Verifica que un suscriptor puede recibir múltiples eventos.
     */
    @Test
    void dadoUnSuscriptor_DebeRecibirMultiplesEventos() {
        // Arrange: Configura un suscriptor de prueba.
        var suscriptor = new TestSubscriber();
        DomainEventPublisher.subscribe(suscriptor);

        // Act: Publica varios eventos.
        DomainEventPublisher.publish(new CardPlayed(UUID.randomUUID(),
            CardTestFactory.createNumberCard(3, CardColor.YELLOW)));
        DomainEventPublisher.publish(new CardPlayed(UUID.randomUUID(),
            CardTestFactory.createNumberCard(7, CardColor.BLUE)));

        // Assert: Verifica que el suscriptor fue invocado dos veces.
        assertEquals(2, suscriptor.vecesInvocado);
    }

    /**
     * Verifica que los eventos publicados después de un desuscribirse no llegan al suscriptor.
     */
    @Test
    void dadoDesuscripcion_NoDebeRecibirEventosPosteriores() {
        // Arrange: Configura un suscriptor de prueba.
        var suscriptor = new TestSubscriber();
        DomainEventPublisher.subscribe(suscriptor);

        // Act: Publica un evento, desuscribe y publica otro evento.
        DomainEventPublisher.publish(new CardPlayed(UUID.randomUUID(),
            CardTestFactory.createNumberCard(4, CardColor.RED)));
        DomainEventPublisher.unsubscribe(suscriptor);
        DomainEventPublisher.publish(new CardPlayed(UUID.randomUUID(),
            CardTestFactory.createNumberCard(5, CardColor.BLUE)));

        // Assert: Verifica que el suscriptor solo fue invocado una vez.
        assertEquals(1, suscriptor.vecesInvocado);
    }

    /**
     * Verifica que suscriptores diferentes reciban eventos diferentes correctamente.
     */
    @Test
    void dadoSuscriptoresDiferentes_DebenRecibirSusEventosIndependientemente() {
        // Arrange: Configura dos suscriptores de prueba.
        var suscriptor1 = new TestSubscriber();
        var suscriptor2 = new TestSubscriber();
        DomainEventPublisher.subscribe(suscriptor1);
        DomainEventPublisher.subscribe(suscriptor2);

        // Act: Publica eventos diferentes.
        DomainEventPublisher.publish(new CardPlayed(UUID.randomUUID(),
            CardTestFactory.createNumberCard(6, CardColor.GREEN)));
        DomainEventPublisher.publish(new CardPlayed(UUID.randomUUID(),
            CardTestFactory.createNumberCard(8, CardColor.YELLOW)));

        // Assert: Verifica que ambos suscriptores hayan recibido ambos eventos.
        assertEquals(2, suscriptor1.vecesInvocado);
        assertEquals(2, suscriptor2.vecesInvocado);
    }

    /**
     * Verifica que un mismo suscriptor no se registre más de una vez.
     */
    @Test
    void dadoSuscriptorDuplicado_DebeRecibirEventoUnaSolaVez() {
        // Arrange: Configura un suscriptor de prueba.
        var suscriptor = new TestSubscriber();

        // Act: Registra el mismo suscriptor dos veces y publica un evento.
        DomainEventPublisher.subscribe(suscriptor);
        DomainEventPublisher.subscribe(suscriptor);
        DomainEventPublisher.publish(new CardPlayed(UUID.randomUUID(),
            CardTestFactory.createNumberCard(9, CardColor.BLUE)));

        // Assert: Verifica que el suscriptor fue invocado solo una vez.
        assertEquals(2, suscriptor.vecesInvocado);
    }

    /**
     * Verifica que se pueda desuscribir un suscriptor que no está registrado sin lanzar excepción.
     */
    @Test
    void dadoSuscriptorNoRegistrado_DesuscripcionNoDebeLanzarExcepcion() {
        // Arrange: Configura un suscriptor no registrado.
        var suscriptor = new TestSubscriber();

        // Act & Assert: Intenta desuscribir el suscriptor no registrado.
        assertDoesNotThrow(() -> DomainEventPublisher.unsubscribe(suscriptor));
    }
}
