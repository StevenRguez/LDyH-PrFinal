package domain.common;

import java.time.LocalDate;

/**
 * @class DomainEvent
 * @brief Clase abstracta base que representa un evento de dominio genérico en la aplicación.
 *
 * Un evento de dominio captura una ocurrencia específica dentro del dominio, incluyendo
 * una marca de tiempo de cuándo ocurrió. Las subclases deben heredar de DomainEvent
 * para representar tipos específicos de eventos.
 */
public abstract class DomainEvent {

    /**
     * @brief Fecha en la que ocurrió el evento.
     *
     * Esta fecha se establece automáticamente a la fecha actual en el momento de la creación del evento.
     */
    private final LocalDate occurredDate;

    /**
     * @brief Constructor protegido que inicializa occurredDate con la fecha actual.
     *
     * Este constructor está destinado a ser llamado por las subclases para establecer
     * automáticamente la fecha de ocurrencia del evento.
     */
    protected DomainEvent(){
        occurredDate = LocalDate.now();
    }

    /**
     * @brief Obtiene la fecha en la que ocurrió el evento.
     *
     * @return LocalDate que representa la fecha de ocurrencia del evento.
     */
    public LocalDate getOccurredDate(){
        return occurredDate;
    }
}
