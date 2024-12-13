package ui.view;

import domain.card.Card;
import domain.card.CardColor;
import domain.card.NumberCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class TestCardView {

    private Card testCard;
    private List<Card> clickedCards;
    private CardView cardView;

    @BeforeEach
    void setUp() {
        testCard = new NumberCard(5, CardColor.RED);
        clickedCards = new ArrayList<>();
        cardView = new CardView(testCard, clickedCards::add);
    }

    @Test
    void testInitView() {
        Dimension expectedDimension = new Dimension(100, 150);
        assertEquals(expectedDimension, cardView.getDimension());
        assertNotNull(cardView.getBorder());
    }

    @Test
    void testMouseEnteredShowsHoverEffect() {
        cardView.setLocation(new Point(0, 0));
        MouseEvent event = new MouseEvent(cardView, MouseEvent.MOUSE_ENTERED, System.currentTimeMillis(), 0, 0, 0, 0, false);

        cardView.getMouseListeners()[0].mouseEntered(event);

        assertEquals(new Point(0, -20), cardView.getLocation());
        assertNotNull(cardView.getBorder());
    }

    @Test
    void testMouseExitedRemovesHoverEffect() {
        cardView.setLocation(new Point(0, -20));
        MouseEvent event = new MouseEvent(cardView, MouseEvent.MOUSE_EXITED, System.currentTimeMillis(), 0, 0, 0, 0, false);

        cardView.getMouseListeners()[0].mouseExited(event);

        assertEquals(new Point(0, 0), cardView.getLocation());
        assertNotNull(cardView.getBorder());
    }

    @Test
    void testMousePressedCallsConsumer() {
        MouseEvent event = new MouseEvent(cardView, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), 0, 0, 0, 0, false);

        cardView.getMouseListeners()[0].mousePressed(event);

        assertEquals(1, clickedCards.size());
        assertEquals(testCard, clickedCards.get(0));
    }

    @Test
    void testPaintComponent() {
        JPanel testPanel = new JPanel();
        testPanel.add(cardView);
        testPanel.paintComponents(testPanel.getGraphics());

        assertTrue(true);
    }

    @Test
    void testGetDimension() {
        Dimension expectedDimension = new Dimension(100, 150);
        assertEquals(expectedDimension, cardView.getDimension());
    }
}
