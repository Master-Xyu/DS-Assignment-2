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
		ExecutorService es = Executors.newFixedThreadPool(30);
		ArrayList<Future<Boolean>> fList1 = new ArrayList<Future<Boolean>>();
		ArrayList<Future<Boolean>> fList2 = new ArrayList<Future<Boolean>>();
		ArrayList<Future<Boolean>> waitF = new ArrayList<Future<Boolean>>();
		ArrayList<Task> tList1 = new ArrayList<Task>();
		ArrayList<Task> tList2 = new ArrayList<Task>();
		ArrayList<Task> waitT = new ArrayList<Task>();
		
		WaitingThread wThread = new WaitingThread(es, waitF, waitT, sw);
		GameThread gt1 = new GameThread(fList1, tList1, sw, wThread);
		GameThread gt2 = new GameThread(fList2, tList2, sw, wThread);
		wThread.setTable(gt1, gt2);
		sw.setWt(wThread);
		wThread.start();
		gt1.start();
		gt2.start();
		try {
			server = new ServerSocket(port);
			CheckThread ct = new CheckThread(server, sw, es);
			ct.addWait(waitF, wThread, waitT);
			ct.start();
			sw.appendMessage("Server is online.\n");
			while(true) {
				if(gt1.isDisconnected == true) {
					System.out.println("server1");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("server2");
					gt1.interrupt();
					String disconnectedUser = gt1.disconnectedUser;
					String[] message = new String[3];
					message[0] = "alert";
					message[1] = "disconnected";
					message[2] = disconnectedUser;
					
					for(int i=0; i<gt1.tList.size();i++)
						gt1.tList.get(i).output(message);
					System.out.println("server3");
					gt1 = new GameThread(fList1, tList1, sw, wThread);
					
					for(int i=0; i<gt1.tList.size();i++)
						gt1.tList.get(i).gt = gt1;
					wThread.table1 = gt1;
				}
				if(gt2.isDisconnected == true) {
					gt2.interrupt();
					String disconnectedUser = gt2.disconnectedUser;
					for(int i=0; i<gt2.tList.size();i++)
						gt2.tList.get(i).disconnect(disconnectedUser);
					gt2 = new GameThread(fList2, tList2, sw, wThread);
					for(int i=0; i<gt2.tList.size();i++)
						gt2.tList.get(i).gt = gt2;
					wThread.table2 = gt2;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
