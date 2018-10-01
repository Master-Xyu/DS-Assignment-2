package Client;

public class scoreBoard {
	private int number;
	
	private String[] player= new String[number];
	private int[] score;
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String[] getPlayer() {
		return player;
	}
	public void setPlayer(String[] player) {
		this.player = player;
	}
	public int[] getScore() {
		return score;
	}
	public void setScore(int[] score) {
		this.score = score;
	}
	
	scoreBoard(int num) {
		this.number = num;
		score = new int[number];
		for(int i= 0; i< num; i++) {
			score[i] = 0;
		}
	}
	
	public void setPlayer(String[] name,int[] sc) {
		for(int i=0;i<this.number;i++) {
			
			this.player[i] = name[i];
			this.score[i] = sc[i];
			
		}
	}
	
	public void update(int i,int sc) {
		
		this.score[i]= this.score[i] + sc;
		
	}
}
