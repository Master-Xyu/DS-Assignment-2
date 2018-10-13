package Server;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.annotation.processing.RoundEnvironment;
import javax.xml.ws.handler.MessageContext;

public class GameThread extends Thread {
	public ArrayList<Future<Boolean>> fList;
	public ArrayList<Task> tList;
	public Boolean on = false;
	public WaitingThread wt;
	private Boolean[] pass;
	private ServerWindow sw;
	public String disconnectedUser;
	public Boolean isDisconnected = false;
	public GameThread(ArrayList<Future<Boolean>> fList, ArrayList<Task> tList, ServerWindow sw, WaitingThread wt) {
		this.fList = fList;
		this.tList = tList;
		this.sw = sw;
		this.wt = wt;
	}
	
	public void run() {
		Boolean ready = false;
		while(true) {
			try {
				Thread.sleep(3000);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			for(int i=0; (i<tList.size()) && (tList.size() > 1);i++) {
				if(!tList.get(i).isReady()) {
					ready = false;
					break;
				}
				ready = true;
			}
			if(ready == true)
				break;
			ready = false;
		}
		on = true;
		ready = true;
		
		sw.appendMessage("Game starts!\n");
		
		game();	
		for(int i=0; i< tList.size();i++)
			tList.get(i).ready = false;
		this.on = false;
		run();
	}
	
	public void game(){
		pass = new Boolean[tList.size()];
		String[] message = new String[4 + tList.size()];
		message[0] = "alert";
		message[1] = "start";
		message[2] = Integer.toString(tList.size());
		for(int i = 0; i < tList.size(); i++)
			message[4 + i] = tList.get(i).getUsername();
		for(int i = 0; i < tList.size(); i++) {
			message[3] = Integer.toString(i);
			tList.get(i).output(message);
		}
		int[] score = new int[tList.size()];
		
		int count = 0;
		int round = 0;
		Boolean end = false;
		String turnUser = null;
		while(end == false) {
			round++;
			for(int i=0; i< tList.size(); i++)
				pass[i] = false; 
			for(int turn=0; turn<tList.size(); turn++) {
				if(count++==400)
					break;
				tList.get(turn).turn(round);
				turnUser = tList.get(turn).getUsername();
				message = getMessage(turnUser);
				System.out.println("1");
				groupSend(turn, message);
				if(message[1].equals("pass")) {
					pass[turn] = true;
					continue;
				}				
				
				System.out.println("12");
				
				message = getMessage(turnUser);
				groupSend(turn, message);
				if(message[1].equals("pass")) {
					//pass[turn] = true;
					continue;
				}	
				
				System.out.println("123");
				
				message = new String[3];
				message[0] = "score";
				message[1] = Integer.toString(turn);
				if(vote(turn)) {
					//score[turn]++;
					message[2] = "plus";	
				}
				else {
					message[2] = "unchanged";
				}
				groupSend(100, message);
			}
			for(int i = 0; i< tList.size(); i++) {
				end = true;
				if(pass[i] == false) {
					end = false;
					break;
				}
			}
			if(count == 400)
				end = true;
		}
		sw.appendMessage("Game over!\n");
		message = new String[2];
		message[0] = "alert";
		message[1] = "gameover";
		groupSend(100,message);
	}
	
	public Boolean vote(int i){
		String[] message;
		String user;
		Boolean agreed = true;
		for(int j=0; j<tList.size(); j++) {
			if(j == i)
				continue;
			user = tList.get(j).getUsername();
			message = getMessage(user);
			if(message[1].equals("disagree"))
				agreed = false;
		}
		return agreed;
	}
	
	public synchronized void groupSend(int n, String[] message){
		for(int j = 0; j<tList.size(); j++) {
			tList.get(j).output(message);
		}
	}
	
	public void disconnect(Task t, Future<Boolean> f) {
		sw.appendMessage(t.getUsername() + " disconnected!\n");
		sw.appendMessage("Game over!\n");
		this.disconnectedUser = t.getUsername();		
		this.tList.remove(t);
		this.fList.remove(f);
		this.isDisconnected = true;
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String[] getMessage(String user) {
		String[] message = null;
		while(message == null) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

			}
			for(int i = 0; i<tList.size(); i++)
				if(tList.get(i).getUsername().equals(user)) {					
					message = tList.get(i).getInMessage();
				}
		}
		System.out.println("1");
		return message;
	}
	
	public void leave(Task t, Future<Boolean> f) {
		tList.remove(t);
		fList.remove(f);
	}
}
