package fish;

import java.util.*;

import game.*;

/**
 * A network player.
 * 
 * @author Joey Devlin, Nate Kozlowski, Kyle DeFrancia
 * @version 12/10/11
 */
class FishProxyPlayer extends ProxyPlayer implements FishPlayer {

    private Vector<Card> hand;
    private int ID;
    private int score;

    /**
     * Constructor
     * 
     * @param hand
     * @param ID
     * @param score
     */
    public FishProxyPlayer() {
        super();
    }

    /**
     * Encodes a state into a string
     * 
     * @param gs
     *            the state we are encoding into a string
     */
    protected String encodeState(GameState gs) {
        try {
            FishState fs = (FishState) gs;

            // get info
            int numPlayers = fs.getNumPlayers();
            Vector<Card> deck = fs.getDeck();
            Vector<String> chat = fs.getChat();
            int currPlayer = fs.getCurrentPlayer();
            Vector<Card> hand1 = fs.getHand(0);
            Vector<Card> hand2 = fs.getHand(1);
            Vector<Card> hand3 = fs.getHand(2);
            Vector<Card> hand4 = fs.getHand(3);
            Vector<Move> moves = fs.getLog();
            int[] scoreArr = fs.getScores();

            // convert to strings
            String deck_string = "";
            for (Card cards : deck) {
                int id = cards.getID();
                deck_string = deck_string + id + "-";
            }

            String chat_string = "";
            for (String chats : chat) {
                chat_string = chat_string + chats + ".#";
            }

            String hand1_string = "";
            for (Card cards : hand1) {
                int id = cards.getID();
                hand1_string = hand1_string + id + "-";
            }

            String hand2_string = "";
            for (Card cards : hand2) {
                int id = cards.getID();
                hand2_string = hand2_string + id + "-";
            }

            String hand3_string = "";
            if (numPlayers >= 3) {
                for (Card cards : hand3) {
                    int id = cards.getID();
                    hand3_string = hand3_string + id + "-";
                }
            } else {
                hand3_string = "empty";
            }

            String hand4_string = "";
            if (numPlayers == 4) {
                for (Card cards : hand4) {
                    int id = cards.getID();
                    hand4_string = hand4_string + id + "-";
                }
            } else {
                hand4_string = "empty";
            }

            String moves_string = "";
            for (Move log : moves) {
                int playerAsked = log.getPlayerAsked();
                int playerTurn = log.getPlayerTurn();
                int cardsReceived = log.getCardsReceived();
                int cardAsked = log.getCardAsked();

                int completed;
                if (log.isCompletedSet()) {
                    completed = 1;
                } else {
                    completed = 0;
                }

                int received;
                if (log.isReceivedCardFromDeck()) {
                    received = 1;
                } else {
                    received = 0;
                }

                moves_string = moves_string + playerAsked + "-" + playerTurn
                        + "-" + cardsReceived + "-" + cardAsked + "-"
                        + completed + "-" + received + "#";
            }

            String score_string = "";
            for (int i = 0; i < 4; i++) {
                score_string = score_string + scoreArr[i] + "-";
            }

            // return it all
            return deck_string + "!#" + chat_string + "!#" + currPlayer + "!#"
                    + hand1_string + "!#" + hand2_string + "!#" + hand3_string
                    + "!#" + hand4_string + "!#" + moves_string + "!#"
                    + score_string + "!#" + numPlayers;

        } catch (Exception e) {
            return "Error: Could not encode state";
        }
    }

    /**
     * Decodes an action from a string
     * 
     * @param s
     *            string we are decoding
     */
    protected GameAction decodeAction(String s) {

        // get first word
        String cmd = parseCommand(s);

        if (cmd.equals("ask")) {
            // get numbers sent in the string
            int[] args = parseArgs(s);
            // create and return
            Card card = FishGameImpl.initCard(args[2]);
            return new FishAskPlayerAction(args[0], args[1], card, this);
        } else if (cmd.equals("msg")) {
            int start = s.indexOf('#');
            // create and return
            String msg = s.substring(start + 1);
            return new FishMessageSentAction(this, msg);
        } else {
            return new GameNullAction(this);
        }
    }

    protected int getAdmPortNum() {
        return FishProxyGame.PORT;
    }

    public void setHand(Vector<Card> newHand) {
        hand = newHand;
    }

}