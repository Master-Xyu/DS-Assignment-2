package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

import javax.sql.rowset.spi.TransactionalWriter;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.TRANSACTION_MODE;

public class Task implements Callable<Boolean> {
	private Boolean ready;
	private Socket client;
	private Boolean current;
	private DataInputStream in;
	private DataOutputStream out;
	private Boolean turn;
	private Boolean over;
	private String username;
	private String[] inMessage = null;
	private ServerWindow sw; 
	
	public Task(Socket client, ServerWindow sw) {
		this.client = client;
		ready = false;
		current = false;
		turn = false;
		over = false;
		this.sw = sw;
		try {
			in = new DataInputStream(client.getInputStream());
			out = new DataOutputStream(client.getOutputStream());
		}catch(Exception e) {
		}
	}
	@Override
	public Boolean call() {
		username = Trans.read(in)[1];
		
		sw.appendMessage(username + " log in!\n");
		
		//String[] message = {"alert","ready required"};
		//Trans.send(out, message);
		String[] message;
		message = input();
		if(message[1].equals("ready")) {
			sw.appendMessage(username + " is ready!\n");
			ready = true;
		}
		else if(message[1].equals("exit"))
			return true;
		while(true) {
			message = input();
			if(message[1].equals("exit")) 
				break;
			if(message[1].equals("Y")) {	
				ready = true;
				continue;
			}
			if(message[1].equals("N")) {		
				return true;
			}
			inMessage = new String[message.length];
			for(int i = 0; i< message.length; i++)
				inMessage[i]= new String(message[i]);
		}
		return false;
	}
	
	public void turn(int round) {
		String[] message = new String[2];
		message = new String[2];
		message[0] = "turn";
		message[1] = Integer.toString(round);
		output(message);
	}
	
	public void disconnect() {
		String[] message = new String[2];
		message[0] = "alert";
		message[1] = "disconnected";
		output(message);
		ready = false;
	}
	public void setCurent(Boolean c) {
		current = c;
	}
	private String[] input() {
		return Trans.read(in);
	}
	public synchronized void output(String[] message) {
		Trans.send(out,message);
	}
	public Boolean isReady() {
		return ready;
	}
	public Boolean isCurrent() {
		return current;
	}
	public void setTurn(Boolean turn) {
		this.turn = turn;
	}
	public void setOver(Boolean over) {
		this.over = over;
	}
	public String getUsername() {
		return this.username;
	}
	public String[] getInMessage() {
		if(inMessage == null)
			return null;
		String[] message = new String[inMessage.length];
		for(int i = 0; i< message.length; i++)
			message[i]= new String(inMessage[i]);
		inMessage = null;
		return message;
	}
}
