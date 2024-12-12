package domain.common;

/**
 DomainEventSubscriber
 * Interfaz para suscriptores de eventos de dominio.
 *
 * Define el contrato que debe implementar cualquier clase que desee suscribirse y
 * responder a eventos de dominio publicados por el `DomainEventPublisher`.
 */
public interface DomainEventSubscriber {

    /**
     * Maneja un evento de dominio específico.
     *
     * Este metodo es invocado cuando un evento de dominio es publicado y debe ser implementado
     * por la clase suscriptora para definir cómo responder al evento.
     *
     * @param event Objeto DomainEvent que representa el evento de dominio recibido.
     */
    void handleEvent(DomainEvent event);
}

