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
public class Game extends Entity {
    private final PlayerRoundIterator players;
    private DrawPile drawPile;
    private final Stack<Card> discardPile = new Stack<>();
    private ImmutablePlayer winner = null;
    private int moveCount; // Contador de movimientos
    private Instant startTime; // Tiempo de inicio
    private Instant endTime; // Tiempo de finalización
    private Map<UUID, Integer> playerMoves = new HashMap<>(); // Mapa de movimientos por jugador
    public Game(DrawPile drawPile, PlayerRoundIterator players) {
        super();
        this.drawPile = drawPile;
        this.players = players;
        this.moveCount = 0; // Inicializamos el contador
        this.startTime = Instant.now(); // Registramos el inicio de la partida
        startDiscardPile();
    }
    public Stream<ImmutablePlayer> getPlayers() {
        return players.stream().map(Player::toImmutable);
    }
    public ImmutablePlayer getCurrentPlayer() {
        return players.getCurrentPlayer().toImmutable();
    }
    public Stream<Card> getHandCards(UUID playerId) {
        return players.getPlayerById(playerId).getHandCards();
    }
    public Card peekTopCard() {
        return discardPile.peek();
    }
    public int getMoveCount() {
        return moveCount;
    }
    public Instant getStartTime() {
        return startTime;
    }
    public String getGameDuration() {
        if (endTime == null) {
            return "La partida no ha terminado.";
        }
        Duration duration = Duration.between(startTime, endTime);
        long minutes = duration.toMinutes();
        long seconds = duration.getSeconds() % 60;
        return String.format("%d minutos y %d segundos", minutes, seconds);
    }
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
    public void playCard(UUID playerId, Card playedCard) {
        playCard(playerId, playedCard, false);
    }
    public void playCard(UUID playerId, Card playedCard, boolean hasSaidUno) {
        if (isOver()) {
            throw new IllegalStateException("Game is over");
        }
        moveCount++; // Incrementamos el contador de movimientos
        validatePlayedCard(playerId, playedCard);
        // Contamos los movimientos por jugador
        playerMoves.put(playerId, playerMoves.getOrDefault(playerId, 0) + 1);
        System.out.println("Jugador " + getCurrentPlayer().getName() + " ha realizado " + playerMoves.get(playerId) + " movimientos.");
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
    public void drawCard(UUID playerId) {
        if (getCurrentPlayer().getId().equals(playerId)) {
            moveCount++; // Incrementamos el contador de movimientos
            var drawnCards = drawCards(players.getCurrentPlayer(), 1);
            tryToPlayDrawnCard(playerId, drawnCards.get(0));
        }
    }
    public boolean isOver() {
        return winner != null;
    }
    public ImmutablePlayer getWinner() {
        return winner;
    }
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
    private void validatePlayedCard(UUID playerId, Card card) {
        var currentPlayer = players.getCurrentPlayer();
        if (!currentPlayer.getId().equals(playerId)) {
            throw new IllegalArgumentException(String.format("Not the turn of Player ID %s", playerId));
        }
        if (!currentPlayer.hasHandCard(card)) {
            throw new IllegalArgumentException(String.format("Card %s does not exist in player's hand cards", card));
        }
    }
    private void checkNumberCardRule(Card playedCard) {
        var topCard = peekTopCard();
        if (isFirstDiscardAWildCard() || CardRules.isValidNumberCard(topCard, (NumberCard) playedCard)) {
            return;
        }
        rejectPlayedCard(playedCard);
    }
    private void checkActionCardRule(Card playedCard) {
        var topCard = peekTopCard();
        if (isFirstDiscardAWildCard() || CardRules.isValidActionCard(topCard, (ActionCard) playedCard)) {
            return;
        }
        rejectPlayedCard(playedCard);
    }
    private void checkWildCardRule(Card playedCard) {
        if (!CardRules.isValidWildCard((WildCard) playedCard)) {
            rejectPlayedCard(playedCard);
        }
    }
    private boolean isFirstDiscardAWildCard() {
        return discardPile.size() == 1 && peekTopCard().getType() == CardType.WILD_COLOR;
    }
    private void recreateDrawPile(Card card) {
        if (drawPile.getSize() == 0) {
            throw new IllegalStateException("Not enough cards to recreate draw pile");
        }
        drawPile = DealerService.shuffle(drawPile, card);
    }
    private void acceptPlayedCard(Card card, boolean hasSaidUno) {
        players.getCurrentPlayer().removePlayedCard(card);
        discard(card);
        var remainingTotalCards = getCurrentPlayer().getTotalCards();
        checkSaidUno(remainingTotalCards, hasSaidUno);
        if (remainingTotalCards == 0) {
            winner = getCurrentPlayer();
        }
    }
    private void checkSaidUno(int remainingTotalCards, boolean hasSaidUno) {
        if (remainingTotalCards == 1 && !hasSaidUno) {
            drawCards(players.getCurrentPlayer(), 2);
        }
    }
    private void discard(Card card) {
        discardPile.add(card);
    }
    private void reverse() {
        players.reverseDirection();
        players.next();
    }
    private void drawTwoCards(Player player) {
        drawCards(player, 2);
    }
    private void drawFourCards(Player player) {
        drawCards(player, 4);
    }
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
    private void rejectPlayedCard(Card playedCard) {
        throw new IllegalArgumentException(
                String.format("Played card %s is not valid for %s", playedCard, peekTopCard()));
    }
}