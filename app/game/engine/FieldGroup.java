package game.engine;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;

public class FieldGroup {

	private HashSet<Point> fieldsInGroup;
	private HashSet<Point> fieldsToKillThisGroup;
	private GameBoard mainGameBoard;
	private boolean isAlive;

	public FieldGroup() {
		this.fieldsInGroup = new HashSet<Point>();
		this.fieldsToKillThisGroup = new HashSet<Point>();
		this.isAlive =true;
	}

	public FieldGroup(GameBoard gameBoard) {
		this.mainGameBoard = gameBoard;
		this.fieldsInGroup = new HashSet<Point>();
		this.fieldsToKillThisGroup = new HashSet<Point>();
		this.isAlive = true;
	}
	
	/**
	 * Returns how many stones are  in group
	 * @return Size of group
	 */
	public int getGroupSize() {
		return this.fieldsInGroup.size();
	}

	/**
	 * Adds a point to a group
	 * @param newPoint
	 */
	public void addToGroup(Point newPoint) {
		int x = newPoint.x;
		int y = newPoint.y;
		fieldsInGroup.add(newPoint);
		fieldsToKillThisGroup.remove(newPoint);
		Point point;
		if (y>0) {
			point = new Point(x, y-1);
			if (!fieldsInGroup.contains(point) && mainGameBoard.isEmpty(point))
				fieldsToKillThisGroup.add(point);
		}
		if (y<18) {
			point = new Point(x, y+1);
			if (!fieldsInGroup.contains(point) && mainGameBoard.isEmpty(point))
				fieldsToKillThisGroup.add(point);
		}
		if (x>0) {
			point = new Point(x-1, y);
			if (!fieldsInGroup.contains(point) && mainGameBoard.isEmpty(point))
				fieldsToKillThisGroup.add(point);
		}
		if (x<18) {
			point = new Point(x+1, y);
			if (!fieldsInGroup.contains(point) && mainGameBoard.isEmpty(point))
				fieldsToKillThisGroup.add(point);
		}
	}
	
	/**
	 * Returns a point which can cause ko rule to trigger
	 * @return
	 */
	public Point getKoPoint() {
		if(this.fieldsInGroup.size()==1) {
			Iterator<Point> it = fieldsInGroup.iterator();
			if (it.hasNext())
				return it.next();
		}
		return null;
	}
	
	/**
	 * Adds a point to breaths which was emptied
	 * @param p
	 */
	public void notifyEmpty(Point p) {
		this.fieldsToKillThisGroup.add(p);
	}
	
	/**
	 * Removes a point from breaths, because other player placed his stone
	 * @param point
	 */
	public void updateBreaths(Point point) {
		fieldsToKillThisGroup.remove(point);
	}
	
	/**
	 * Returns all points in group
	 * @return
	 */
	public HashSet<Point> getAllPointsInGroup() {
		return this.fieldsInGroup;
	}

	/**
	 * 
	 * @param point
	 * @return
	 */
	public boolean isNextTo (Point point) {
		for (Point p : fieldsInGroup) {
			int x = p.x;
			int y = p.y;
			Point newPoint;
			if (y>0) {
				newPoint = new Point(x, y-1);
				if (point.equals(newPoint))
					return true;
			}
			if (y<18) {
				newPoint = new Point(x, y+1);
				if (point.equals(newPoint))
					return true;
			}
			if (x>0) {
				newPoint = new Point(x-1, y);
				if (point.equals(newPoint))
					return true;
			}
			if (x<18) {
				newPoint = new Point(x+1, y);
				if (point.equals(newPoint))
					return true;
			}
		}
		return fieldsToKillThisGroup.contains(point);
	}

	public boolean contains (Point point) {
		return fieldsInGroup.contains(point);
	}
	
	public int getBreathsLeft() {
		return this.fieldsToKillThisGroup.size();
	}

	public int killThisGroup() {
		for (Point p : fieldsInGroup) {
			mainGameBoard.emptyField(p);
		}
		return this.fieldsInGroup.size();
	}

	public boolean isAlive() {
		return isAlive;
	}

	public boolean changAliveStatus() {
		this.isAlive = !isAlive;
		return isAlive;
	}

}