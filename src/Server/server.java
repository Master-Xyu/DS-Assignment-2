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
		try {
			server = new ServerSocket(port);
			System.out.println("Server is online.");
			while(true) {
				Socket client = server.accept();
				if(gt.on == true) {					
					String[] message= {"alert","Game is on!"};
					Trans.send(new DataOutputStream(client.getOutputStream()), message);
					continue;
				}
				else {
					Task t = new Task(client);
					tList.add(t);
					Future<Boolean> f = es.submit(t);
					fList.add(f);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
