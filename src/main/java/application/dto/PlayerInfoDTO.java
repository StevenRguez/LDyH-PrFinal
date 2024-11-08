package application.dto;

import java.io.Serializable;
import java.util.UUID;

/**
 * @class PlayerInfoDTO
 * @brief Objeto de Transferencia de Datos (DTO) que contiene la información del jugador para la transferencia de datos entre capas.
 *
 * Esta clase representa la información esencial de un jugador, encapsulando
 * su identificador único (UUID) y su nombre.
 * Implementa Serializable para permitir la serialización del objeto para la transferencia de datos.
 */
public class PlayerInfoDTO implements Serializable {

    /**
     * @brief Identificador único del jugador.
     */
    private final UUID id;

    /**
     * @brief Nombre del jugador.
     */
    private final String name;

    /**
     * @brief Constructor que inicializa el PlayerInfoDTO con un id y un nombre especificados.
     *
     * @param id Identificador único (UUID) del jugador.
     * @param name Nombre del jugador.
     */
    public PlayerInfoDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @brief Obtiene el identificador único (UUID) del jugador.
     *
     * @return UUID del jugador.
     */
    public UUID getId() {
        return id;
    }

    /**
     * @brief Obtiene el nombre del jugador.
     *
     * @return Nombre del jugador.
     */
    public String getName() {
        return name;
    }
}