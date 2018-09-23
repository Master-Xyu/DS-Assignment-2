package Server;

import java.util.ArrayList;
import java.util.concurrent.Future;
import java.lang.*;

public class CheckThread extends Thread {
	private ArrayList<Future<Boolean>> fList;
	private ArrayList<Task> tList;
	private GameThread g;
	
	public CheckThread(ArrayList<Future<Boolean>> fList, ArrayList<Task> tList, GameThread g) {
		this.fList = fList;
		this.tList = tList;
		this.g = g;
	}
	public void run() {
		String[] on = {"alert", "online"};
		while(true) {
			try {
				Thread.sleep(5000);
				if(g.voting == true)
					continue;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for(int i = 0; i<tList.size(); i++) {
				tList.get(i).output(on);
				if(tList.get(i).input()[1].equals("exit")) {
					g.disconnected = true;
					return;
				}
			}
		}
	}
}
