package application.dto;

import java.io.Serializable;
import java.util.UUID;

/**
 * @class PlayerInfoDTO
 * @brief Data Transfer Object (DTO) that holds player information for data transfer between layers.
 *
 * This class represents the essential information of a player, encapsulating
 * their unique identifier (UUID) and name.
 * Implements Serializable to allow object serialization for data transfer.
 */
public class PlayerInfoDTO implements Serializable {

    /**
     * @brief Unique identifier for the player.
     */
    private final UUID id;

    /**
     * @brief Name of the player.
     */
    private final String name;

    /**
     * @brief Constructor that initializes the PlayerInfoDTO with a specified id and name.
     *
     * @param id Unique identifier (UUID) for the player.
     * @param name Name of the player.
     */
    public PlayerInfoDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @brief Retrieves the unique identifier (UUID) of the player.
     *
     * @return UUID of the player.
     */
    public UUID getId() {
        return id;
    }

    /**
     * @brief Retrieves the name of the player.
     *
     * @return Name of the player.
     */
    public String getName() {
        return name;
    }
}