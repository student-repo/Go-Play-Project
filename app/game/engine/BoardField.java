package game.engine;

@Deprecated
public class BoardField {
	private BoardFieldOwnership owner;
	
	public BoardField() {
		this.owner = BoardFieldOwnership.FREE;
	}
	
	public void changeOwner(BoardFieldOwnership newOwner) {
		this.owner = newOwner;
	}
	
	public BoardFieldOwnership getOwner() {
		return owner;
	}
}
