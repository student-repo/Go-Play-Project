package game.engine;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class GameBoard {

	private HashMap<Point, BoardFieldOwnership> boardFields;
	private ArrayList<FieldGroup> blackGroups;
	private ArrayList<FieldGroup> whiteGroups;
	/**
	 * Places a stone on given position and returns true if did it successfully, false otherwise
	 * @param point coordinate
	 * @param player enum value describing which player placed it
	 * @return true if move was successfully executed, false otherwise
	 */
	public boolean placeStone(Point point, BoardFieldOwnership player) {
		ArrayList<FieldGroup> friendlyGroups, enemyGroups;
		boolean isNotSuicide = false;
		FieldGroup newGroup = new FieldGroup(this);
		HashSet<Point> points;
		int blackStonesRemoved = 0,
				whiteStonesRemoved = 0;
		if (!(this.isEmpty(point))) {
			return false;
		}
		if (koTestNeeded) {
			if (point.equals(koPoint))
				return false;
		}
		if (player == BoardFieldOwnership.BLACK) {
			enemyGroups = getNearbyGroups(point, BoardFieldOwnership.WHITE);
			friendlyGroups = getNearbyGroups(point, player);
			if (enemyGroups.size() != 0) {
				for(FieldGroup gr : enemyGroups) {
					if (gr.getBreathsLeft() == 1) {
						if (gr.getGroupSize()==1){
							this.koPoint = gr.getKoPoint();
						}
						isNotSuicide = true;

						points = gr.getAllPointsInGroup();
						for (Point p : points) {
							ArrayList<FieldGroup> fgs = getNearbyGroups(p, player);
							for (FieldGroup fg : fgs)
								fg.notifyEmpty(p);
						}
						whiteStonesRemoved += gr.killThisGroup();
						whiteGroups.remove(gr);
					}
				}
			}

			if (friendlyGroups.size() == 0 && !isNotSuicide && !hasEmptyNearbyFields(point))
				return false;
			else {
				newGroup.addToGroup(point);
				for (FieldGroup gr : friendlyGroups) {
					points = gr.getAllPointsInGroup();
					for (Point p : points) {
						newGroup.addToGroup(p);
					}
				}
				if (newGroup.getBreathsLeft() == 0)
					return false;
				else {
					blackGroups.add(newGroup);
					for (FieldGroup gr : friendlyGroups) {
						blackGroups.remove(gr);
					}
				}
			}

		}
		else {
			enemyGroups = getNearbyGroups(point, BoardFieldOwnership.BLACK);
			friendlyGroups = getNearbyGroups(point, player);
			if (enemyGroups.size() != 0) {
				for(FieldGroup gr : enemyGroups) {
					if (gr.getBreathsLeft() == 1) {
						if (gr.getGroupSize()==1){
							this.koPoint = gr.getKoPoint();
						}
						isNotSuicide = true;

						points = gr.getAllPointsInGroup();
						for (Point p : points) {
							ArrayList<FieldGroup> fgs = getNearbyGroups(p, player);
							for (FieldGroup fg : fgs)
								fg.notifyEmpty(p);
						}
						blackStonesRemoved += gr.killThisGroup();
						blackGroups.remove(gr);
					}
				}
			}

			if (friendlyGroups.size() == 0 && !isNotSuicide && !hasEmptyNearbyFields(point))
				return false;
			else {
				newGroup.addToGroup(point);
				for (FieldGroup gr : friendlyGroups) {
					points = gr.getAllPointsInGroup();
					for (Point p : points) {
						newGroup.addToGroup(p);
					}
				}
				if (newGroup.getBreathsLeft() == 0)
					return false;
				else {
					whiteGroups.add(newGroup);
					for (FieldGroup gr : friendlyGroups) {
						whiteGroups.remove(gr);
					}
				}
			}
		}
		if(blackStonesRemoved == 1 || whiteStonesRemoved == 1) {
			koTestNeeded = true;
		}
		else
			koTestNeeded = false;
		this.capturedBlackStones += blackStonesRemoved;
		this.capturedWhiteStones += whiteStonesRemoved;
		boardFields.put(point, player);
		for(FieldGroup gr : enemyGroups) {
			gr.updateBreaths(point);
		}
		return true;
	}
	private int capturedWhiteStones = 0, capturedBlackStones = 0;
	private Point koPoint = null;
	private boolean koTestNeeded = false;

	private Integer boardSize = 19;


	/**
	 *  Default constructor
	 */
	public GameBoard() {
		boardFields = new HashMap<Point, BoardFieldOwnership>();

		for (int i=0; i<boardSize; i++) {
			for (int j=0; j<boardSize; j++) {
				boardFields.put(new Point(i, j), BoardFieldOwnership.FREE);
			}
		}
		blackGroups = new ArrayList<FieldGroup>();
		whiteGroups = new ArrayList<FieldGroup>();

	}

	/**
	 * Removes all groups that were marked as dead and adds number of stones removed.
	 */
	public void removeAllDeadGroups() {
		HashSet<FieldGroup> deadGroups;
		deadGroups = getAllDeadGroups(BoardFieldOwnership.BLACK);
		for (FieldGroup gr : deadGroups) {
			capturedBlackStones += gr.killThisGroup();
			blackGroups.remove(gr);
			HashSet<Point> points = gr.getAllPointsInGroup();
			for (Point p : points) {
				ArrayList<FieldGroup> fgs = getNearbyGroups(p, BoardFieldOwnership.WHITE);
				for (FieldGroup fg : fgs)
					fg.notifyEmpty(p);
			}
		}
		deadGroups = getAllDeadGroups(BoardFieldOwnership.WHITE);
		for (FieldGroup gr : deadGroups) {
			capturedWhiteStones += gr.killThisGroup();
			whiteGroups.remove(gr);
			HashSet<Point> points = gr.getAllPointsInGroup();
			for (Point p : points) {
				ArrayList<FieldGroup> fgs = getNearbyGroups(p, BoardFieldOwnership.BLACK);
				for (FieldGroup fg : fgs)
					fg.notifyEmpty(p);
			}
		}
	}
	
	/**
	 * Makes field empty
	 * @param point Field to be emptied
	 */
	public void emptyField(Point point) {
		boardFields.put(point, BoardFieldOwnership.FREE);
	}

	/**
	 * Checks if field is empty
	 * @param point point to be checked
	 * @return true if is empty, false if not
	 */
	public boolean isEmpty(Point point) {
		if (boardFields.get(point) == BoardFieldOwnership.FREE)
			return true;
		else
			return false;
	}
	
	
	/**
	 * Changes group status if it consists given Point
	 * @param point Point inside group
	 * @return true if point was found, false otherwise
	 */
	public boolean changeGroupStatus (Point point) {
		for (FieldGroup g : blackGroups) {
			if (g.contains(point)) {
				g.changAliveStatus();
				return true;
			}
		}
		for (FieldGroup g : whiteGroups) {
			if (g.contains(point)) {
				g.changAliveStatus();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Creates an array of GroupField objects near a Point p
	 * @param p Point around which we will look for groups
	 * @param color color of groups to be found
	 * @return An array of GroupField, if no groups were found return empty ArrayList
	 */
	
	private ArrayList<FieldGroup> getNearbyGroups (Point p, BoardFieldOwnership color) {
		ArrayList<FieldGroup> nearbyGroups = new ArrayList<>();
		if (color.equals(BoardFieldOwnership.BLACK)){
			for(FieldGroup gr : blackGroups) {
				if (gr.isNextTo(p)) {
					nearbyGroups.add(gr);
				}
			}
		} else {
			for(FieldGroup gr : whiteGroups) {
				if (gr.isNextTo(p)) {
					nearbyGroups.add(gr);
				}
			}
		}
		return nearbyGroups;
	}

	/**
	 * Gets how many white stones were captured
	 * @return Captured white stones
	 */
	public int getCapturedWhiteStones() {
		return capturedWhiteStones;
	}

	/**
	 * Gets how many black stones were captured
	 * @return Captured black stones
	 */
	public int getCapturedBlackStones() {
		return capturedBlackStones;
	}


	public int getCapturedColorStones(BoardFieldOwnership color) {
		if(color == BoardFieldOwnership.BLACK){
			return capturedBlackStones;
		}
		return capturedWhiteStones;
	}

	/**
	 * Returns current board as HashMap
	 * @return
	 */
	public HashMap<Point, BoardFieldOwnership> getBoardFields(){
		return boardFields;
	}
    
	/**
	 * Checks if point has empty fields next to it
	 * @param p point tested
	 * @return true if at least one field was empty
	 */
    private boolean hasEmptyNearbyFields (Point p){
    	int x = p.x;
		int y = p.y;
		Point point;
		if (y>0) {
			point = new Point(x, y-1);
			if (isEmpty(point))
				return true;
		}
		if (y<18) {
			point = new Point(x, y+1);
			if (isEmpty(point))
				return true;
		}
		if (x>0) {
			point = new Point(x-1, y);
			if (isEmpty(point))
				return true;
		}
		if (x<18) {
			point = new Point(x+1, y);
			if (isEmpty(point))
				return true;
		}
    	return false;
    }
    
    private HashSet<FieldGroup> getAllDeadGroups(BoardFieldOwnership color) {
    	HashSet<FieldGroup> deadGroups = new HashSet<>();
    	if (color == BoardFieldOwnership.BLACK) {
    		for (FieldGroup gr : blackGroups) {
    			if (!gr.isAlive()) {
    				deadGroups.add(gr);
    			}
    		}
    	}
    	else {
    		for (FieldGroup gr : whiteGroups) {
    			if (!gr.isAlive()) {
    				deadGroups.add(gr);
    			}
    		}
    	}
    	return deadGroups;
    
    }

	public int getWhiteTerritoryPoints(){
		int pkt = 0;
		for(Point p: boardFields.keySet()){
			if(boardFields.get(p) == BoardFieldOwnership.BLACK_PIECE_NOT_ALIVE ||
					boardFields.get(p) == BoardFieldOwnership.WHITE_TERRITORY){
				pkt ++;
			}
		}
		return pkt;
	}

	public int getBlackTerritoryPoints(){
		int pkt = 0;
		for(Point p: boardFields.keySet()){
			if(boardFields.get(p) == BoardFieldOwnership.WHITE_PIECE_NOT_ALIVE ||
					boardFields.get(p) == BoardFieldOwnership.BLACK_TERRITORY){
				pkt ++;
			}
		}
		return pkt;
	}

	public void restoreGameBoard(){
		for(Point p : boardFields.keySet()){
			if(boardFields.get(p) == BoardFieldOwnership.WHITE_PIECE_NOT_ALIVE){
				boardFields.put(p, BoardFieldOwnership.WHITE);
			}
			else if(boardFields.get(p) == BoardFieldOwnership.BLACK_PIECE_NOT_ALIVE){
				boardFields.put(p, BoardFieldOwnership.BLACK);
			}
			else if(boardFields.get(p) != BoardFieldOwnership.BLACK && boardFields.get(p) != BoardFieldOwnership.WHITE) {
				boardFields.put(p, BoardFieldOwnership.FREE);
			}
		}
	}

}
