package fish;

import game.*;

/**
 * A class to store info about the message for the chat.
 * 
 * @author Joey Devlin, Nate Kozlowski, Kyle DeFrancia
 * @version 12/10/11
 */
class FishMessageSentAction extends FishMoveAction {
    private String message;

    /**
     * Constructs a new message sent
     * 
     * @param source
     * @param chatMsg
     */
    public FishMessageSentAction(GamePlayer source, String chatMsg) {
        super(source);
        message = chatMsg;
    }

    /**
     * Lets the moveaction know that it really is a message sent
     * 
     * @param message
     */
    public boolean isMessageSent(String message) {
        return true;
    }

    public String getMessage() {
        return message;
    }

}