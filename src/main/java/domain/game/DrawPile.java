package domain.game;

import domain.card.Card;

import java.util.List;
import java.util.Stack;

/**
 * @class DrawPile
 * @brief Representa el mazo de robar en el juego, donde los jugadores toman cartas.
 */
public class DrawPile {

    /**
     * @brief Pila de cartas que componen el mazo de robar.
     */
    private final Stack<Card> cards = new Stack<>();

    /**
     * @brief Constructor que inicializa el mazo de robar con una lista de cartas barajadas.
     * 
     * @param shuffledCards Lista de cartas ya barajadas para inicializar el mazo.
     */
    public DrawPile(List<Card> shuffledCards) {
        cards.addAll(shuffledCards);
    }

    /**
     * @brief Extrae y devuelve la carta superior del mazo.
     * 
     * @return La carta superior del mazo.
     * @throws EmptyStackException si el mazo está vacío.
     */
    public Card drawCard() {
        return cards.pop();
    }

    /**
     * @brief Obtiene el tamaño actual del mazo.
     * 
     * @return El número de cartas restantes en el mazo.
     */
    public int getSize() {
        return cards.size();
    }
}
