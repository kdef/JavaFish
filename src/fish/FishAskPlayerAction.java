package fish;

import game.*;

/**
 * An action created when someone asks another player for a card
 * 
 * @author Nate Kozlowski, Joey Devlin, Kyle DeFrancia
 * @version 12/10/11
 */
class FishAskPlayerAction extends FishMoveAction {
    private int asker;
    private int asked;
    private Card card;

    /**
     * Constructor for the ask player action
     * 
     * @param source
     */
    public FishAskPlayerAction(GamePlayer source) {
        super(source);
    }

    /**
     * Constructor for the ask player action
     * 
     * @param asker
     * @param asked
     * @param card
     * @param source
     */
    public FishAskPlayerAction(int asker, int asked, Card card,
            GamePlayer source) {
        super(source);
        this.setAsker(asker);
        this.setAsked(asked);
        this.setCardRank(card);
    }

    /**
     * Lets MoveAction know it's an ask player action
     * 
     * @param asker
     *            who is asking
     * @param asked
     *            who was asked
     * @param cardRank
     *            rank of the card being asked *
     */
    public boolean isAskPlayer() {
        return true;
    }

    public void setAsked(int asked) {
        this.asked = asked;
    }

    public int getAsked() {
        return asked;
    }

    public void setCardRank(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    public void setAsker(int asker) {
        this.asker = asker;
    }

    public int getAsker() {
        return asker;
    }

}