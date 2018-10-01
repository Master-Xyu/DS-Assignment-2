package Client;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;



public class Listener extends Thread{
	
	private DataInputStream in;
	private DataOutputStream out;

	
	private boolean[][] state = new boolean[20][20];
	private ArrayList<Coordinate> word = new ArrayList();
	private int wordLength;
	private ArrayList<Coordinate> matrixState = new ArrayList();	
	
	private scoreBoard score;

	private boolean isReady = false;
	
	public boolean isReady() {
		return isReady;
	}

	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}

	public Listener (DataInputStream in, DataOutputStream out) {
		
		this.in = in;
		this.out = out;

		for(int i=0; i<20; i++)
			for(int j=0; j<20; j++) {
				
				state[i][j] = false;
				
			}
		
	}
	
	public void setScore(scoreBoard score) {
		this.score = score;
	}

	


	public void run() {
		while(true) {
		try {
			
			String[] message = Trans.read(in);
			if(message[0].equals("turn")){
				
				isReady = true;
				pre.gui.myTurn();
			
			}
			else if(message[0].equals("alert") && message[1].equals("start")) {
				
				initializeScoreBoard(message);
				
			}
			else if(message[0].equals("alert") && message[1].equals("gameover")){
				
				System.exit(0);
				
			}
			else if(message[0].equals("alert") && message[1].equals("disconnected")) {
				
				System.exit(0);
				
			}
			else if(message[0].equals("alert") && message[1].equals("Game is on!")) {
				
				System.exit(0);
				
			}
			else if(message[0].equals("word")) {
				
				resolveWord(message);
				
			}
			else if(message[0].equals("letter")) {
				
				fillLetter(message);
				
			}
			else if(message[0].equals("score") && message[2].equals("plus")) {
				
				scorePlus(message);
				
			}
				
				
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		}
	}
	
	private void resolveWord(String[] message) {
		
		
		word.clear();
		String thisWord = "";
		for(int i=1; i+3<message.length; i=i+3 ) {
			
			
			int dx = Integer.parseInt(message[i]);
			int dy = Integer.parseInt(message[i+1]);
		    String letter =message[i+2];
		    thisWord = thisWord + letter;

		    
		    word.add(new Coordinate(dx, dy, letter));
			
		}
		
		pre.gui.updateWord(word);
		pre.gui.vote(thisWord, word);
		
		wordLength = word.size();
		
	}
	
	
	private void fillLetter(String[] message) {
		
		int dx = Integer.parseInt(message[1]);
		int dy = Integer.parseInt(message[2]);
	    String letter =message[3];
	    state[dx][dy] = true;
	    
	    Coordinate newLetter =new Coordinate(dx, dy, letter);
	    matrixState.add(newLetter);
	    
	    pre.gui.updateLetter(newLetter);
	    
		
	}
	
	private void initializeScoreBoard(String[] message) {
		
		int number = Integer.parseInt(message[2]);
		this.setScore(new scoreBoard(number));
		
		String[] name = new String[number];
		for(int i=0; i<number; i++) {
			
			name[i] = message[i+4];
			
		}
		
		getScore().setPlayer(name);
		pre.window.startgame();
	}
	
	
	private void scorePlus(String[] message) {
		
		getScore().update(Integer.parseInt(message[2]), wordLength);
		
	}
	
	public ArrayList<Coordinate> getMessage(){
		
		return word;
	}
	
	public ArrayList<Coordinate> getMartrixState(){
		
		return matrixState;	
	}
	
	public boolean[][] getCurrentState(){
		
		return state;
	}

	public scoreBoard getScore() {
		return score;
	}


}
