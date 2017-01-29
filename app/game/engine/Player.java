package game.engine;

import java.awt.*;
import java.util.ArrayList;

public interface Player {
	
	public void stonePlaced(Point opponentPoint, BoardFieldOwnership player);
	
	public void playerPassedTurn(BoardFieldOwnership player);
	
	public void setColor(BoardFieldOwnership color);
	
	public BoardFieldOwnership getColor();
	
	public void notifyGameStateChanged(GameEngineStatus newState);
	
	public void announceWinner(BoardFieldOwnership winner, int blackScore, int whiteScore);
	
	public void territoryProposition(ArrayList<Point> blackTerritory, ArrayList<Point> whiteTerritory, BoardFieldOwnership player);
	

}
