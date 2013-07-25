package fish;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import game.*;

/**
 * This is a human local player.  Defines GUI.
 * 
 * The player's cards are always displayed on the bottom.
 * @author Joe Devlin
 * @version 12/9/2011
 * still need to grey out buttons when it is not this players turn,
 * make actions
 */
class FishHumanPlayer extends GameHumanPlayer implements FishPlayer {
	private static final long serialVersionUID = 3704377010176717652L;
	private Vector<Card> cHand = new Vector<Card>();
	private Vector<JButton> bHand;
	private int topID;
	private int rightID;
	private int leftID;
	private int score = 0;
	private int topCards;
	private int leftCards;
	private int rightCards;

	JPanel top;

	JButton topPlayer;
	JButton rightPlayer;
	JButton leftPlayer;

	JTextArea convo;
	JTextField chat;
	JTextArea moveLog;

	JLabel turn;
	JLabel sTop;
	JLabel sBottom;
	JLabel sRight;
	JLabel sLeft;

	Box bottomBox;
	Box topBox;
	Box rightBox;
	Box leftBox;

	private FishState cState;
	private boolean isTurn;
	private boolean setPOV;
	private Card cardPicked;
	private static final int WINDOW_SIZE = 800;
	
	JPanel topHand = new JPanel();
	JPanel bottomHand = new JPanel();
	JPanel rightHand = new JPanel();
	JPanel leftHand = new JPanel();
	JPanel center = new JPanel();
	/**
	 * Constructor for the Human Players
	 */
	public FishHumanPlayer(){
		super();
		//used to see if the player's are used
		topID = -1;
		leftID = -1;
		rightID = -1;
		
		isTurn = false;
		setPOV = true;
		bHand = new Vector<JButton>();
	}
	/**
	 * Initializes all the containers, buttons, and labels
	 */
	protected void setGameMore(){	
		sTop = new JLabel();
		sBottom = new JLabel();
		sRight = new JLabel();
		sLeft = new JLabel();
		
		convo = new JTextArea(8,12);
		chat = new JTextField(5);
		chat.addActionListener(this);
		chat.setActionCommand("chat");
		moveLog = new JTextArea(8,18);
		turn = new JLabel("IT IS YOUR TURN");
		
		topPlayer = new JButton();
		topPlayer.addActionListener(this);
		rightPlayer = new JButton();
		rightPlayer.addActionListener(this);
		leftPlayer = new JButton();
		leftPlayer.addActionListener(this);
		
		convo.setEditable(false);
		convo.setLineWrap(true);
		convo.setWrapStyleWord(true);
		moveLog.setEditable(false);
		moveLog.setLineWrap(true);
		moveLog.setWrapStyleWord(true);
		moveLog.setMinimumSize(new Dimension(275,150));
		moveLog.setMaximumSize(new Dimension(275,150));
		convo.setMinimumSize(new Dimension(425,150));
		convo.setMaximumSize(new Dimension(2400,150));
		
		setSize(WINDOW_SIZE+60,WINDOW_SIZE);
		setMinimumSize(new Dimension(WINDOW_SIZE,WINDOW_SIZE));
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		ImageIcon ii = new ImageIcon("images/default/53.gif");
		JButton centerC = new JButton(ii);
		Dimension dim = new Dimension(ii.getIconWidth(),ii.getIconHeight());
		centerC.setMinimumSize(dim);
		centerC.setMaximumSize(dim);
		
		//box for the card in the middle
		Box cardBox = Box.createHorizontalBox();
		cardBox.add(Box.createVerticalGlue());
		cardBox.add(centerC);
		cardBox.add(Box.createVerticalGlue());
		center.add(cardBox);

		//box for the player on the top of the screen
		topBox = Box.createHorizontalBox();
		Box topBoxP = Box.createHorizontalBox();
		topBoxP.add(topPlayer);
		topHand.add(topBox);
		topHand.add(Box.createRigidArea(new Dimension(10,10)));
		topHand.add(topBoxP);
		Box topBoxS = Box.createHorizontalBox();
		topBoxS.add(sTop);
		topHand.add(topBoxS);

		//box for the move log
		Box moveBox = Box.createVerticalBox();
		moveBox.add(new JLabel("Recent Moves"));
		moveBox.add(moveLog);

		//box for the chat and text field
		Box chatBox = Box.createVerticalBox();
		chatBox.add(new JLabel("Chat Box"));
		chatBox.add(convo);
		chatBox.add(chat);

		//the box that holds the chat and move log
		Box chatMove = Box.createHorizontalBox();
		chatMove.add(chatBox);
		chatMove.add(Box.createHorizontalStrut(5));
		chatMove.add(moveBox);


		//box that holds the bottom player's information
		bottomBox = Box.createHorizontalBox();
		Box scoreTurn = Box.createHorizontalBox();
		scoreTurn.add(Box.createGlue());
		scoreTurn.add(sBottom);
		scoreTurn.add(Box.createGlue());
		scoreTurn.add(turn);
		scoreTurn.add(Box.createHorizontalStrut(100));
		bottomHand.add(scoreTurn);
		bottomHand.add(bottomBox);
		bottomHand.add(Box.createVerticalStrut(20));
		bottomHand.add(chatMove);

		//box for the right player's info
		rightBox = Box.createVerticalBox();
		Box rightBoxP = Box.createVerticalBox();
		rightBoxP.add(rightPlayer);
		rightBoxP.add(sRight);
		rightHand.add(rightBoxP);
		rightHand.add(Box.createRigidArea(new Dimension(5,5)));
		rightHand.add(rightBox);

		//box for the left player's info
		leftBox = Box.createVerticalBox();
		Box leftBoxP = Box.createVerticalBox();
		leftBoxP.add(leftPlayer);
		leftBoxP.add(sLeft);
		leftHand.add(leftBox);
		leftHand.add(Box.createHorizontalStrut(5));
		leftHand.add(leftBoxP);

		//put each panel in its appropriate spot
		top.add(topHand,BorderLayout.NORTH);
		top.add(bottomHand,BorderLayout.SOUTH);
		top.add(rightHand,BorderLayout.EAST);
		top.add(leftHand,BorderLayout.WEST);
		top.add(center,BorderLayout.CENTER);

		//get the entire background to be a dark green
		topHand.setLayout(new BoxLayout(topHand,BoxLayout.Y_AXIS));
		topHand.setBackground(Color.GREEN.darker());
		bottomHand.setLayout(new BoxLayout(bottomHand,BoxLayout.Y_AXIS));
		bottomHand.setBackground(Color.GREEN.darker());
		rightHand.setLayout(new BoxLayout(rightHand,BoxLayout.X_AXIS));
		rightHand.setBackground(Color.GREEN.darker());
		leftHand.setLayout(new BoxLayout(leftHand,BoxLayout.X_AXIS));
		leftHand.setBackground(Color.GREEN.darker());
		center.setLayout(new BoxLayout(center,BoxLayout.Y_AXIS));
		center.setBackground(Color.GREEN.darker());

	}
	/**
	 * returns the component that holds this game's
	 * specific GUI
	 */
	protected Component createApplComponent(){
		top = new JPanel(new BorderLayout());
		return top;

	}
	/**
	 * There are two types of actions that a player can make:
	 * ask a player and send a message.  Each event is triggered
	 * by an ActionEvent
	 */
	protected void moreActionPerformed(ActionEvent ae){
		//checks to see if is a message action
		if(ae.getActionCommand().equals("chat") ){
			String message =  chat.getText();
			message.trim();
			//check if the message is too large or small
			if(message.length() > 0 && message.length() < 51){
				message = "Player " + (getId()+1) +" : " + message;
				game.applyAction(new FishMessageSentAction(this, message));
				chat.setText("");
			}
		}
		else{
			JButton jb = (JButton) ae.getSource();
			int i = 0;
			boolean found = false;
			//decides what card is picked
			for(JButton b : bHand){
				if(jb.equals(b)){
					found = true;
					topPlayer.setEnabled(isTurn);
					rightPlayer.setEnabled(isTurn);
					leftPlayer.setEnabled(isTurn);
					cardPicked = cHand.elementAt(i);
					turn.setText("You chose: " + cHand.elementAt(i).getName());
				}
				if (!found){
					i++;
				}
			}
			//decides who the player is asking;
			if(jb == topPlayer){
				game.applyAction(new FishAskPlayerAction(this.getId(), topID, cardPicked, this));
			}
			else if(jb == rightPlayer){
				game.applyAction(new FishAskPlayerAction(this.getId(), rightID, cardPicked, this));
			}
			else if(jb == leftPlayer){
				game.applyAction(new FishAskPlayerAction(this.getId(), leftID, cardPicked, this));
			}
		}
	}
	/**
	 * Is called only once.  Displays the table based on which player you are
	 * @param: fs: the current state of the game.
	 */
	//used so each user's hand is on bottom and
	//creates a uniform view of the game
	public void initPOV(FishState fs){
		setPOV = false;
		if(this.getId() == 0){
			this.topID = 1;
			if(!(fs.getHandVal(2) == 0)){
				this.rightID = 2;
			}
			if(!(fs.getHandVal(3) == 0)){
				this.leftID = 3;
			}
		}
		else if(this.getId() == 1){
			this.topID = 0;
			if(!(fs.getHandVal(2) == 0)){
				this.rightID = 2;
			}
			if(!(fs.getHandVal(3) == 0)){
				this.leftID = 3;
			}
		}		
		else if(this.getId() == 2){
			this.topID = 0;
			if(!(fs.getHandVal(1) == 0)){
				this.rightID = 1;
			}
			if(!(fs.getHandVal(3) == 0)){
				this.leftID = 3;
			}
		}
		else if(this.getId() == 3){
			this.topID = 0;
			if(!(fs.getHandVal(1) == 0)){
				this.rightID = 1;
			}
			if(!(fs.getHandVal(2) == 0)){
				this.leftID = 2;
			}
		}
		//sets the button to represent the player based on id
		topPlayer.setText("Player: "+(topID+1));
		if(fs.getHandVal(2) == 0){
			rightPlayer.setVisible(false);
		}
		else{
			rightPlayer.setText("Player: "+(rightID+1));
		}
		if(fs.getHandVal(3) == 0){
			leftPlayer.setVisible(false);
		}
		else{
			leftPlayer.setText("Player: "+ (leftID+1));
		}
	}
	/**
	 * Is called when the state is changed.  Places all the
	 * the buttons in the appropriate box and updates the
	 * text of the move log and chat box
	 */
	public void stateChanged() {
		//grabs the current state
		cState = (FishState) game.getState(this, 0);
		//places the panels on the correct border
		//is only called once
		if(setPOV){
			initPOV(cState);
		}
		int[] cScores = cState.getScores();
		if(cState.isHasWon()){
			JOptionPane.showMessageDialog(top, "Player " + (cState.getWinner()+1)+ " won");
		}
		score = cScores[this.getId()];
		
		//update the two logs
		moveLog.setText(setMoveText(cState.getLog()));
		convo.setText(setChatText(cState.getChat()));
		
		//makes sure the player has to pick a card before asking
		topPlayer.setEnabled(false);
		rightPlayer.setEnabled(false);
		leftPlayer.setEnabled(false);

		//changes text based on if it is the player's turn
		if(cState.getCurrentPlayer() == getId()){
			isTurn = true;
		}
		else{
			isTurn = false;
		}
		if(isTurn){
			turn.setForeground(Color.RED);
			turn.setText("It is your turn");
		}
		else{
			turn.setForeground(Color.BLACK);
			turn.setText("not your turn");
		}
		cHand = cState.getHand(this.getId());
		topCards = cState.getHandVal(topID);

		//here we get this player's hand
		//adding the image to a label
		//then adding the button to the vector
		//gonna try setting default size on it as well
		bottomBox.removeAll();
		bHand.clear();
		for(Card c: this.cHand){
			ImageIcon cardFace = new ImageIcon(c.getImage());
			if(cHand.size()>10){
				int cWidth = WINDOW_SIZE/cHand.size();
				//show the front of the card
				Image img = cardFace.getImage();
				img = img.getScaledInstance(cWidth,(int) (cWidth*1.3),Image.SCALE_FAST);
				cardFace = new ImageIcon(img);
			}
			JButton temp = new JButton(cardFace);
			Dimension dim = new Dimension(cardFace.getIconWidth(), cardFace.getIconHeight());
			temp.setPreferredSize(dim);
			temp.setMaximumSize(dim);
			temp.setMinimumSize(dim);
			temp.addActionListener(this);
			bHand.add(temp);
			bottomBox.add(temp);
		}
		sBottom.setText("Player "+ (getId()+1) + "      Score: "+score + "      Cards in hand: "+cHand.size());

		//updates top player's score and hand
		topBox.removeAll();
		sTop.setText("Score: "+ cScores[topID]);
		for(int i = 0;i<topCards; i++){
			//can only see the backs of the card
			ImageIcon cardFace = new ImageIcon("images/default/00.gif");
			if(topCards>18){
				int cWidth = WINDOW_SIZE/topCards;
				Image img = cardFace.getImage();
				img = img.getScaledInstance(cWidth,(int) (cWidth*1.3),Image.SCALE_FAST);
				cardFace = new ImageIcon(img);
			}
			JButton temp = new JButton(cardFace);
			Dimension dim = new Dimension(cardFace.getIconWidth(), cardFace.getIconHeight());
			temp.setPreferredSize(dim);
			temp.setMaximumSize(dim);
			temp.setMinimumSize(dim);
			topBox.add(temp);
		}

		//updates right player's score and hand
		if(this.rightID > -1){
			sRight.setText("Score: "+ cScores[rightID]);
			rightBox.removeAll();
			this.rightCards = cState.getHandVal(rightID);
			for(int i = 0;i<rightCards; i++){
				//can only see the backs of the card
				ImageIcon cardFace = new ImageIcon("images/default/53.gif");
				if(rightCards > 8){
					int cWidth = (int) (WINDOW_SIZE*.4)/rightCards;
					Image img = cardFace.getImage();
					img = img.getScaledInstance((int) (cWidth*1.3),cWidth,Image.SCALE_FAST);
					cardFace = new ImageIcon(img);
				}
				JButton temp = new JButton(cardFace);
				Dimension dim = new Dimension(cardFace.getIconWidth(), cardFace.getIconHeight());
				temp.setPreferredSize(dim);
				temp.setMaximumSize(dim);
				temp.setMinimumSize(dim);
				rightBox.add(temp);
			}
		}

		//updates left player's score and hand
		if(this.leftID > -1){
			sLeft.setText("Score: "+cScores[leftID]);
			leftBox.removeAll();
			this.leftCards = cState.getHandVal(leftID);
			for(int i = 0;i<leftCards; i++){
				//can only see the backs of the card
				ImageIcon cardFace = new ImageIcon("images/default/53.gif");
				if(leftCards > 8){
					int cWidth = (int) (WINDOW_SIZE*.4/leftCards);
					Image img = cardFace.getImage();
					img = img.getScaledInstance((int) (cWidth*1.3),cWidth,Image.SCALE_FAST);
					cardFace = new ImageIcon(img);
				}
				JButton temp = new JButton(cardFace);
				Dimension dim = new Dimension(cardFace.getIconWidth(), cardFace.getIconHeight());
				temp.setPreferredSize(dim);
				temp.setMaximumSize(dim);
				temp.setMinimumSize(dim);
				leftBox.add(temp);
			}
		}
		this.activateMoveButtons(isTurn);
		validate();
	}
	private String setChatText(Vector<String> messages){
		String wholeText = "Messages that are over 50 characters will not be sent"+ "\n";
		int tMsg = messages.size();
		//only get the last 5 messages
		if (tMsg >= 5) tMsg = 5;
		//list most recent on bottom
		for (int i = messages.size()-tMsg ; i < messages.size(); i++){
			wholeText += messages.elementAt(i)+ "\n";
		}
		wholeText = wholeText.replaceAll("Player " + (getId()+1), "You");
		return wholeText;
	}
	private void activateMoveButtons(boolean b){
		for(JButton jb: bHand){
			jb.setEnabled(b);
		}
	}

	protected String defaultTitle(){
		return "Go Fish";
	}

	/**
	 * Use this to update the hand of the player by passing in a new hand
	 * 
	 */
	public void setHand(Vector<Card> newHand) {
		cHand = newHand;
	}
	public String setMoveText(Vector<Move> pastMoves){
		String wholeText = "";
		int tMoves = pastMoves.size();
		//only grabs last 3 moves
		if (tMoves >= 3) tMoves = 3;

		//creates the log. Most current info on the top
		for (int i = 0; i < tMoves; i++){
			Move m = pastMoves.elementAt(pastMoves.size() - 1 - i);
			wholeText += "Player " + (m.getPlayerTurn()+1) + " asked ";
			wholeText += "Player " + (m.getPlayerAsked()+1) + " for a ";
			wholeText += m.getCardAskedName() + "." + "\n";
			wholeText += "      Player " + (m.getPlayerAsked()+1);
			if(m.getCardsReceived() > 0){
				wholeText += " had " + m.getCardsReceived()+" " + m.getCardAskedName() + "\n";
			}
			else{
				wholeText += " said: Go-Fish. \n";
				if(m.isReceivedCardFromDeck()){
					wholeText += "Player " + (m.getPlayerTurn()+1) + " drew the card asked for. \n";
				}
			}
		}
		wholeText = wholeText.replaceAll("Player " + (getId()+1), "You");
		return wholeText;

	}
	public void paint(Graphics g){
		top.repaint();
	}
}