package Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class CheckThread extends Thread {
	WaitingThread wt;
	ArrayList<Task> waitT;
	ArrayList<Future<Boolean>> waitF;
	ServerSocket server;
	ExecutorService es;
	ServerWindow sw;
	
	public CheckThread(ServerSocket server, ServerWindow sw, ExecutorService es){		
		this.server = server;
		this.sw = sw;
		this.es = es;
	}
	
	public void run() {
		while(true) {
			Socket client;
			try {
				client = server.accept();
				if(waitT.size() == 10) {					
					/**To do**/
					String[] message= {"alert","Game is on!"};
					Trans.send(new DataOutputStream(client.getOutputStream()), message);
					continue;
				}		
				wt.join(client);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void addWait(ArrayList<Future<Boolean>> fList, WaitingThread wt,
			ArrayList<Task> tList) {
		this.waitT = tList;
		this.wt = wt;
		this.waitF = fList;
	}
}
