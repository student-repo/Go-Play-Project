package models.msgs;

import java.awt.*;

public class Move {
	Point p;
//	final String name;
	String from;
	String to;



	public Point getPosition() {
		return p;
	}
	
	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}
	
	public Move(Point p, String from, String to) {
	    this.p = p;
	    this.from = from;
		this.to = to;
	
	}
}
