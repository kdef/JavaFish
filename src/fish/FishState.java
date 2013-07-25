package fish;

import game.*;
import java.util.*;

/**
 * This is the class for the state of the game. Is passed around and constantly
 * updated.
 * 
 * @author Joey Devlin, Nate Kozlowski, Kyle DeFrancia
 * @version 12/10/11
 */
class FishState extends GameState {

    private Vector<Card> deck;
    private int currentPlayer;
    private Vector<String> chatHistory;
    private Vector<Move> moveLog;
    private int[] score = new int[4];
    private boolean hasWon;

    // each player's hand
    private Vector<Card> p1 = new Vector<Card>();
    private Vector<Card> p2 = new Vector<Card>();
    private Vector<Card> p3 = new Vector<Card>();
    private Vector<Card> p4 = new Vector<Card>();

    protected int numPlayers;

    /**
     * Constructor for creating a new state.
     * 
     * @param Deck
     *            the deck in the middle.
     * @param Players
     *            Vector containing all the players
     * @param ChatHistory
     *            Vector containing all the chats
     * @param curPlayer
     *            integer to keep track of whose turn it is.
     */
    public FishState(Vector<Card> newDeck, Vector<String> newChatHistory,
            int newCurPlayer, Vector<Card> p1, Vector<Card> p2,
            Vector<Card> p3, Vector<Card> p4, Vector<Move> moves, int[] score) {
        deck = newDeck;
        chatHistory = newChatHistory;
        currentPlayer = newCurPlayer;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.score = score;
        moveLog = moves;

        if (p4 == null) {
            if (p3 == null) {
                setNumPlayers(2);
            } else {
                setNumPlayers(3);
            }
        } else {
            setNumPlayers(4);
        }

    }

    /**
     * Returns whose turn it is.
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Gets the stuff for the chat.
     */
    public Vector<String> getChat() {
        return chatHistory;
    }

    /**
     * Gets the stuff for the movelog
     */
    public Vector<Move> getLog() {
        return moveLog;
    }

    /**
     * Gets the deck of cards left in
     */
    public Vector<Card> getDeck() {
        return deck;
    }

    /**
     * This method returns the size of the requested player
     * 
     * @param player
     *            , the player whom we want to get the size of their hand
     * @return toReturn, the size of their hand
     */
    public int getHandVal(int player) {
        int toReturn = 0;
        if (player == 0) {
            toReturn = p1.size();
        } else if (player == 1) {
            toReturn = p2.size();
        } else if ((player == 2) && (getNumPlayers() > 2)) {
            toReturn = p3.size();
        } else if ((player == 3) && (getNumPlayers() > 3)) {
            toReturn = p4.size();
        }

        return toReturn;
    }

    /**
     * If a player needs the cards of their hand they can request it here
     * 
     * @param player
     *            , the player who is requesting their hand
     * @return toReturn, that player's hand
     */
    public Vector<Card> getHand(int player) {
        Vector<Card> toReturn = new Vector<Card>();
        if (player == 0) {
            toReturn = p1;
        }
        if (player == 1) {
            toReturn = p2;
        }
        if (player == 2) {
            toReturn = p3;
        }
        if (player == 3) {
            toReturn = p4;
        }

        return toReturn;
    }

    /**
     * score of specific player
     * 
     * @param ID
     *            , the ID of the player's score we are requesting
     * @return score, the score of the requested player
     */
    public int getScore(int ID) {
        return score[ID];
    }

    /**
     * this one will send off the entire array of the scores
     * 
     * @return score, the score array
     */
    public int[] getScores() {
        return score;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public int getNumPlayers() {
        return numPlayers;
    }
    /**
     * Goes through the active players and finds the
     * player with the most points
     * @return the id of the winning player
     */
    public int getWinner() {
        int highScore = -1;
        int id = -1;
        for (int i = 0; i < numPlayers-1; i++) {
            if (score[i] > highScore) {
                highScore = score[i];
                id = i;
            }
        }
        return id;

    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    public boolean isHasWon() {
        return hasWon;
    }

}