package fish;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import game.*;

/**
 * Controls and updates all relevant game information.
 * 
 * @author Nate Kozlowski, Joey Devlin, Kyle DeFrancia
 * @version 12/10/11
 */
class FishGameImpl extends GameImpl implements FishGame {
    protected int winner;
    protected boolean hasWon;

    private int numPlayers;
    private int currentPlayer;

    // hands for all the players
    private Vector<Card> p1 = new Vector<Card>();
    private Vector<Card> p2 = new Vector<Card>();
    private Vector<Card> p3 = new Vector<Card>();
    private Vector<Card> p4 = new Vector<Card>();

    // deck of cards
    private Vector<Card> deck = new Vector<Card>();

    // chat history
    private Vector<String> chatHistory = new Vector<String>();

    // move log
    private Vector<Move> moveLog = new Vector<Move>();

    // scores
    private int[] score = new int[4];

    /**
     * Constructor to initialize the game
     * 
     * @param numPlayers
     *            amount of players who are playing
     */
    public FishGameImpl(int numPlayers) {
        super();
        this.numPlayers = numPlayers;
    }

    /**
     * Handles moves
     * 
     * @param thePlayer
     *            player who's moving
     * @param move
     *            contains all the info about the move
     * 
     * @return if the move was successful or not
     */
    public boolean makeMove(GamePlayer thePlayer, GameMoveAction move) {
        checkWinner();
        FishMoveAction fma = (FishMoveAction) move;
        thePlayer = (FishPlayer) thePlayer;

        // check every card in each player's hand for 4 of a kinds
        checkEveryHand();

        if (fma instanceof FishMessageSentAction) {
            FishMessageSentAction fms = (FishMessageSentAction) fma;
            chatHistory.add(fms.getMessage());

            notifyAllStateChanged();
            return true;
        }
        if (!canMove(thePlayer)) {
            return false;
        }
        if (fma.isAskPlayer()) {
            FishAskPlayerAction fap = (FishAskPlayerAction) fma;

            // figure out the two hands we care about
            Vector<Card> askerHand = grabHand(fap.getAsker());
            Vector<Card> askedHand = grabHand(fap.getAsked());

            // grabbing the card we asked for
            Card c = fap.getCard();

            // grabbing the amount of cards the asked person has of this card
            int numCard = checkHaveCard(c.getRank(), askedHand);

            // Creating the new move object for the movelog
            Move m = new Move();
            m.setPlayerAsked(fap.getAsked());
            m.setPlayerTurn(fap.getAsker());
            m.setCardAsked(c.getRank());
            m.setCardsReceived(numCard);
            m.setCardAskedName(c.getName());

            // if the player asked has one or more cards then follow through
            // checks the value of the card and then removes it if they're
            // what we're looking for.
            if (numCard != 0) {
                for (int i = (askedHand.size() - 1); i >= 0; i--) {
                    Card card = askedHand.elementAt(i);
                    if (card.getRank() == c.getRank()) {
                        askerHand.add(card);
                        askedHand.remove(card);
                    }
                }
            } else {
                checkWinner();
                Card newCard = deck.lastElement();
                checkWinner();

                deck.remove(deck.lastElement());
                askerHand.add(newCard);

                // checks if the player got the card asked for
                if (c.getRank() == newCard.getRank()) {
                    m.setReceivedCardFromDeck(true);
                } else {
                    m.setReceivedCardFromDeck(false);
                    currentPlayer = fap.getAsked();
                }
            }
            // look if the player completed the set
            int newAmt = checkHaveCard(c.getRank(), askerHand);
            if (newAmt == 4) {
                m.setCompletedSet(true);
                int toRemove = c.getRank();
                for (int i = (askerHand.size() - 1); i >= 0; i--) {
                    if (askerHand.elementAt(i).getRank() == toRemove) {
                        askerHand.remove(i);
                    }
                }
                // updating this player's score
                int toIncrement = fap.getAsker();
                score[toIncrement]++;
            } else {
                m.setCompletedSet(false);
            }

            // update the movelog
            moveLog.add(m);

            // sort players hands
            p1 = sortHand(p1);
            p2 = sortHand(p2);
            if (numPlayers == 4) {
                p4 = sortHand(p4);
            }
            if (numPlayers >= 3) {
                p3 = sortHand(p3);
            }
            notifyAllStateChanged();
            return true;
        }
        return false;
    }

    /**
     * Checks for winner.
     */
    private void checkWinner() {
        // TODO Auto-generated method stub
    }

    /**
     * Returns a hand based on the ID
     * 
     * @param id
     *            , the player's hand we are asking for
     * @return the player's hand
     */
    public Vector<Card> grabHand(int id) {
        if (id == 0) {
            return p1;
        }
        if (id == 1) {
            return p2;
        }
        if (id == 2) {
            return p3;
        }
        if (id == 3) {
            return p4;
        }
        return null;
    }

    /**
     * This method checks to see if a hand has a certain card we are looking for
     * 
     * @param rank
     *            , rank of card we are looking for
     * @param hand
     *            , the hand we are going through
     * @return counter, the amount of cards this hand has
     */
    public int checkHaveCard(int rank, Vector<Card> hand) {
        int counter = 0;
        for (Card c : hand) {
            if (c.getRank() == rank) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Sets minimum players needed to play the game
     */
    public int minPlayersAllowed() {
        return 2;

    }

    /**
     * Max players allowed to play the game
     */
    public int maxPlayersAllowed() {
        return 4;
    }

    /**
     * If a null player is allowed
     */
    public boolean nullPlayersAllowed() {
        return false;
    }

    /**
     * Gets the "official" game state
     * 
     * @param p
     *            player who wants to update the state
     * @param stateType
     *            depends what player you are.
     */
    public GameState getGameState(GamePlayer p, int stateType) {
        FishState fs = new FishState(deck, chatHistory, currentPlayer, p1, p2,
                p3, p4, moveLog, score);
        fs.setHasWon(hasWon);
        return fs;
    }

    /**
     * Initializes the game
     * 
     * It creates a deck of size 52 It hands out 7 cards to each hand It then
     * chooses a random player to go first
     */
    public void initializeGame() {
        // creating the deck
        deck = initDeck();

        // resetting score
        for (int i = 0; i < 4; i++) {
            score[i] = 0;
        }

        // handing out the initial cards to all the players
        // then we hand the players their cards
        p1 = sortHand(dealInit());
        ((FishPlayer) player[0]).setHand(p1);

        p2 = sortHand(dealInit());
        ((FishPlayer) player[1]).setHand(p2);

        if (numPlayers == 4) {
            p4 = sortHand(dealInit());
            ((FishPlayer) player[3]).setHand(p4);
        } else {
            p4 = null;
        }

        if (numPlayers >= 3) {
            p3 = sortHand(dealInit());
            ((FishPlayer) player[2]).setHand(p3);
        } else {
            p3 = null;
        }

        // choosing a random person to go first
        Random generator = new Random();
        currentPlayer = generator.nextInt(numPlayers);

        // notifying everyone the state has changed
        notifyAllStateChanged();
    }

    /**
     * Makes sure the person who is suppossed to be doing an action is the one
     * who is doing it.
     * 
     * @param gp
     */
    public boolean canMove(GamePlayer gp) {
        // allows people to send messages at any time
        if ((gp instanceof FishProxyPlayer) || gp instanceof FishHumanPlayer) {
            return true;
        } else {
            return gp == player[currentPlayer];
        }
    }

    public boolean gameOver() {
        if (deck.size() == 0) {
            hasWon = true;
        }

        if (p1.size() == 0) {
            hasWon = true;
        }

        if (p2.size() == 0) {
            hasWon = true;
        }

        if (numPlayers >= 3) {
            if (p3.size() == 0) {
                hasWon = true;
            }
        }

        if (numPlayers == 4) {
            if (p4.size() == 0) {
                hasWon = true;
            }
        }

        if (hasWon)
            notifyAllStateChanged();

        return hasWon;
    }

    protected boolean canQuit(GamePlayer gp) {
        return true;
    }

    /**
     * We will be using this method to init the deck Makes a randomized deck of
     * size 52
     * 
     * @return the finalized deck
     */
    public Vector<Card> initDeck() {
        // The deck of cards we will end up returning
        Vector<Card> Deck = new Vector<Card>();

        // placeholder for the cardID's to check for repeats
        int[] cardID = new int[52];

        for (int i = 1; i < 53; i++) {
            boolean notUsed = false;
            int num = 0;
            // Generates a random number between 1-52 and then checks
            // to see if the number had already been generated or not
            // isUsed() does the actual repeat checking
            while (!notUsed) {
                Random generator = new Random();
                num = generator.nextInt(52) + 1;
                boolean contains = isUsed(num, cardID);
                if (!contains) {
                    notUsed = true;
                }
            }
            // Store the ID in the array to prevent repeats
            cardID[i - 1] = num;
            // Now we generate the card and add it to the deck
            Card newCard = initCard(num);
            Deck.add(newCard);
        }
        return Deck;
    }

    /**
     * This method takes an int and then uses a specified text file to set the
     * parameters for the card
     * 
     * put the skin info in the skins folder (must be a .txt file!) put the
     * images into the images folder in a name exactly the same name as the text
     * file. name them in order from 01-52. 00 is reserved for the back. 53 is
     * if the back is on it's side. images must be .gif
     * 
     * Example: skins/default.txt, images/default/01.gif
     * 
     * @param i
     *            , the current card we are creating
     * @return toReturn, the card we just made!
     */
    public static Card initCard(int i) {
        Card toReturn = null;
        BufferedReader br = null;
        // loading the textfile to parse the info from it
        try {
            br = new BufferedReader(new FileReader("skins/default.txt"));
            String curr;
            // add a 0 to the string if it's below 10
            if (i < 10) {
                curr = "0" + i;
            } else {
                curr = "" + i;
            }

            boolean found = false;
            String Line = null;

            // go through the text file to find the line we want to parse
            while (!found) {
                Line = br.readLine();
                if (Line == null) {
                    break;
                }
                int cut = Line.indexOf('#');
                String check = Line.substring(0, cut);
                if (check.equals(curr)) {
                    found = true;
                }
            }
            if (found == false) {
                System.out.println("Did not find the matching string :/");
                return null;
            }

            int ID = i;
            // rank is =<13, so get it down into that range
            int rank = i;
            while (rank > 13) {
                rank -= 13;
            }

            // this is creating the strings to find the images
            String name = Line.substring(Line.lastIndexOf('#') + 1);
            String front = "images/default/" + curr + ".gif";
            String back = "images/default/00.gif";
            String side = "images/default/53.gif";

            // now we finally create the card
            toReturn = new Card(rank, ID, name, front, back, side);
        } catch (IOException ioe) {
            System.out.println("IDK what went wrong but you're fucked!");

        } finally {
            try {
                br.close();
            } catch (IOException ioe) {
                // nothing should go here
            }
        }
        // return the card we made
        return toReturn;
    }

    /**
     * Just checks to see if the card has been used before to prevent repeats in
     * the deck
     * 
     * @param num
     *            , the num we are looking to see if it's been used already
     * @param cardID
     *            , the array of already used ID's
     * @return contains, will be set to true if a match is found
     */
    private boolean isUsed(int num, int[] cardID) {
        boolean contains = false;
        for (int i = 0; i < 52; i++) {
            if (cardID[i] == num) {
                contains = true;
            }
        }
        return contains;
    }

    /**
     * This creates a 7 card hand for the player to start out with.
     * 
     * @return hand, the initialized hand
     */
    private Vector<Card> dealInit() {
        Vector<Card> hand = new Vector<Card>();
        for (int i = 0; i < 7; i++) {
            // add top card to the hand and then remove it from the deck
            hand.add(deck.get(0));
            deck.remove(0);
        }
        return hand;
    }

    /**
     * Method to sort hand by rank
     * 
     */
    private Vector<Card> sortHand(Vector<Card> toSort) {
        Vector<Card> temp = new Vector<Card>();
        for (Card c : toSort) {
            boolean added = false;
            for (int i = (temp.size() - 1); i >= 0; i--) {

                // if it's larger than the last card then we stick it behind
                // the card we are comparing to
                // if they are equal we compare ID's and use that to see
                // what goes where

                if (c.getRank() > temp.elementAt(i).getRank()) {
                    added = true;
                    if (i == temp.size()) {
                        temp.add(c);
                        break;
                    } else {
                        temp.add(i + 1, c);
                        break;
                    }
                } else if (c.getRank() == temp.elementAt(i).getRank()) {
                    // here we sort by ID if the rank is the same
                    if (c.getID() > temp.elementAt(i).getID()) {
                        temp.add(i + 1, c);
                        added = true;
                        break;
                    }
                }
            }
            if (!added) {
                temp.add(0, c);
            }
        }
        return temp;
    }

    private void checkEveryHand() {
        // per player
        for (int i = 0; i < numPlayers; i++) {
            Vector<Card> handToCheck = new Vector<Card>();
            if (i == 0)
                handToCheck = p1;
            if (i == 1)
                handToCheck = p2;
            if (i == 2)
                handToCheck = p3;
            if (i == 3)
                handToCheck = p4;

            // per card in this hand
            for (int j = 0; j < handToCheck.size(); j++) {
                Card c = handToCheck.get(j);
                int newAmt = checkHaveCard(c.getRank(), handToCheck);
                if (newAmt == 4) {
                    int toRemove = c.getRank();
                    for (int k = (handToCheck.size() - 1); k >= 0; k--) {
                        if (handToCheck.elementAt(k).getRank() == toRemove) {
                            handToCheck.remove(k);
                        }
                    }
                    // updating this player's score
                    score[i]++;
                    // resetting the index in the hand
                    j--;
                }
            }
        }
    }

}