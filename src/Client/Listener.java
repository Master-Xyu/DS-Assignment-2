package Client;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;



public class Listener extends Thread{
	
	private DataInputStream in;
	private DataOutputStream out;
	private static int id;

	
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
				pre.window.gui.myTurn();
			
			}
			else if(message[0].equals("alert") && message[1].equals("start")) {
				
				initializeScoreBoard(message);
				
			}
			else if(message[0].equals("alert") && message[1].equals("gameover")){
				
				pre.window.gui.gameover();
				
			}
			else if(message[0].equals("alert") && message[1].equals("server down")) {
				if(pre.window.myState == 3)
					pre.window.gui.serverDisconnect();
				if(pre.window.myState == 2||pre.window.myState == 1)
					pre.window.mid.ServerDisconnect();
				
			}
			else if(message[0].equals("alert") && message[1].equals("Game is on!")) {
				
				//pre.window.setWarning("Game is on!");
				
			}
			else if(message[0].equals("alert") && message[1].equals("pass")) {
				
				isReady = false;
				
			}
			else if(message[0].equals("alert") && message[1].equals("duplicated username")) {
				
				pre.window.invalidName();
				
			}
			else if(message[0].equals("alert") && message[1].equals("connected")) {
				
				pre.window.enterGame();
				
			}
			else if(message[0].equals("word")) {
				
				resolveWord(message);
				
			}
			else if(message[0].equals("chat")) {
				int num = Integer.parseInt(message[1]);
				pre.window.gui.chatMsg(num,message[2]);
				
			}
			else if(message[0].equals("letter")) {
				
				fillLetter(message);
				
			}
			else if(message[0].equals("list") && message[1].equals("wait")) {
				String player[] = new String[message.length-2];
				for(int i=2;i<message.length;i++)
					player[i-2] = message[i];
				if(pre.window.isEnter)
					pre.window.mid.updateList(player);
				else
					pre.window.addList(player);
			}
			else if(message[0].equals("list") && !message[1].equals("wait")) {
				String table = message[1];
				String state = message[2];
				String player[] = new String[message.length-3];
				for(int i=3;i<message.length;i++)
					player[i-3] = message[i];
				if(pre.window.isEnter)
					pre.window.mid.updateTable(state,table,player);
				else
					pre.window.addInfo(state,player);
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
		
		int len = message.length;
		word.clear();
		String thisWord = "";
		for(int i=0; i<(len-1)/3; i++ ) {
			
			
			int dx = Integer.parseInt(message[i*3+1]);
			int dy = Integer.parseInt(message[i*3+2]);
		    String letter =message[i*3+3];
		    thisWord = thisWord + letter;

		    
		    word.add(new Coordinate(dx, dy, letter));
			
		}
		
		pre.window.gui.updateWord(word);
		
		if(isReady) {
			
			isReady = false;
			
		}else {
			
			pre.window.gui.vote(thisWord, word);
			
		}
		
		wordLength = word.size();
		
	}
	
	
	private void fillLetter(String[] message) {
		
		int dx = Integer.parseInt(message[1]);
		int dy = Integer.parseInt(message[2]);
	    String letter =message[3];
	    state[dx][dy] = true;
	    
	    Coordinate newLetter =new Coordinate(dx, dy, letter);
	    matrixState.add(newLetter);
	    
	    pre.window.gui.updateLetter(newLetter);
	    
		
	}
	
	private void initializeScoreBoard(String[] message) {
		
		int number = Integer.parseInt(message[2]);
		this.setScore(new scoreBoard(number));
		
		id = Integer.parseInt(message[3]);
		
		String[] name = new String[number];
		for(int i=0; i<number; i++) {
			
			name[i] = message[i+4];
			
		}
		
		getScore().setPlayer(name);
		pre.window.startgame(id);
	}
	
	
	private void scorePlus(String[] message) {
		
		getScore().update(Integer.parseInt(message[1]), wordLength);
		pre.window.gui.updateScore(score);
		
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
