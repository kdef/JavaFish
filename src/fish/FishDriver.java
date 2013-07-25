package fish;

import game.*;

/**
 * Responsible for starting the game.
 * 
 * @author Nate Kozlowski, Joey Devlin, Kyle DeFrancia
 * @version 12/10/11
 */
class FishDriver implements GameDriver {
    protected String[] lpChoices = new String[] { "human player",
            "EASY computer player", "MED computer player",
            "HARD computer player" };

    /**
     * Constructor.
     */
    public FishDriver() {

    }

    /**
     * Starts the game
     */
    public static void main(String[] args) {
        DriverEngine.play(args, new FishDriver());
    }

    public String[] localPlayerChoices() {
        // TODO Auto-generated method stub
        return lpChoices;
    }

    /**
     * Makes a local player
     * 
     * @param name
     *            for deciding what kind of a player to make
     */
    public GamePlayer createLocalPlayer(String name) {
        if (name.equals(lpChoices[0])) { // user selected "Human Player"
            return new FishHumanPlayer();
        } else if (name.equals(lpChoices[1])) { // user selected
                                                // "EASY Computer Player"
            return new FishComputerPlayer(1);
        } else if (name.equals(lpChoices[2])) { // user selected
                                                // "MED Computer Player"
            return new FishComputerPlayer(2);
        } else if (name.equals(lpChoices[3])) { // user selected
                                                // "HARD Computer Player"
            return new FishComputerPlayer(3);
        } else {
            // bad selection: return null
            return null;
        }
    }

    /**
     * Makes a proxy player
     */
    public ProxyPlayer createRemotePlayer() {
        return new FishProxyPlayer();
    }

    /**
     * Makes a proxy game for networking
     * 
     * @param hostName
     *            info for the host
     */
    public ProxyGame createRemoteGame(String hostName) {
        return new FishProxyGame(hostName);
    }

    /**
     * Makes a new GoFish game
     * 
     * @param numPlayers
     *            how many players are going to play
     */
    public Game createGame(int numPlayers) {
        return new FishGameImpl(numPlayers);
    }

}