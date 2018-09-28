package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

import javax.sql.rowset.spi.TransactionalWriter;

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
	
	public Task(Socket client) {
		this.client = client;
		ready = false;
		current = false;
		turn = false;
		over = false;
		try {
			in = new DataInputStream(client.getInputStream());
			out = new DataOutputStream(client.getOutputStream());
		}catch(Exception e) {
		}
		username = Trans.read(in)[1];
	}
	@Override
	public Boolean call() {
		String[] message = input();
		if(message[1].equals("ready"))
			ready = true;
		else if(message[1].equals("exit"))
			return true;
		while(true) {
			if(turn == false)
				continue;
			if(over == true)
				break;
			message = new String[2];
			message[0] = "alert";
			message[1] = "turn";
			output(message);
			turn = false;
		}
		ready = false;
		if(input()[1].equals("Y")) {
			over = false;			
			call();
		}
		return true;
	}
	public void setCurent(Boolean c) {
		current = c;
	}
	public String[] input() {
		return Trans.read(in);
	}
	public void output(String[] message) {
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
}
