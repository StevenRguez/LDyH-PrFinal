package domain.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @class DomainEventPublisher
 * @brief Publicador de eventos de dominio que gestiona suscriptores y la publicación de eventos de manera segura en un contexto de subprocesos.
 *
 * Esta clase permite registrar suscriptores que escucharán eventos de dominio específicos,
 * publicar eventos a todos los suscriptores registrados, y manejar la suscripción y cancelación de suscriptores.
 */
public class DomainEventPublisher {
    /**
     * @brief Lista de suscriptores registrados en el contexto del hilo actual.
     */
    private static final ThreadLocal<List<DomainEventSubscriber>> subscribers = ThreadLocal.withInitial(ArrayList::new);

    /**
     * @brief Indicador de si se está realizando una publicación de evento actualmente en el contexto del hilo.
     */
    private static final ThreadLocal<Boolean> isPublishing = ThreadLocal.withInitial(() -> Boolean.FALSE);

    /**
     * @brief Constructor privado para evitar la instanciación de esta clase.
     */
    private DomainEventPublisher() {
    }

    /**
     * @brief Registra un suscriptor para recibir eventos de dominio.
     *
     * Si la publicación de eventos está en curso, el suscriptor no será añadido hasta que esta finalice.
     *
     * @param subscriber Objeto suscriptor que desea recibir eventos.
     */
    public static void subscribe(DomainEventSubscriber subscriber) {
        if (Boolean.TRUE.equals(isPublishing.get())) {
            return;
        }

        var registeredSubscribers = subscribers.get();
        registeredSubscribers.add(subscriber);
    }

    /**
     * @brief Cancela la suscripción de un suscriptor, evitando que reciba futuros eventos de dominio.
     *
     * Si la publicación de eventos está en curso, el suscriptor no será eliminado hasta que esta finalice.
     *
     * @param subscriber Objeto suscriptor que desea cancelar su suscripción.
     */
    public static void unsubscribe(DomainEventSubscriber subscriber) {
        if (Boolean.TRUE.equals(isPublishing.get())) {
            return;
        }

        subscribers.get().remove(subscriber);
    }

    /**
     * @brief Publica un evento de dominio, notificando a todos los suscriptores registrados.
     *
     * Este método marca el estado de publicación como activo mientras el evento se distribuye a los suscriptores,
     * y luego lo restablece. Los suscriptores reciben el evento a través de su método `handleEvent`.
     *
     * @param event Objeto DomainEvent que representa el evento a publicar.
     */
    public static void publish(final DomainEvent event) {
        if (Boolean.TRUE.equals(isPublishing.get())) {
            return;
        }

        try {
            isPublishing.set(Boolean.TRUE);

            var registeredSubscribers = subscribers.get();

            for (var subscriber : registeredSubscribers) {
                subscriber.handleEvent(event);
            }
        } finally {
            isPublishing.set(Boolean.FALSE);
        }
    }

    /**
     * @brief Restablece el estado del publicador de eventos, eliminando suscriptores y el estado de publicación del hilo actual.
     *
     * Este método es útil para limpiar el contexto de eventos en casos de pruebas o reinicios.
     */
    public static void reset() {
        subscribers.remove();
        isPublishing.remove();
    }
}