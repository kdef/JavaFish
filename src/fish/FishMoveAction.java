package fish;

import game.*;

/**
 * Action handler containing a bunch of stuff regarding the move that just
 * happened.
 * 
 * @author Joey Devlin, Nate Kozlowski, Kyle DeFrancia
 * @version 12/10/11
 */
class FishMoveAction extends GameMoveAction {
    private int passedCardRank;
    private GamePlayer playerAsked;
    private GamePlayer playerTurn;
    private String chatMsg;

    /**
     * An ask move action constructor
     * 
     * @param playerAsked
     *            player who was asked
     * @param playerTurn
     *            player who asked
     * @param cardRank
     *            the rank of the card that was asked
     */
    public FishMoveAction(GamePlayer playerAsked, GamePlayer playerTurn,
            int cardRank) {
        super(playerTurn);
    }

    /**
     * Constructs a moveaction specifically for a chat message
     * 
     * @param playerAsked
     *            player being asked
     * @param chatMsg
     *            the message that is being passed through
     */
    public FishMoveAction(GamePlayer playerAsked, String chatMsg) {
        super(playerAsked);
        this.chatMsg = chatMsg;
    }

    /**
     * Constructor with just one parameter
     * 
     * @param source
     */
    public FishMoveAction(GamePlayer source) {
        super(source);
    }

    /**
     * Checks to see if it's an ask player action
     * 
     * @param asker
     *            who asked
     * @param asked
     *            who is being asked
     * @param cardRank
     *            rank of card being asked
     */
    public boolean isAskPlayer() {
        return false;
    }

    /**
     * Checks to see if the action is a draw card
     * 
     */
    public boolean isTakeCard() {
        return false;
    }

    /**
     * Checks to see if the action is a chat string message
     * 
     * @param message
     *            the message being passed through
     */
    public boolean isMessageSent() {
        return false;
    }

}