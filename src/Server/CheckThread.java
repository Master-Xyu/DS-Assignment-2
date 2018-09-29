package Server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class CheckThread extends Thread {
	ArrayList<Future<Boolean>> fList;
	GameThread gt;
	ArrayList<Task> tList;
	ServerSocket server;
	ExecutorService es;
	
	public CheckThread(ArrayList<Future<Boolean>> fList, GameThread gt, ArrayList<Task> tList, ServerSocket server, ExecutorService es){
		this.fList = fList;
		this.gt = gt;
		this.tList = tList;
		this.server = server;
		this.es = es;
	}
	
	public void run() {
		while(true) {
			Socket client;
			try {
				client = server.accept();
				if(gt.on == true) {					
					String[] message= {"alert","Game is on!"};
					Trans.send(new DataOutputStream(client.getOutputStream()), message);
					continue;
				}
				Task t = new Task(client);
				tList.add(t);
				Future<Boolean> f = es.submit(t);
				fList.add(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void setGameThread(GameThread gt) {
		this.gt = gt;
	}
}
