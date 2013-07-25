package fish;

/**
 * Class for info about the moves to be used for the AI and the info box.
 * 
 * @author Joey Devlin, Nate Kozlowski, Kyle DeFrancia
 * @version 12/10/11
 */
class Move {
    private int playerAsked;
    private int playerTurn;
    private int cardsReceived;
    private int cardAsked;
    private String cardName;
    private boolean completedSet;
    private boolean receivedCardFromDeck;

    /**
     * Constructor
     * 
     * @param PlayerAsked
     *            who was asked
     * @param PlayerTurn
     *            who asked
     * @param cardsReceived
     *            amount of cards recieved
     * @param CardAsked
     *            use card ID
     * @param completedSet
     *            did they complete a set?
     * @param receivedCardFromDeck
     *            did they receive their card from the deck?
     */
    public Move(int PlayerAsked, int PlayerTurn, int CardsReceived,
            int CardAsked, boolean CompletedSet, boolean ReceivedCardFromDeck) {
        setPlayerAsked(PlayerAsked);
        setPlayerTurn(PlayerTurn);
        setCardsReceived(CardsReceived);
        setCardAsked(CardAsked);
        setCompletedSet(CompletedSet);
        setReceivedCardFromDeck(ReceivedCardFromDeck);
    }

    public Move() {

    }

    public void setPlayerAsked(int playerAsked) {
        this.playerAsked = playerAsked;
    }

    public int getPlayerAsked() {
        return playerAsked;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setCardsReceived(int cardsReceived) {
        this.cardsReceived = cardsReceived;
    }

    public int getCardsReceived() {
        return cardsReceived;
    }

    public void setCardAsked(int cardAsked) {
        this.cardAsked = cardAsked;
    }

    public int getCardAsked() {
        return cardAsked;
    }

    public void setCompletedSet(boolean completedSet) {
        this.completedSet = completedSet;
    }

    public boolean isCompletedSet() {
        return completedSet;
    }

    public void setReceivedCardFromDeck(boolean receivedCardFromDeck) {
        this.receivedCardFromDeck = receivedCardFromDeck;
    }

    public boolean isReceivedCardFromDeck() {
        return receivedCardFromDeck;
    }

    public String getCardAskedName() {
        return cardName;
    }

    public void setCardAskedName(String cardName) {
        this.cardName = cardName;
    }

}