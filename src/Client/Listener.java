package Client;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;



public class Listener extends Thread{
	
	private DataInputStream in;
	private DataOutputStream out;

	
	private boolean[][] state = new boolean[20][20];
	private ArrayList<Coordinate> word = new ArrayList();	
	private ArrayList<Coordinate> matrixState = new ArrayList();	
	
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
	
	public void run() {
		
		try {
			
			String[] message = Trans.read(in);
			if(message[0].equals("alert") && message[1].equals("online")) {
				
				String[] reply= {"alert","Y"};
				Trans.send(out, reply);
				
			}else if(message[0].equals("alert") && message[1].equals("turn")){
				
				isReady = true;
			
			}else if(message[0].equals("word")) {
				
				resolveWord(message);
			}
			else if(message[0].equals("letter")) {
				
				fillLetter(message);
				
				
			}
				
				
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
	}
	
	public void resolveWord(String[] message) {
		
		
		word.clear();
		for(int i=1; i+3<message.length; i=i+3 ) {
			
			
			int dx = Integer.parseInt(message[i]);
			int dy = Integer.parseInt(message[i+1]);
		    char letter =message[i+2].charAt(0);

		    
		    word.add(new Coordinate(dx, dy, letter));
			
		}
		
	}
	
	
	public void fillLetter(String[] message) {
		
		int dx = Integer.parseInt(message[1]);
		int dy = Integer.parseInt(message[2]);
	    char letter =message[3].charAt(0);
	    state[dx][dy] = true;
	    matrixState.add(new Coordinate(dx, dy, letter));
	    
		
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
	

}
