package Client;

public class Coordinate {
	
	private int dx;
	private int dy;
	private char letter;
	
	public Coordinate(int dx, int dy, char letter) {

		this.dx = dx;
		this.dy = dy;
		this.letter = letter;
	}
	
	public Coordinate() {
		
	}
	
	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public char getLetter() {
		return letter;
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

}
