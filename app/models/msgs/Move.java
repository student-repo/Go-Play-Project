package models.msgs;

public class Move {
	final int position;
	final String name;


	public int getPosition() {
		return position;
	}
	
	public String getName() {
		return name;
	}
	
	public Move(int position, String name) {
	    this.position = position;
	    this.name = name;
	
	}
}
