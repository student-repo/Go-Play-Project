package game.engine;


import game.exceptions.IncorrectMoveException;

import java.awt.*;
import java.util.HashMap;

public class GameEngine {

	private GameBoard gameBoard;
	private GameEngineStatus gameStatus;
	private BoardFieldOwnership currentPlayer;
	private Player blackPlayer;
	private Player whitePlayer;
	private int turnCounter, 
				passCounter; //Checking if both player passed a turn
	private int whiteScore,
				blackScore;
	
	public GameEngine() {
		this.gameStatus = GameEngineStatus.PREPARING;
		this.gameBoard = new GameBoard();
		this.currentPlayer = BoardFieldOwnership.BLACK;
		this.turnCounter = 0;
		this.blackScore = 0;
		this.whiteScore = 0;
		this.gameStatus = GameEngineStatus.GAME;
		blackPlayer.notifyGameStateChanged(gameStatus);
		whitePlayer.notifyGameStateChanged(gameStatus);
	}
	
	public GameEngine(Player white, Player black) {
		this.gameStatus = GameEngineStatus.PREPARING;
		this.gameBoard = new GameBoard();
		this.currentPlayer = BoardFieldOwnership.BLACK;
		this.blackPlayer = black;
		this.whitePlayer = white;
		this.turnCounter = 0;
		this.blackScore = 0;
		this.whiteScore = 0;
		this.gameStatus = GameEngineStatus.GAME;
		blackPlayer.notifyGameStateChanged(gameStatus);
		whitePlayer.notifyGameStateChanged(gameStatus);
	}
	
	
	/**
	 * Makes a move directly from connected client. Sends references to itself.
	 * @param x 1st coordinate	
	 * @param y 2nd coordinate
	 * @param player Player who sent instruction
	 * @throws IncorrectMoveException Throws when move validated any rules of game
	 */
	public void makeMove(int x, int y, Player player)  throws IncorrectMoveException {
		if (player.getColor() != currentPlayer)
			throw new IncorrectMoveException("Not your turn");
		if (player == blackPlayer) {
			if (!gameBoard.placeStone(new Point (x, y), BoardFieldOwnership.BLACK))
				throw new IncorrectMoveException("Move not permitted");
			else {
				passCounter = 0;
				turnCounter++;
				changeCurrentPlayer();
			}
		}
		else if (player == whitePlayer) {
			if (!gameBoard.placeStone(new Point (x, y), BoardFieldOwnership.WHITE))
				throw new IncorrectMoveException("Move not permitted");
			else {
				passCounter = 0;
				changeCurrentPlayer();
				turnCounter++;
			}
			
		}
		else ;
		
	}
	
	/**
	 * 
	 * @param player
	 */
	public void resumeGame(Player player) {
		this.gameStatus = GameEngineStatus.GAME;
	}
	
	public void endGame() {
		this.gameStatus = GameEngineStatus.FINISHED;
		blackPlayer.notifyGameStateChanged(gameStatus);
		whitePlayer.notifyGameStateChanged(gameStatus);
		whitePlayer.announceWinner(getWinner(), blackScore, whiteScore);
		blackPlayer.announceWinner(getWinner(), blackScore, whiteScore);
	}
	
	public BoardFieldOwnership getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * 
	 */
	public boolean passTurn(Player player) {
		if (player.getColor() != currentPlayer)
			return false;
		changeCurrentPlayer();
		turnCounter++;
		passCounter++;
		if (passCounter == 2) {
			gameStatus = GameEngineStatus.NEGOTIATION;
			blackPlayer.notifyGameStateChanged(gameStatus);
			whitePlayer.notifyGameStateChanged(gameStatus);
		}
		return true;
	}
	
	public int getTurnCounter() {
		return this.turnCounter;
	}
	
	public void concedeGame(Player player) {
		if (player.getColor() == BoardFieldOwnership.BLACK) {
			blackScore = -10;
			gameStatus = GameEngineStatus.FINISHED;
			blackPlayer.notifyGameStateChanged(gameStatus);
			whitePlayer.notifyGameStateChanged(gameStatus);
		}
		else {
			whiteScore = -10;
			gameStatus = GameEngineStatus.FINISHED;
			blackPlayer.notifyGameStateChanged(gameStatus);
			whitePlayer.notifyGameStateChanged(gameStatus);
		}
	}
	
	public int getWhiteScore() {
		return whiteScore;
	}
	
	public int getBlackScore() {
		return blackScore;
	}

	public String getWinnerMessage(String whitePlayerName, String blackPlayerName){
		int whitePkt = gameBoard.getWhiteTerritoryPoints() + gameBoard.getCapturedBlackStones();
		int blackPkt = gameBoard.getBlackTerritoryPoints() + gameBoard.getCapturedWhiteStones();
		if(whitePkt > blackPkt){
			return whitePlayerName + " won with " + whitePkt + " to " + blackPkt;
		}
		else if (whitePkt < blackPkt) {
			return blackPlayerName + " won with " + blackPkt + " to " + whitePkt;
		}
		else{
			return "Draw";
		}
	}
	public GameEngineStatus getStatus() {
		return this.gameStatus;
	}
	
	private BoardFieldOwnership getWinner() {
		if((gameBoard.getCapturedWhiteStones()) >= gameBoard.getCapturedBlackStones())
			return BoardFieldOwnership.WHITE;
		else
			return BoardFieldOwnership.BLACK;
	}
	
	private void changeCurrentPlayer() {
		if (currentPlayer == BoardFieldOwnership.BLACK) {
			this.currentPlayer = BoardFieldOwnership.WHITE;
		}
		else
			this.currentPlayer = BoardFieldOwnership.BLACK;
	}

	public HashMap<Point, BoardFieldOwnership> getBoardFields(){
		return gameBoard.getBoardFields();
	}

	public void restoreGameBoard(){
		gameBoard.restoreGameBoard();
	}
	
}
