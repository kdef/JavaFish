package fish;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FishProxyPlayerTest {

    FishProxyPlayer fpp;

    @Before
    public void setUp() throws Exception {
        fpp = new FishProxyPlayer();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDecodeAction() {
        String input = "msg #hello bob, how are you today?";
        FishMessageSentAction msg = (FishMessageSentAction) fpp
                .decodeAction(input);
        String result = msg.getMessage();

        assertEquals(result, "hello bob, how are you today?");
    }

    @Test
    public void testEncodeState() {
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

        FishState input = new FishState(newDeck, chatLog, CurrPlayer, p1, p2,
                p3, p4, moveLog, scores);

        String expected = "11-22-33-!#hi bob.#meowmeowmeow.#i love turtles.#hi!.#!#3!#11-22-33-!#11-22-33-!#11-22-33-!#11-22-33-!#1-2-0-55-0-1#2-1-1-44-1-0#!#1-2-3-4-!#4";

        assertEquals(fpp.encodeState(input), expected);
    }

}
