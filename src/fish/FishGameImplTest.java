package fish;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FishGameImplTest {

    FishGameImpl gameImp;

    @Before
    public void setUp() throws Exception {
        gameImp = new FishGameImpl(2);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCanMove() {
        FishHumanPlayer bob = new FishHumanPlayer();
        assertEquals(gameImp.canMove(bob), true);
    }

    @Test
    public void testCheckHaveCard() {
        Vector<Card> hand = new Vector<Card>();
        hand.add(new Card(5, 5, null, null, null, null));
        assertEquals(gameImp.checkHaveCard(5, hand), 1);
    }

    @Test
    public void testInitDeck() {
        Vector<Card> deck = gameImp.initDeck();
        assertEquals(deck.size(), 52);
    }

    @Test
    public void testInitCard() {
        Card expected = new Card(4, 4, null, null, null, null);
        Card result = gameImp.initCard(4);
        assertEquals(expected.getRank(), result.getRank());
    }

}
