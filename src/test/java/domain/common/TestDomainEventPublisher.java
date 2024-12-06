package domain.common;

import domain.card.Card;
import domain.card.CardColor;
import domain.game.events.CardPlayed;
import domain.testhelper.CardTestFactory;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
}
