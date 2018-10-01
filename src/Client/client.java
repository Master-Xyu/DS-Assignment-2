package Client;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;
import java.util.ArrayList;

import java.net.ConnectException;

public class client extends Thread{
	
	private DataInputStream in;
	private DataOutputStream out;

	private ArrayList<ArrayList<Integer>> word;
	
	private static boolean state = false;
	
	private Socket socket;
	private Listener ls;


	
	
	public client(String address,int port) {
		
		try {
			
	
			Socket socket = new Socket(address, port);
			
			this.in = new DataInputStream(socket.getInputStream());
			this.out = new DataOutputStream(socket.getOutputStream());
			
			//String[] message= {"alert","connect"};
			//Trans.send(out, message);
	
			this.ls = new Listener(in, out);
			ls.start();
			
			state = true;
			
			
			
		} catch (ConnectException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {

			e.printStackTrace();
			
		} 
	}
	
	
	public boolean submitLetter(Coordinate letter) {
		
		if(ls.isReady() &&  !ls.getCurrentState()[letter.getDx()][letter.getDy()]) {
			
			String[] message = {"letter",  ""+ letter.getDx(), "" + letter.getDy(), letter.getLetter()};
			Trans.send(out, message);
			
			return true;
	
		}
		else {
			
			return false;
			
		}
		
	}
	
	public void submitWord(ArrayList<Coordinate> word){
		int num=word.size();
		
		if(ls.isReady()) {
			
			String[] message = new String[word.size()*3+1];                   //possible maximum number of the information in 'word'
			
			message[0] = "word";

			for(int i=0; i<word.size(); i++) {
				
				message[3*i + 1] = "" + word.get(i).getDx();
				message[3*i + 2] = "" + word.get(i).getDy();
				message[3*i + 3] = word.get(i).getLetter();

 			
			}
			
			
			Trans.send(out, message);
			ls.setReady(false);
	
		}
		
	}
	
	
	public boolean checkWord(ArrayList<Coordinate> word) {
		
		sort(word);
		
		Coordinate letter1 = word.get(0);
		Coordinate letter2 = word.get(1);
			
		if (letter1.getDx() == letter2.getDx()) {
			
			for(int i=0; i+1<word.size(); i++) {
				
				if(word.get(i).getLetter() !=  ""
						&& word.get(i).getDx() == word.get(i+1).getDx() 
						&& Math.abs(word.get(i).getDy() - word.get(i+1).getDy()) == 1)
						continue;
				else
					return false;
				
				
			}			
		}
		else if(letter1.getDy() == letter2.getDy()){
			
			for(int i=0; i+1<word.size(); i++) {
				
				if(word.get(i).getLetter() != ""
						&& word.get(i).getDy() == word.get(i+1).getDy() 
						&& Math.abs(word.get(i).getDx() - word.get(i+1).getDx()) == 1)
						continue;
				else
					return false;
			}
		}
		else
			return false;
	
		return true;
		
		
	}
	
	private void sort(ArrayList<Coordinate> word) {
		
		Coordinate letter1 = word.get(0);
		Coordinate letter2 = word.get(1);
		Coordinate letter = new Coordinate();
		
		if (letter1.getDx() == letter2.getDx()) {
			
			for(int i=0;i<word.size()-1;i++){
				for(int j=0;j<word.size()-1-i;j++){
					if(letter1.getDy()>letter2.getDy()){
						
						letter = letter1;
						letter1 = letter2;
						letter2 = letter;
						
					}
				}
			}
		}
		else {
			
			for(int i=0;i<word.size()-1;i++){
				for(int j=0;j<word.size()-1-i;j++){
					if(letter1.getDx()>letter2.getDx()){
						
						letter = letter1;
						letter1 = letter2;
						letter2 = letter;
						
					}
				}
			}
		}

	}
	
	public Listener getListener() {
		
		return ls;
		
	}
	
	public String connect(String username) {
		
		String[] message= {"connect", username};
		Trans.send(out, message);
		
		return "Ready required!";
		
	}
	
	public String ready() {
		String[] message= {"alert", "ready"};
		Trans.send(out, message);
		return "online";
	}
	
	/*
	 * 
	public String gameStart() {
		
		if(Trans.read(in)[1].equals("start"))
			return "start";
		
		else
			return "notyet";
		
	}
	
	*/
	
	public void vote(String voting) {
		
		if(voting.equals("yes")) {
			
			String[] message= {"alert", "Y"};
			Trans.send(out, message);
			
		}
	
		else{
			
			String[] message= {"alert", "N"};
			Trans.send(out, message);
			
		}
		
	}
	
	public void submit() {
		

		String[] message= {"alert", "pass"};
		Trans.send(out, message);
		
	}
	
	public void disconnect() {
		String[] message= {"alert", "exit"};
		Trans.send(out, message);
	}

	
	
	public scoreBoard getScore() {
		
		return ls.getScore();
		
	}
	
	
}
