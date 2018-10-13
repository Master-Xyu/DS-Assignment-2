package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Trans {

	public static String[] read(DataInputStream in) {
		String tmp[] = new String[50];
		String res[] = null;
		try {
			JSONParser parser = new JSONParser();
			JSONObject msg = (JSONObject) parser.parse(in.readUTF());
			tmp[0] = (String) msg.get("command");
			tmp[1] = (String) msg.get("content");
			if(tmp[0].equals("connect")) {
				res = new String[2];
				res[0] = (String) msg.get("command");
				res[1] = (String) msg.get("name");
			}
			if(tmp[0].equals("alert")) {
				if(tmp[1].equals("start")) {
					int n = Integer.parseInt((String) msg.get("number"));
					res=new String[n+4];
					res[0] = (String) msg.get("command");
					res[1] = (String) msg.get("content");
					res[2] = (String) msg.get("number");
					res[3] = (String) msg.get("num");
					for(int i=1;i<=n;i++) {
						String o=i+"";
						res[i+3] = (String) msg.get(o);
					}
				}
				else if(tmp[1].equals("disconnected"))
				{
					res=new String[3];
					res[0] = (String) msg.get("command");
					res[1] = (String) msg.get("content");
					res[2] = (String) msg.get("user");
				}
				else {
					res = new String[2];
					res[0] = (String) msg.get("command");
					res[1] = (String) msg.get("content");
				}
			}
			if(tmp[0].equals("turn")) {
				res = new String[2];
				res[0] = (String) msg.get("command");
				res[1] = (String) msg.get("num");
			}
			if(tmp[0].equals("score")) {
				res = new String[3];
				res[0] = (String) msg.get("command");
				res[1] = (String) msg.get("num");
				res[2] = (String) msg.get("state");
			}
			if(tmp[0].equals("letter")) {
				res = new String[4];
				res[0] = (String) msg.get("command");
				res[1] = (String) msg.get("x");
				res[2] = (String) msg.get("y");
				res[3] = (String) msg.get("letter");
			}
			if(tmp[0].equals("word")) {
				int n=0;
				while(msg.containsKey("letter"+(n+1)))
				{
					n++;
				}
				res = new String[1+n*3];
				res[0] = (String) msg.get("command");
				for(int i=1;i<=n;i++)
				{
					res[(i-1)*3+1]=(String) msg.get("x"+i);
					res[(i-1)*3+2]=(String) msg.get("y"+i);
					res[(i-1)*3+3]=(String) msg.get("letter"+i);
				}
			}
			if(tmp[0].equals("list"))
			{
				tmp[1] = (String) msg.get("type");
				if(tmp[1].equals("wait"))
				{
					int c1=0;
					while(msg.containsKey("player"+(c1+1)))
					{
						tmp[c1+2] = (String) msg.get("player"+(c1+1));
						c1++;
					}
					res = new String[2+c1];
					for(int c2=0;c2<c1+2;c2++)
						res[c2] = tmp[c2];
				}
				else
				{
					tmp[2] = (String) msg.get("state");
					int c1=0;
					while(msg.containsKey("player"+(c1+1)))
					{
						tmp[c1+3] = (String) msg.get("player"+(c1+1));
						c1++;
					}
					res = new String[3+c1];
					for(int c2=0;c2<c1+3;c2++)
						res[c2] = tmp[c2];
					
				}
			}
			if(tmp[0].equals("join"))
			{
				res = new String[2];
				res[0] = tmp[0];
				res[1] = (String) msg.get("table");
			}
			if(tmp[0].equals("leave"))
			{
				res = new String[1];
				res[0] = tmp[0];
			}
			if(tmp[0].equals("chat"))
			{
				res = new String[3];
				res[0] = tmp[0];
				res[1] = (String) msg.get("num");
				res[2] = (String) msg.get("chat");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	public static void send(DataOutputStream out, String[] message) {
		try {
			JSONObject response = new JSONObject();
			if(message.length>1)
			{
				if(message[0].equals("connect")) {
					response.put("command",message[0]);
					response.put("playername",message[1]);
				}
				if(message[0].equals("alert")) {
					response.put("command",message[0]);
					response.put("content",message[1]);
					if(message[1].equals("start"))
					{
						response.put("number",message[2]);
						response.put("num",message[3]);
						for(int i=1;i<=message.length-4;i++)
						{
							String o=i+"";
							response.put(o,message[i+3]);
						}
					}
					if(message[1].equals("disconnected"))
					{
						response.put("user",message[2]);
					}
				}
				if(message[0].equals("turn")) {
					response.put("command",message[0]);
					response.put("num",message[1]);
				}

				if(message[0].equals("score")) {
					response.put("command",message[0]);
					response.put("num",message[1]);
					response.put("state",message[2]);
				}
				if(message[0].equals("connect")) {
					response.put("command",message[0]);
					response.put("name",message[1]);
				}
				if(message[0].equals("letter")) {
					response.put("command",message[0]);
					response.put("x",message[1]);
					response.put("y",message[2]);
					response.put("letter",message[3]);
				}
				if(message[0].equals("word")) {
					int len=message.length;
					int number = (len-1)/3;
					response.put("command",message[0]);
					for(int i=1;i<=number;i++)
					{
						response.put("x"+i,message[i*3-2]);
						response.put("y"+i,message[i*3-1]);
						response.put("letter"+i,message[i*3]);
					}
				}
				if(message[0].equals("list")) {
					int len = message.length;
					if(len>=2)
					{
						response.put("command", message[0]);
						response.put("type", message[1]);
						if(message[1].equals("wait"))
						{
							int count = 1;
							while(count<=len-2)
							{
								String tmp = "player"+count;
								response.put(tmp, message[count+1]);
								count++;
							}
						}
						else 
						{
							response.put("state", message[2]);
							int count = 1;
							while(count<=len-3)
							{
								String tmp = "player"+count;
								response.put(tmp, message[count+2]);
								count++;
							}
						}
					}
				}
				if(message[0].equals("join")) {
					response.put("command", message[0]);
					response.put("table", message[1]);
				}
				if(message[0].equals("leave")) {
					response.put("command", message[0]);
				}
				if(message[0].equals("chat")) {
					response.put("command", message[0]);
					response.put("num", message[1]);
					response.put("chat", message[2]);
				}
			}
			out.writeUTF(response.toJSONString());
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
