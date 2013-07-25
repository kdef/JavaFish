package fish;

import java.util.Vector;

import game.*;
import org.junit.Test;
import junit.framework.TestCase;

public class FishProxyGameTest extends TestCase {

    @Test
    public void testEncodeAction() {
        FishHumanPlayer player = new FishHumanPlayer();
        Card card = new Card(4, 33, "fName", "fName", "fName", "fName");
        FishAskPlayerAction ask = new FishAskPlayerAction(1, 2, card, player);
        FishProxyGame game = new FishProxyGame("meow");

        String expected = "ask " + 1 + " " + 2 + " " + 33;
        String result = game.encodeAction(ask);
        assertEquals(expected, result);
    }

    @Test
    public void testDecodeState() {
        String input = "11-22-33-!#hi bob.#meowmeowmeow.#i love turtles.#hi!.#!#3!#11-22-33-!#11-22-33-!#11-22-33-!#11-22-33-!#1-2-0-55-0-1#2-1-1-44-1-0#!#1-2-3-4-!#4";

        Vector<Card> newDeck = new Vector<Card>();
        newDeck.add(FishGameImpl.initCard(11));
        newDeck.add(FishGameImpl.initCard(22));
        newDeck.add(FishGameImpl.initCard(33));

        Vector<String> chatLog = new Vector<String>();
        chatLog.add("hi bob");
        chatLog.add("meowmeowmeow");
        chatLog.add("i love turtles");
        chatLog.add("hi!");

        int CurrPlayer = 3;
        Vector<Card> p1 = new Vector<Card>();
        p1.add(FishGameImpl.initCard(11));
        p1.add(FishGameImpl.initCard(22));
        p1.add(FishGameImpl.initCard(33));

        Vector<Card> p2 = new Vector<Card>();
        p2.add(FishGameImpl.initCard(11));
        p2.add(FishGameImpl.initCard(22));
        p2.add(FishGameImpl.initCard(33));

        Vector<Card> p3 = new Vector<Card>();
        p3.add(FishGameImpl.initCard(11));
        p3.add(FishGameImpl.initCard(22));
        p3.add(FishGameImpl.initCard(33));

        Vector<Card> p4 = new Vector<Card>();
        p4.add(FishGameImpl.initCard(11));
        p4.add(FishGameImpl.initCard(22));
        p4.add(FishGameImpl.initCard(33));

        Vector<Move> moveLog = new Vector<Move>();
        moveLog.add(new Move(1, 2, 0, 55, false, true));
        moveLog.add(new Move(2, 1, 1, 44, true, false));

        int[] scores = { 1, 2, 3, 4 };

        FishState expected = new FishState(newDeck, chatLog, CurrPlayer, p1,
                p2, p3, p4, moveLog, scores);
        FishProxyGame game = new FishProxyGame("meow");
        GameState resultG = game.decodeState(input);
        FishState result = (FishState) resultG;

        assertEquals(result.getDeck().size(), 3);
        assertEquals(result.getCurrentPlayer(), 3);
        assertEquals(result.getHandVal(0), 3);
        assertEquals(result.getHandVal(1), 3);
        assertEquals(result.getHandVal(2), 3);
        assertEquals(result.getHandVal(3), 3);
        assertEquals(result.getChat().get(2), "i love turtles");
        assertEquals(result.getLog().size(), 2);

    }
}
