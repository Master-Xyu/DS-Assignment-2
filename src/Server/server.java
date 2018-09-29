package Server;
import java.awt.List;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.*;
public class server {
	private static int port;
	public static void main(String[] args) {
		port = 1101;
		ServerSocket server = null;
		ExecutorService es = Executors.newFixedThreadPool(10);
		ArrayList<Future<Boolean>> fList = new ArrayList<Future<Boolean>>();
		ArrayList<Task> tList = new ArrayList<Task>();
		int clientID = 0;
		GameThread gt = new GameThread(fList, tList);
		gt.start();
		CheckThread ct = new CheckThread(fList, gt, tList, server, es);
		try {
			server = new ServerSocket(port);
			System.out.println("Server is online.");
			while(true) {
				if(gt.on == true) {					
					for(int i=0; i < tList.size(); i++) {					
						if(fList.get(i).isDone() == true) {
							gt.disconnect();
							fList.remove(i);
							tList.remove(i);
							for(int j=0; j < tList.size(); j++) {
								tList.get(j).disconnect();
							}
							gt = new GameThread(fList, tList);
							ct.setGameThread(gt);
							break;
						}					
					}
				}
				if(!gt.isAlive()) {
					gt = new GameThread(fList, tList);
					ct.setGameThread(gt);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
