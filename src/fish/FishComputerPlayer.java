package fish;

import java.util.*;

import game.*;

/**
 * Computer Player
 * 
 * @author Nate
 * 
 * @version 12/08/2011
 */
class FishComputerPlayer extends GameComputerPlayer implements FishPlayer {
	
    private int difficulty; //1 = easy; 2= medium; 3 = hard
    private Vector<Card> hand;
    private int score;
    private FishState cState;
    private Vector<Move> moveLog;
    private boolean debug = true;


    /**
     * Constructor
     * @param difficulty, how difficult the computer player will be
     */
	
	public FishComputerPlayer(int difficulty){
		this.difficulty = difficulty;
	}

	/** 
	 * The move the computer wants to do 
	 */
	public void doRequestMove () {
		try{
			Thread.sleep(3000);
		}
		catch(InterruptedException ie){
			//some error
		}
		
		if (debug) debugHand();
		
		if (cState.getCurrentPlayer() == this.getId()){
			if (difficulty == 1){
				doEasyMove();
			}
			if (difficulty == 2){
				doMediumMove();
			}
			if (difficulty == 3){
				doHardMove();
			}
		}
	}

	/** 
	 * Updates the state for the computer player 
	 */
	public void stateChanged () {
		cState = (FishState) game.getState(this, 0);
		moveLog = cState.getLog();
		hand = cState.getHand(this.getId());
		debug = true;
	}
	
	/**
	 * Setting the hand for the player
	 * @param newHand, the new hand for the player
	 */
	public void setHand(Vector<Card> newHand){
		hand = newHand;
	}
	
	/**
	 * AI for the easy computer
	 * does a random move
	 */
	private void doEasyMove () {
		Random generator = new Random();
		//choose a random card in their hand
		int handSize = hand.size();
		int locInHand = generator.nextInt(handSize);
		Card cardChosen = hand.elementAt(locInHand);
		
		//choosing which player to ask
		int playerChosen = this.getId();
		while (playerChosen == this.getId()){
			playerChosen = generator.nextInt(cState.numPlayers);
		}
		
		//doing the ask method
		game.applyAction(new FishAskPlayerAction(this.getId(), playerChosen, cardChosen, this));
		doRequestMove();
	}
	
	/**
	 * Thinking process for the medium AI
	 */
	
	private void doMediumMove() {
		Random generator = new Random();
		
		if (moveLog.isEmpty()) {
			doEasyMove();
			doRequestMove();
		}
		
		int shouldQuit = 0;
		
		//just some randomness
		if (generator.nextInt(100) <= 25) {
			doEasyMove();
			doRequestMove();
		}
		
		for (int i = (moveLog.size() - 1); i >= 0; i--) {
			Move currentMove = moveLog.get(i);
			
			//skip if the person who asked was this guy
			if (currentMove.getPlayerTurn() != this.getId()) {
				
				//grabbing the rank of card they asked for that turn
				int cardAsked = currentMove.getCardAsked();
				
				//checking every card in the comp's hand and checking to see if
				//they have it as well
				for (int j = 0; j < (hand.size() - 1); j++) {
					
					if (cardAsked == hand.elementAt(j).getRank()) {
						game.applyAction(new FishAskPlayerAction(this.getId(), currentMove.getPlayerTurn(), hand.get(j), this));
						doRequestMove();
					}
				}
			}
			
			shouldQuit++;
			if (shouldQuit >= 5) break;
		}
		
		//if we can't find anything that is the same in our hand then
		//ask a random move
		doEasyMove();
	}
	
	/**
	 * Thinking process for hard AI
	 */
	private void doHardMove() {
		Random generator = new Random();
		
		if (moveLog.isEmpty()) {
			doEasyMove();
			doRequestMove();
		}
		
		int shouldQuit = 0;
		
		//just some randomness
		if (generator.nextInt(100) <= 25) {
			doEasyMove();
			doRequestMove();
		}
		
		Vector<Move> validMoves = new Vector<Move>();
		
		for (int i = (moveLog.size() - 1); i >= 0; i--) {
			Move currentMove = moveLog.get(i);
			
			//skip if the person who asked was this guy
			if (currentMove.getPlayerTurn() != this.getId()) {
				
				//grabbing the rank of card they asked for that turn
				int cardAsked = currentMove.getCardAsked();
				
				//checking every card in the comp's hand and checking to see if
				//they have it as well
				for (int j = 0; j < (hand.size() - 1); j++) {
					
					if (cardAsked == hand.elementAt(j).getRank()) {
						validMoves.add(currentMove);
					}
				}
			}
			
			shouldQuit++;
			if (shouldQuit >= 7) break;
		}
		
		//doing a random valid move instead of the first one
		if (!validMoves.isEmpty()) {
			
			//get size of vector and generate a random num of that size
			int valid = validMoves.size();
			int toMove = generator.nextInt(valid);
			
			//now we can make a move based on that number generated
			Move moveToMake = validMoves.get(toMove);
			Card cardToAsk = null;
			
			//gotta find the card in the hand
			for (int i = 0; i < (hand.size() - 1); i++) {
				
				if (moveToMake.getCardAsked() == hand.elementAt(i).getRank()) {
					cardToAsk = hand.elementAt(i);
				}
			}
			
			game.applyAction(new FishAskPlayerAction(this.getId(), moveToMake.getPlayerTurn(), cardToAsk, this));
			doRequestMove();
		}
		
		//if we can't find anything that is the same in our hand then
		//ask a random move
		doEasyMove();
	}
	
	/**
	 * Prints out AI's hand to the console.
	 * 
	 * For debug purposes
	 */
	private void debugHand() {
		String cards = "";
		for (int i = 0; i < hand.size(); i++) {
			cards += hand.elementAt(i).getName() + " ";
		}
		debug = false;
	}

}