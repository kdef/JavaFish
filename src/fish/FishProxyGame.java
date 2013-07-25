package fish;

import java.util.Vector;

import game.*;

/**
 * Sets up a network game to pass info over the network
 * 
 * @author Joey Devlin, Nate Kozlowski, Kyle DeFrancia
 * @version 12/10/11
 */
class FishProxyGame extends ProxyGame implements FishGame {

    static final int PORT = 1111;

    public FishProxyGame(String hostName) {
        super(hostName);
    }

    /**
     * Turns an action into a string
     * 
     * @param ga
     *            action being encoded
     */
    public String encodeAction(GameAction ga) {

        FishMoveAction act = (FishMoveAction) ga;

        // determine type of action
        if (act.isAskPlayer()) {
            // typecast to correct action and convert to string
            FishAskPlayerAction askAct = (FishAskPlayerAction) act;

            int asker = askAct.getAsker();
            int asked = askAct.getAsked();
            int id = askAct.getCard().getID();
            return "ask " + asker + " " + asked + " " + id;
        } else if (act.isMessageSent()) {
            // typecast to correct action and convert to string
            FishMessageSentAction msgAct = (FishMessageSentAction) act;

            String msg = msgAct.getMessage();
            return "msg " + "#" + msg;
        } else {
            return "???";
        }

    }

    /**
     * Decodes a string into an action
     * 
     * @param str
     *            the string we are decoding
     */
    public GameState decodeState(String str) {

        String[] args = str.split("!#");

        // args[0] = deck;
        // args[1] = chat;
        // args[2] = currPlayer;
        // args[3] = hand1;
        // args[4] = hand2;
        // args[5] = hand3;
        // args[6] = hand4;
        // args[7] = moves;
        // args[8] = scores;
        // args[9] = numPlayers;

        // decode numPlayers
        int numPlayers = Integer.parseInt(args[9]);

        // decode deck
        String[] ids = args[0].split("-");
        Vector<Card> newDeck = new Vector<Card>();
        for (int i = 0; i < ids.length; i++) {
            int id = Integer.parseInt(ids[i]);
            Card card = FishGameImpl.initCard(id);
            newDeck.add(card);
        }

        // decode chat
        String[] chats = args[1].split(".#");
        Vector<String> chatLog = new Vector<String>();
        for (int i = 0; i < chats.length; i++) {
            chatLog.add(chats[i]);
        }

        // decode current player
        System.out.println("CurrPlayer:");
        int CurrPlayer = Integer.parseInt(args[2]);

        // decode hands
        String[] hand1 = args[3].split("-");
        Vector<Card> p1 = new Vector<Card>();
        for (int i = 0; i < hand1.length; i++) {
            int id = Integer.parseInt(hand1[i]);
            Card card = FishGameImpl.initCard(id);
            p1.add(card);
        }

        String[] hand2 = args[4].split("-");
        Vector<Card> p2 = new Vector<Card>();
        for (int i = 0; i < hand2.length; i++) {
            int id = Integer.parseInt(hand2[i]);
            Card card = FishGameImpl.initCard(id);
            p2.add(card);
        }

        Vector<Card> p3 = new Vector<Card>();
        if (numPlayers >= 3) {
            String[] hand3 = args[5].split("-");
            for (int i = 0; i < hand3.length; i++) {
                int id = Integer.parseInt(hand3[i]);
                Card card = FishGameImpl.initCard(id);
                p3.add(card);
            }
        } else {
            p3 = null;
        }

        Vector<Card> p4 = new Vector<Card>();
        if (numPlayers == 4) {
            String[] hand4 = args[6].split("-");
            for (int i = 0; i < hand4.length; i++) {
                int id = Integer.parseInt(hand4[i]);
                Card card = FishGameImpl.initCard(id);
                p4.add(card);
            }
        } else {
            p4 = null;
        }

        // decode moves
        String[] moves = args[7].split("#");
        Vector<Move> moveLog = new Vector<Move>();
        for (int i = 0; i < moves.length; i++) {
            String[] parts = moves[i].split("-");

            // convert to ints
            int[] iParts = new int[parts.length];
            for (int j = 0; j < parts.length; j++) {
                iParts[j] = Integer.parseInt(parts[j]);
            }

            boolean completed;
            if (iParts[4] == 1) {
                completed = true;
            } else {
                completed = false;
            }

            boolean received;
            if (iParts[5] == 1) {
                received = true;
            } else {
                received = false;
            }

            moveLog.add(new Move(iParts[0], iParts[1], iParts[2], iParts[3],
                    completed, received));
        }

        // decode score
        String[] score = args[8].split("-");
        int[] scores = new int[4];
        for (int i = 0; i < 4; i++) {
            scores[i] = Integer.parseInt(score[i]);
        }

        return new FishState(newDeck, chatLog, CurrPlayer, p1, p2, p3, p4,
                moveLog, scores);
    }

    /**
     * If a null player is allowed
     */
    public boolean nullPlayersAllowed() {
        return true;
    }

    /**
     * Minimum players allowed to play the game
     */
    public int minPlayersAllowed() {
        return 2;
    }

    /**
     * Maximum players allowed to play the game
     */
    public int maxPlayersAllowed() {
        return 4;
    }

    protected int getAdmPortNum() {
        return PORT;
    }

}