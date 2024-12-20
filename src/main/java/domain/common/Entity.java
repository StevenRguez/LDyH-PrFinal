package domain.common;

import java.util.UUID;

/**
 Entity
 * Clase abstracta base para todas las entidades del dominio.
 *
 * Proporciona una estructura común para las entidades, definiendo un identificador único (UUID)
 * que permite la identificación de cada instancia dentro del sistema.
 */
public abstract class Entity {
    /**
     * Identificador único de la entidad.
     */
    private final UUID id;

    /**
     * Constructor protegido que inicializa la entidad con un UUID aleatorio.
     */
    protected Entity(){
        this(UUID.randomUUID());
    }

    /**
     * Constructor protegido que permite asignar un UUID específico a la entidad.
     *
     * @param id Identificador UUID que se asignará a la entidad.
     */
    protected Entity(UUID id){
        this.id = id;
    }

    /**
     * Obtiene el identificador único de la entidad.
     *
     * @return UUID que representa el identificador único de la entidad.
     */
    public UUID getId() {
        return id;
    }
}
