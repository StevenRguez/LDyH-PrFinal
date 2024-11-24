package domain.game;

import domain.card.*;
import domain.common.DomainEventPublisher;
import domain.common.Entity;
import domain.game.events.CardDrawn;
import domain.game.events.CardPlayed;
import domain.game.events.GameOver;
import domain.player.ImmutablePlayer;
import domain.player.Player;
import domain.player.PlayerRoundIterator;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

/**
 * @class Game
 * @brief Representa una partida del juego, gestionando jugadores, cartas y reglas.
 */
public class Game extends Entity {

    /** 
     * @brief Iterador que gestiona el orden y las rondas de los jugadores.
     */
    private final PlayerRoundIterator players;

    /**
     * @brief Mazo de cartas para robar.
     */
    private DrawPile drawPile;

    /**
     * @brief Pila de descarte donde se colocan las cartas jugadas.
     */
    private final Stack<Card> discardPile = new Stack<>();

    /**
     * @brief Jugador ganador de la partida.
     */
    private ImmutablePlayer winner = null;

    /**
     * @brief Contador de movimientos realizados en la partida.
     */
    private int moveCount;

    /**
     * @brief Tiempo de inicio de la partida.
     */
    private Instant startTime;

    /**
     * @brief Tiempo de finalización de la partida.
     */
    private Instant endTime;

    /**
     * @brief Registro de movimientos realizados por cada jugador.
     */
    private Map<UUID, Integer> playerMoves = new HashMap<>();

    /**
     * @brief Constructor de la partida.
     * 
     * @param drawPile Mazo inicial de cartas.
     * @param players Iterador que gestiona a los jugadores.
     */
    public Game(DrawPile drawPile, PlayerRoundIterator players) {
        super();
        this.drawPile = drawPile;
        this.players = players;
        this.moveCount = 0;
        this.startTime = Instant.now();
        startDiscardPile();
    }

    /**
     * @brief Devuelve un flujo de los jugadores en su estado inmutable.
     * @return Stream de jugadores inmutables.
     */
    public Stream<ImmutablePlayer> getPlayers() {
        return players.stream().map(Player::toImmutable);
    }

    /**
     * @brief Devuelve el jugador actual.
     * @return Jugador actual en estado inmutable.
     */
    public ImmutablePlayer getCurrentPlayer() {
        return players.getCurrentPlayer().toImmutable();
    }

    /**
     * @brief Obtiene las cartas en mano de un jugador.
     * 
     * @param playerId Identificador del jugador.
     * @return Stream de cartas en la mano del jugador.
     */
    public Stream<Card> getHandCards(UUID playerId) {
        return players.getPlayerById(playerId).getHandCards();
    }

    /**
     * @brief Obtiene la carta superior de la pila de descarte.
     * @return Carta en la cima de la pila de descarte.
     */
    public Card peekTopCard() {
        return discardPile.peek();
    }

    /**
     * @brief Devuelve el número total de movimientos realizados en la partida.
     * @return Número de movimientos.
     */
    public int getMoveCount() {
        return moveCount;
    }

    /**
     * @brief Devuelve el tiempo de inicio de la partida.
     * @return Instante de inicio de la partida.
     */
    public Instant getStartTime() {
        return startTime;
    }

    /**
     * @brief Calcula la duración de la partida.
     * @return Duración de la partida como cadena de texto.
     */
    public String getGameDuration() {
        if (endTime == null) {
            return "La partida no ha terminado.";
        }
        Duration duration = Duration.between(startTime, endTime);
        long minutes = duration.toMinutes();
        long seconds = duration.getSeconds() % 60;
        return String.format("%d minutos y %d segundos", minutes, seconds);
    }

    /**
     * @brief Inicializa la pila de descarte con la primera carta del mazo de robar.
     */
    private void startDiscardPile() {
        var card = drawPile.drawCard();
        switch (card.getType()) {
            case NUMBER, WILD_COLOR -> discard(card);
            case SKIP -> {
                discard(card);
                players.next();
            }
            case REVERSE -> {
                discard(card);
                reverse();
            }
            case DRAW_TWO -> {
                discard(card);
                drawTwoCards(players.getCurrentPlayer());
                players.next();
            }
            case WILD_DRAW_FOUR -> {
                recreateDrawPile(card);
                startDiscardPile();
            }
            default -> throw new UnsupportedOperationException("Unsupported card type " + card.getType());
        }
    }

    /**
     * @brief Juega una carta para el jugador actual.
     * 
     * @param playerId Identificador del jugador.
     * @param playedCard Carta jugada.
     */
    public void playCard(UUID playerId, Card playedCard) {
        playCard(playerId, playedCard, false);
    }

    /**
     * @brief Juega una carta para el jugador actual, validando si dijo "Uno".
     * 
     * @param playerId Identificador del jugador.
     * @param playedCard Carta jugada.
     * @param hasSaidUno Indica si el jugador dijo "Uno".
     */
    public void playCard(UUID playerId, Card playedCard, boolean hasSaidUno) {
        if (isOver()) {
            throw new IllegalStateException("Game is over");
        }
        moveCount++;
        validatePlayedCard(playerId, playedCard);

        playerMoves.put(playerId, playerMoves.getOrDefault(playerId, 0) + 1);

        // Lógica para diferentes tipos de cartas
        switch (playedCard.getType()) {
            case NUMBER -> { /* Lógica para cartas numéricas */ }
            case SKIP -> { /* Lógica para cartas de "saltar" */ }
            case REVERSE -> { /* Lógica para cartas de "reversa" */ }
            case DRAW_TWO -> { /* Lógica para cartas de "robar dos" */ }
            case WILD_COLOR, WILD_DRAW_FOUR -> { /* Lógica para cartas comodín */ }
            default -> rejectPlayedCard(playedCard);
        }

        DomainEventPublisher.publish(new CardPlayed(playerId, playedCard));

        if (isOver()) {
            endTime = Instant.now();
            DomainEventPublisher.publish(new GameOver(winner));
        }
    }

        /**
     * @brief Permite al jugador actual robar una carta del mazo.
     * 
     * @param playerId Identificador del jugador.
     */
    public void drawCard(UUID playerId) {
        if (getCurrentPlayer().getId().equals(playerId)) {
            moveCount++; // Incrementa el contador de movimientos
            var drawnCards = drawCards(players.getCurrentPlayer(), 1);
            tryToPlayDrawnCard(playerId, drawnCards.get(0));
        }
    }

    /**
     * @brief Verifica si el juego ha terminado.
     * 
     * @return true si el juego ha terminado, false en caso contrario.
     */
    public boolean isOver() {
        return winner != null;
    }

    /**
     * @brief Obtiene al jugador ganador de la partida.
     * 
     * @return Jugador ganador en estado inmutable.
     */
    public ImmutablePlayer getWinner() {
        return winner;
    }

    /**
     * @brief Intenta jugar una carta recién robada. Si no es válida, el turno pasa al siguiente jugador.
     * 
     * @param playerId Identificador del jugador.
     * @param drawnCard Carta recién robada.
     */
    private void tryToPlayDrawnCard(UUID playerId, Card drawnCard) {
        try {
            var cardToPlay = CardUtil.isWildCard(drawnCard)
                    ? new WildCard(drawnCard.getType(), peekTopCard().getColor())
                    : drawnCard;

            playCard(playerId, cardToPlay);
        } catch (Exception ex) {
            // Si no se puede jugar la carta, pasa el turno
            players.next();
            DomainEventPublisher.publish(new CardDrawn(playerId));
        }
    }

    /**
     * @brief Valida que la carta jugada sea válida según las reglas del juego.
     * 
     * @param playerId Identificador del jugador.
     * @param card Carta que se intenta jugar.
     */
    private void validatePlayedCard(UUID playerId, Card card) {
        var currentPlayer = players.getCurrentPlayer();
        if (!currentPlayer.getId().equals(playerId)) {
            throw new IllegalArgumentException(String.format("No es el turno del jugador con ID %s", playerId));
        }

        if (!currentPlayer.hasHandCard(card)) {
            throw new IllegalArgumentException(String.format("La carta %s no está en la mano del jugador", card));
        }
    }

    /**
     * @brief Verifica las reglas para jugar una carta numérica.
     * 
     * @param playedCard Carta jugada.
     */
    private void checkNumberCardRule(Card playedCard) {
        var topCard = peekTopCard();

        if (isFirstDiscardAWildCard() || CardRules.isValidNumberCard(topCard, (NumberCard) playedCard)) {
            return;
        }

        rejectPlayedCard(playedCard);
    }

    /**
     * @brief Verifica las reglas para jugar una carta de acción.
     * 
     * @param playedCard Carta jugada.
     */
    private void checkActionCardRule(Card playedCard) {
        var topCard = peekTopCard();

        if (isFirstDiscardAWildCard() || CardRules.isValidActionCard(topCard, (ActionCard) playedCard)) {
            return;
        }

        rejectPlayedCard(playedCard);
    }

    /**
     * @brief Verifica las reglas para jugar una carta comodín.
     * 
     * @param playedCard Carta jugada.
     */
    private void checkWildCardRule(Card playedCard) {
        if (!CardRules.isValidWildCard((WildCard) playedCard)) {
            rejectPlayedCard(playedCard);
        }
    }

    /**
     * @brief Verifica si la primera carta descartada es un comodín.
     * 
     * @return true si la primera carta es un comodín, false en caso contrario.
     */
    private boolean isFirstDiscardAWildCard() {
        return discardPile.size() == 1 && peekTopCard().getType() == CardType.WILD_COLOR;
    }

    /**
     * @brief Reorganiza el mazo de robar utilizando las cartas descartadas.
     * 
     * @param card Carta superior actual.
     */
    private void recreateDrawPile(Card card) {
        if (drawPile.getSize() == 0) {
            throw new IllegalStateException("No hay suficientes cartas para reorganizar el mazo");
        }

        drawPile = DealerService.shuffle(drawPile, card);
    }

    /**
     * @brief Acepta la carta jugada, la descarta y actualiza el estado del jugador.
     * 
     * @param card Carta jugada.
     * @param hasSaidUno Indica si el jugador dijo "Uno".
     */
    private void acceptPlayedCard(Card card, boolean hasSaidUno) {
        players.getCurrentPlayer().removePlayedCard(card);
        discard(card);

        var remainingTotalCards = getCurrentPlayer().getTotalCards();
        checkSaidUno(remainingTotalCards, hasSaidUno);

        if (remainingTotalCards == 0) {
            winner = getCurrentPlayer();
        }
    }

    /**
     * @brief Verifica si el jugador dijo "Uno" correctamente.
     * 
     * @param remainingTotalCards Número de cartas restantes del jugador.
     * @param hasSaidUno Indica si el jugador dijo "Uno".
     */
    private void checkSaidUno(int remainingTotalCards, boolean hasSaidUno) {
        if (remainingTotalCards == 1 && !hasSaidUno) {
            drawCards(players.getCurrentPlayer(), 2);
        }
    }

    /**
     * @brief Descarta una carta añadiéndola a la pila de descarte.
     * 
     * @param card Carta que se descarta.
     */
    private void discard(Card card) {
        discardPile.add(card);
    }

    /**
     * @brief Cambia la dirección del juego.
     */
    private void reverse() {
        players.reverseDirection();
        players.next();
    }

    /**
     * @brief Hace que el jugador actual robe dos cartas.
     * 
     * @param player Jugador actual.
     */
    private void drawTwoCards(Player player) {
        drawCards(player, 2);
    }

    /**
     * @brief Hace que el jugador actual robe cuatro cartas.
     * 
     * @param player Jugador actual.
     */
    private void drawFourCards(Player player) {
        drawCards(player, 4);
    }

    /**
     * @brief Permite a un jugador robar un número específico de cartas.
     * 
     * @param player Jugador que roba las cartas.
     * @param total Número de cartas a robar.
     * @return Lista de cartas robadas.
     */
    private List<Card> drawCards(Player player, int total) {
        int min = Math.min(total, drawPile.getSize());
        var drawnCards = new ArrayList<Card>();

        for (int i = 0; i < min; i++) {
            var drawnCard = drawPile.drawCard();
            drawnCards.add(drawnCard);

            player.addToHandCards(drawnCard);
        }

        return drawnCards;
    }

    /**
     * @brief Rechaza una carta jugada por ser inválida.
     * 
     * @param playedCard Carta jugada.
     * @throws IllegalArgumentException Si la carta no es válida.
     */
    private void rejectPlayedCard(Card playedCard) {
        throw new IllegalArgumentException(
                String.format("La carta jugada %s no es válida para %s", playedCard, peekTopCard()));
    }
}
