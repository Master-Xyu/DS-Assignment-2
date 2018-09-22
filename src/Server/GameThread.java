package Server;

import java.util.ArrayList;
import java.util.concurrent.Future;

import javax.xml.ws.handler.MessageContext;

public class GameThread extends Thread {
	private ArrayList<Future<Boolean>> fList;
	private ArrayList<Task> tList;
	public Boolean on = false;
	public Boolean voting = false;
	public Boolean disconnected = false;
	public GameThread(ArrayList<Future<Boolean>> fList, ArrayList<Task> tList) {
		this.fList = fList;
		this.tList = tList;
	}
	
	public void run() {
		String[] message = new String[2];
		message[0] = "alert";
		message[1] = "ready";
		Boolean ready = true;
		while(true) {
			for(int i = 0; i < tList.size(); i++) {
				tList.get(i).output(message);
				if(tList.get(i).isReady() == false) {
					ready = false;
					break;
				}
			}
			if(ready == true)
				break;
			ready = true;
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		on = true;
		ready = true;
		try {
			CheckThread check = new CheckThread(fList, tList, this);
			check.start();
			game();
			check.interrupt();
		}catch(SecurityException e1) {	
		}
		catch(Exception e) {
			message[0] = "alert";
			message[1] = "disconnected";
			disconnected = false;
			try {
				groupSend(100, message);
			} catch (Exception e1) {
				e1.printStackTrace();
			}			
		}
		for(int i=0; i<tList.size();i++)
			tList.get(i).setOver(true);	
		run();
	}
	
	public void game() throws Exception {
		String[] message = {"alert", "start", Integer.toString(tList.size())};
		int[] score = new int[tList.size()];
		for(int i = 0; i<score.length;i++)
			score[i] = 0; 
		groupSend(100, message);
		int count = 0;
		while(true) {
			for(int i=0; i<tList.size(); i++) {
				if(count++==64)
					break;
				tList.get(i).setTurn(true);
				message = tList.get(i).input();
				groupSend(i, message);
				message = tList.get(i).input();
				groupSend(i, message);
				if(vote(i)) {
					score[i]++;
					message = new String[3];
					message[0] = "score";
					message[1] = "plus";
					message[2] = Integer.toString(i);
				}
				else {
					message = new String[2];
					message[0] = "score";
					message[1] = "unchanged";
				}
				voting = false;
				groupSend(100, message);
			}
			if(count == 65)
				break;
		}
		
	}
	
	public Boolean vote(int i) throws Exception{
		voting = true;
		String[] message;
		Boolean agreed = true;
		for(int j=0; j<tList.size(); j++) {
			if(disconnected == true)
				throw new Exception();
			if(j == i)
				continue;
			message = tList.get(j).input();
			if(message[1].equals("N"))
				agreed = false;
			else if(message[1].equals("exit"))
				throw new Exception();
		}
		return agreed;
	}
	
	public void groupSend(int n, String[] message) throws Exception {
		if(disconnected == true)
			throw new Exception();
		for(int j = 0; j<tList.size(); j++) {
			if(n == j)
				continue;
			tList.get(j).output(message);
		}
	}
}
