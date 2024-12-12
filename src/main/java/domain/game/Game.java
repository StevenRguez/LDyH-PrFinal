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
import java.util.logging.Logger;

/**
 * Clase principal que representa un juego de cartas.
 * Maneja la lógica del juego, los turnos de los jugadores y las reglas de las cartas.
 */
public class Game extends Entity {

    /**
     * Logger para registrar eventos y mensajes relacionados con el juego.
     */
    private static final Logger logger = Logger.getLogger(Game.class.getName());

    /**
     * Iterador para gestionar los turnos de los jugadores.
     */
    private final PlayerRoundIterator players;

    /**
     * Mazo de cartas para robar.
     */
    private DrawPile drawPile;

    /**
     * Pila de descarte que contiene las cartas jugadas.
     */
    private final Stack<Card> discardPile = new Stack<>();

    /**
     * Jugador ganador del juego.
     */
    private ImmutablePlayer winner = null;

    /**
     * Contador del número de movimientos realizados en la partida.
     */
    private int moveCount;

    /**
     * Momento en que comenzó la partida.
     */
    private Instant startTime;

    /**
     * Momento en que terminó la partida (si ya finalizó).
     */
    private Instant endTime;

    /**
     * Mapa que rastrea el número de movimientos realizados por cada jugador.
     */
    private Map<UUID, Integer> playerMoves = new HashMap<>();

    /**
     * Constructor que inicializa el juego con un mazo para robar y una lista de jugadores.
     * También establece el tiempo de inicio y comienza el mazo de descarte.
     *
     * @param drawPile mazo inicial de cartas para robar.
     * @param players  iterador de turnos para los jugadores.
     */
    public Game(DrawPile drawPile, PlayerRoundIterator players) {
        super();
        this.drawPile = drawPile;
        this.players = players;
        this.moveCount = 0; // Inicializamos el contador
        this.startTime = Instant.now(); // Registramos el inicio de la partida
        startDiscardPile();
    }

    /**
     * Devuelve una secuencia inmutable de los jugadores de la partida.
     *
     * @return secuencia de jugadores.
     */
    public Stream<ImmutablePlayer> getPlayers() {
        return players.stream().map(Player::toImmutable);
    }

    /**
     * Devuelve una secuencia inmutable de los jugadores de la partida.
     *
     * @return secuencia de jugadores.
     */
    public ImmutablePlayer getCurrentPlayer() {
        return players.getCurrentPlayer().toImmutable();
    }

    /**
     * Devuelve las cartas en mano de un jugador específico.
     *
     * @param playerId identificador del jugador.
     * @return secuencia de cartas en la mano del jugador.
     */
    public Stream<Card> getHandCards(UUID playerId) {
        return players.getPlayerById(playerId).getHandCards();
    }

    /**
     * Obtiene la carta superior del mazo de descarte.
     *
     * @return carta superior del mazo de descarte.
     */
    public Card peekTopCard() {
        return discardPile.peek();
    }

    /**
     * Devuelve el número total de movimientos realizados en la partida.
     *
     * @return número de movimientos.
     */
    public int getMoveCount() {
        return moveCount;
    }

    /**
     * Devuelve el momento en que comenzó la partida.
     *
     * @return tiempo de inicio de la partida.
     */
    public Instant getStartTime() {
        return startTime;
    }

    /**
     * Calcula y devuelve la duración de la partida.
     *
     * @return duración de la partida en formato de minutos y segundos.
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
     * Configura el estado inicial del mazo de descarte según la primera carta extraída del mazo de robo.
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
     * Permite a un jugador jugar una carta específica.
     *
     * @param playerId   identificador del jugador.
     * @param playedCard carta que se desea jugar.
     */
    public void playCard(UUID playerId, Card playedCard) {
        playCard(playerId, playedCard, false);
    }

    /**
     * Permite a un jugador jugar una carta, indicando si dijo "UNO" en su turno.
     *
     * @param playerId   identificador del jugador.
     * @param playedCard carta que se desea jugar.
     * @param hasSaidUno indica si el jugador dijo "UNO".
     */
    public void playCard(UUID playerId, Card playedCard, boolean hasSaidUno) {
        if (isOver()) {
            throw new IllegalStateException("Game is over");
        }
        moveCount++; // Incrementamos el contador de movimientos
        validatePlayedCard(playerId, playedCard);
        // Contamos los movimientos por jugador
        playerMoves.put(playerId, playerMoves.getOrDefault(playerId, 0) + 1);
        logger.info("Jugador " + getCurrentPlayer().getName() + " ha realizado " + playerMoves.get(playerId) + " movimientos.");
        switch (playedCard.getType()) {
            case NUMBER -> {
                checkNumberCardRule(playedCard);
                acceptPlayedCard(playedCard, hasSaidUno);
                players.next();
            }
            case SKIP -> {
                checkActionCardRule(playedCard);
                acceptPlayedCard(playedCard, hasSaidUno);
                players.next();
                players.next();
            }
            case REVERSE -> {
                checkActionCardRule(playedCard);
                acceptPlayedCard(playedCard, hasSaidUno);
                reverse();
            }
            case DRAW_TWO -> {
                checkActionCardRule(playedCard);
                acceptPlayedCard(playedCard, hasSaidUno);
                players.next();
                drawTwoCards(players.getCurrentPlayer());
                players.next();
            }
            case WILD_COLOR -> {
                checkWildCardRule(playedCard);
                acceptPlayedCard(playedCard, hasSaidUno);
                players.next();
            }
            case WILD_DRAW_FOUR -> {
                checkWildCardRule(playedCard);
                acceptPlayedCard(playedCard, hasSaidUno);
                players.next();
                drawFourCards(players.getCurrentPlayer());
                players.next();
            }
            default -> rejectPlayedCard(playedCard);
        }
        DomainEventPublisher.publish(new CardPlayed(playerId, playedCard));
        if (isOver()) {
            endTime = Instant.now(); // Registramos el tiempo de finalización
            System.out.println("La partida ha terminado.");
            System.out.println("Ganador: " + winner.getName());
            System.out.println("Duración de la partida: " + getGameDuration());
            DomainEventPublisher.publish(new GameOver(winner));
        }
    }

    /**
     * Metodo para extraer una carta
     * @param playerId juagdor que recibe la carta
     */
    public void drawCard(UUID playerId) {
        if (getCurrentPlayer().getId().equals(playerId)) {
            moveCount++; // Incrementamos el contador de movimientos
            var drawnCards = drawCards(players.getCurrentPlayer(), 1);
            tryToPlayDrawnCard(playerId, drawnCards.get(0));
        }
    }

    /**
     * Indica si la partida ha terminado.
     *
     * @return true si la partida terminó, false en caso contrario.
     */
    public boolean isOver() {
        return winner != null;
    }

    /**
     * Obtiene el jugador ganador de la partida.
     *
     * @return jugador ganador o null si la partida no ha terminado.
     */
    public ImmutablePlayer getWinner() {
        return winner;
    }

    /**
     * Intenta jugar una carta recién robada por el jugador.
     * Si la carta no se puede jugar, se pasa el turno.
     *
     * @param playerId   identificador del jugador.
     * @param drawnCard  carta recién robada.
     */
    private void tryToPlayDrawnCard(UUID playerId, Card drawnCard) {
        try {
            var cardToPlay = CardUtil.isWildCard(drawnCard)
                    ? new WildCard(drawnCard.getType(), peekTopCard().getColor())
                    : drawnCard;
            playCard(playerId, cardToPlay);
        } catch (Exception ex) {
            // Drawn couldn't be played, so just switch turn
            players.next();
            DomainEventPublisher.publish(new CardDrawn(playerId));
        }
    }

    /**
     * Valida si la carta jugada por el jugador es válida según las reglas del juego.
     *
     * @param playerId identificador del jugador.
     * @param card     carta jugada por el jugador.
     */
    private void validatePlayedCard(UUID playerId, Card card) {
        var currentPlayer = players.getCurrentPlayer();
        if (!currentPlayer.getId().equals(playerId)) {
            throw new IllegalArgumentException(String.format("Not the turn of Player ID %s", playerId));
        }
        if (!currentPlayer.hasHandCard(card)) {
            throw new IllegalArgumentException(String.format("Card %s does not exist in player's hand cards", card));
        }
    }

    /**
     * Verifica si la carta jugada es válida según las reglas de las cartas numéricas.
     *
     * @param playedCard carta jugada por el jugador.
     */
    private void checkNumberCardRule(Card playedCard) {
        var topCard = peekTopCard();
        if (isFirstDiscardAWildCard() || CardRules.isValidNumberCard(topCard, (NumberCard) playedCard)) {
            return;
        }
        rejectPlayedCard(playedCard);
    }

    /**
     * Verifica si la carta jugada es válida según las reglas de las cartas de acción (salto, reversa, etc.).
     *
     * @param playedCard carta jugada por el jugador.
     */
    private void checkActionCardRule(Card playedCard) {
        var topCard = peekTopCard();
        if (isFirstDiscardAWildCard() || CardRules.isValidActionCard(topCard, (ActionCard) playedCard)) {
            return;
        }
        rejectPlayedCard(playedCard);
    }

    /**
     * Verifica si la carta jugada es válida según las reglas de las cartas comodín (color y más).
     *
     * @param playedCard carta jugada por el jugador.
     */
    private void checkWildCardRule(Card playedCard) {
        if (!CardRules.isValidWildCard((WildCard) playedCard)) {
            rejectPlayedCard(playedCard);
        }
    }

    /**
     * Comprueba si la carta descartada es un comodín
     * @return ¿es un comodín?
     */
    private boolean isFirstDiscardAWildCard() {
        return discardPile.size() == 1 && peekTopCard().getType() == CardType.WILD_COLOR;
    }

    /**
     * Reinicia el mazo de cartas después de que una carta comodín haya sido jugada.
     *
     * @param card carta que causó el reinicio del mazo.
     */
    private void recreateDrawPile(Card card) {
        if (drawPile.getSize() == 0) {
            throw new IllegalStateException("Not enough cards to recreate draw pile");
        }
        drawPile = DealerService.shuffle(drawPile, card);
    }

    /**
     * Acepta la carta jugada y la coloca en la pila de descarte.
     *
     * @param card carta que ha sido jugada.
     * @param hasSaidUno si el jugador dijo "UNO".
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
     * Metodo para comprobar si se ha dicho uno
     * @param remainingTotalCards total de cartas
     * @param hasSaidUno ¿ha dicho uno?
     */
    private void checkSaidUno(int remainingTotalCards, boolean hasSaidUno) {
        if (remainingTotalCards == 1 && !hasSaidUno) {
            drawCards(players.getCurrentPlayer(), 2);
        }
    }

    /**
     * Coloca una carta en la pila de descarte.
     *
     * @param card carta que será colocada en la pila de descarte.
     */
    private void discard(Card card) {
        discardPile.add(card);
    }

    /**
     * Cambia el orden del juego de manera que los jugadores jueguen en orden inverso.
     */
    private void reverse() {
        players.reverseDirection();
        players.next();
    }

    /**
     * Carta robada: Permite que un jugador robe dos cartas.
     *
     * @param player jugador al que se le deben robar dos cartas.
     */
    private void drawTwoCards(Player player) {
        drawCards(player, 2);
    }

    /**
     * Carta comodín: Permite que un jugador robe cuatro cartas.
     *
     * @param player jugador al que se le deben robar cuatro cartas.
     */
    private void drawFourCards(Player player) {
        drawCards(player, 4);
    }

    /**
     * Metodo para extraer cartas del mazo
     * @param player jugador
     * @param total total de cartas del jugador
     * @return total de cartas extraídas
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
     * Rechazar carta jugada
     * @param playedCard carta jugada
     */
    private void rejectPlayedCard(Card playedCard) {
        throw new IllegalArgumentException(
                String.format("Played card %s is not valid for %s", playedCard, peekTopCard()));
    }
}