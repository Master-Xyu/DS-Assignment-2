package Server;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class WaitingThread extends Thread {
	public GameThread table1;
	public GameThread table2;
	public ExecutorService es;
	public ArrayList<Future<Boolean>> waitF;
	public ArrayList<Task> waitT;
	public ServerWindow sw = null;
	public ArrayList<String> usernames;
	
	public WaitingThread(ExecutorService es, ArrayList<Future<Boolean>> waitF,
			ArrayList<Task> waitT, ServerWindow sw) {
		this.es = es;
		this.waitF = waitF;
		this.waitT = waitT;
		this.sw = sw;
		usernames = new ArrayList<String>();
	}
	public void run() {
		int table = 0;
		while(true) {
			for(int i = 0; i< waitT.size(); i++) {
				table = waitT.get(i).getTable();
				if(table != 0) {
					Task t = waitT.get(i);
					Future<Boolean> f = t.f;
					waitT.remove(i);
					waitF.remove(i);
					if(table == 1) {
						table1.tList.add(t);				
						table1.fList.add(f);
						t.gt = table1;
					}
					else if(table == 2) {
						table2.tList.add(t);				
						table2.fList.add(f);
						t.gt = table2;
					}
				}
				refresh();
			}
		}
	}
	
	public void join(Socket client) {
		Task t = new Task(client, sw, this);				
		Future<Boolean> f = es.submit(t);	
		t.f = f;
		while(true) {
			if(t.getUsername() != null)
				break;
		}
		try {
			int n = usernames.indexOf(t.getUsername());
			if(n == -1)
				throw new Exception();
			String[] message= {"alert","duplicated username"};
			Trans.send(new DataOutputStream(client.getOutputStream()), message);
			return;
		}catch(Exception e) {
			usernames.add(t.getUsername());
			String[] message= {"alert","connected"};
			t.output(message);
		}
		waitT.add(t);
		waitF.add(f);
		refresh();
	}
	
	public synchronized void refresh() {
		String[] message1 = new String[2+waitT.size()];
		message1[0] = "list";
		message1[1] = "wait";
		for(int i = 0; i < waitT.size(); i++)
			message1[2+i] = waitT.get(i).getUsername();
		
		String[] message2 = new String[3 + table1.tList.size()];
		message2[0] = "list";
		message2[1] = "table1";
		if(table1.on == true)
			message2[2] = "on";
		else
			message2[2] = "off";
		for(int i = 0; i < table1.tList.size(); i++)
			message2[2+i] = table1.tList.get(i).getUsername();
		
		String[] message3 = new String[3 + table2.tList.size()];
		message3[0] = "list";
		message3[1] = "table2";
		if(table2.on == true)
			message3[2] = "on";
		else
			message3[2] = "off";
		for(int i = 0; i < table2.tList.size(); i++)
			message3[2+i] = table2.tList.get(i).getUsername();
		for(int i = 0; i < waitT.size(); i++) {
			waitT.get(i).output(message1);
			waitT.get(i).output(message2);
			waitT.get(i).output(message3);
		}
		for(int i = 0; i < table1.tList.size(); i++) {
			table1.tList.get(i).output(message1);
			table1.tList.get(i).output(message2);
			table1.tList.get(i).output(message3);
		}
		for(int i = 0; i < table2.tList.size(); i++) {
			table2.tList.get(i).output(message1);
			table2.tList.get(i).output(message2);
			table2.tList.get(i).output(message3);
		}
	}
	
	public void userJoin(Task t, Future<Boolean> f) {
		waitT.add(t);
		waitF.add(f);
	}
	
	public void setTable(GameThread table1, GameThread table2) {
		this.table1 = table1;
		this.table2 = table2;
	}
	
	public void serverDown() {
		String[] message = new String[30];
		message[0] = "alert";
		message[1] = "server down";
		for(int i = 0; i < waitT.size(); i++) 
			waitT.get(i).output(message);
		for(int i = 0; i < table1.tList.size(); i++) 
			table1.tList.get(i).output(message);
		for(int i = 0; i < table2.tList.size(); i++) 
			table2.tList.get(i).output(message);
	}
	public void deleteUser(String name) {
		usernames.remove(name);
	}
}
