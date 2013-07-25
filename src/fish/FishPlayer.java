package fish;

import java.util.*;

import game.*;

/**
 * Interface for FishPlayers
 * 
 * @author Joey Devlin, Nate Kozlowski, Kyle DeFrancia
 * @version 12/10/11
 */
interface FishPlayer extends GamePlayer {

    public void setHand(Vector<Card> newHand);

}