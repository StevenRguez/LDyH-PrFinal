package domain.game;

import domain.card.Card;
import domain.player.HandCardList;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * @class DealerService
 * @brief Clase de utilidad que proporciona servicios relacionados con la baraja y distribución de cartas.
 */
public class DealerService {

    /** 
     * @brief Número total de cartas iniciales que cada jugador recibe al inicio del juego.
     */
    public static final int TOTAL_INITIAL_HAND_CARDS = 7;

    /**
     * @brief Constructor privado para evitar la instanciación de esta clase de utilidad.
     */
    private DealerService() {
    }

    /**
     * @brief Baraja una lista de cartas utilizando el algoritmo moderno de Fisher-Yates.
     * 
     * @details El método crea una nueva lista de cartas barajadas sin modificar la lista original.
     *          Se utiliza `SecureRandom` para generar índices aleatorios de forma segura.
     * 
     * @param cards Lista de cartas a barajar. No se modifica.
     * @return Una nueva lista de cartas barajadas.
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#The_modern_algorithm">Fisher-Yates Shuffle</a>
     */
    public static List<Card> shuffle(List<Card> cards) {
        var shuffledCards = new ArrayList<>(cards);
        var rand = new SecureRandom(); // Cambiado a SecureRandom para mayor seguridad

        for (int current = 0; current < shuffledCards.size() - 1; current++) {
            // Obtener un índice aleatorio dentro de los índices restantes
            var randomIndex = current + rand.nextInt(shuffledCards.size() - current);

            swapCard(shuffledCards, current, randomIndex);
        }

        return shuffledCards;
    }

    /**
     * @brief Baraja el mazo de robar y coloca una carta previamente jugada como primera.
     * 
     * @param drawPile Mazo de robar que será barajado.
     * @param lastPlayedCard Carta previamente jugada que se usará como referencia inicial.
     * @return Un nuevo `DrawPile` con las cartas barajadas.
     */
    public static DrawPile shuffle(DrawPile drawPile, Card lastPlayedCard) {
        var oldCards = new ArrayList<Card>();
        oldCards.add(lastPlayedCard);

        // Extraer todas las cartas del mazo
        for (int i = 0; i < drawPile.getSize(); i++) {
            oldCards.add(drawPile.drawCard());
        }

        // Barajar las cartas extraídas
        var shuffledCards = shuffle(oldCards);

        return new DrawPile(shuffledCards);
    }

    /**
     * @brief Intercambia dos cartas en una lista.
     * 
     * @param shuffledList Lista de cartas en la que se realizará el intercambio.
     * @param currentIndex Índice de la carta actual.
     * @param randomIndex Índice aleatorio para intercambiar.
     */
    private static void swapCard(List<Card> shuffledList, int currentIndex, int randomIndex) {
        var randomCard = shuffledList.get(randomIndex);

        shuffledList.set(randomIndex, shuffledList.get(currentIndex));
        shuffledList.set(currentIndex, randomCard);
    }

    /**
     * @brief Reparte cartas iniciales a los jugadores desde el mazo.
     * 
     * @details Cada jugador recibe una mano inicial de 7 cartas (definido por `TOTAL_INITIAL_HAND_CARDS`).
     * 
     * @param drawPile Mazo de robar del cual se extraen las cartas.
     * @param totalPlayers Número total de jugadores en la partida.
     * @return Un arreglo de `HandCardList` que representa las manos iniciales de cada jugador.
     */
    public static HandCardList[] dealInitialHandCards(DrawPile drawPile, int totalPlayers) {
        var handCardLists = new HandCardList[totalPlayers];

        for (int i = 0; i < TOTAL_INITIAL_HAND_CARDS; i++) {
            for (int p = 0; p < totalPlayers; p++) {
                if (i == 0) {
                    handCardLists[p] = new HandCardList();
                }

                handCardLists[p].addCard(drawPile.drawCard());
            }
        }

        return handCardLists;
    }
}
