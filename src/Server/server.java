package Server;
import java.awt.List;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.*;
public class server {
	private static int port = 0;
	public static void main(String[] args) {
		ServerWindow sw = new ServerWindow();
		while(true) {
			port = sw.getPort();
			if(port == 0)
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			else
				break;
		}
		ServerSocket server = null;
		ExecutorService es = Executors.newFixedThreadPool(10);
		ArrayList<Future<Boolean>> fList = new ArrayList<Future<Boolean>>();
		ArrayList<Task> tList = new ArrayList<Task>();
		int clientID = 0;
		GameThread gt = new GameThread(fList, tList, sw);
		gt.start();
		try {
			server = new ServerSocket(port);
			CheckThread ct = new CheckThread(fList, gt, tList, server, es, sw);
			ct.start();
			sw.appendMessage("Server is online.\n");
			while(true) {
				if(gt.on == true) {					
					for(int i=0; i < tList.size(); i++) {					
						if(fList.get(i).isDone() == true) {
							gt.disconnect(i);
							fList.remove(i);
							tList.remove(i);
							for(int j=0; j < tList.size(); j++) {
								tList.get(j).disconnect();
							}
							gt = new GameThread(fList, tList, sw);
							ct.setGameThread(gt);
							break;
						}					
					}
				}
				if(!gt.isAlive()) {
					gt = new GameThread(fList, tList, sw);
					ct.setGameThread(gt);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
