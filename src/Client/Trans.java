package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Trans {

	public static String[] read(DataInputStream in) {
		String tmp[] = new String[2];
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
				res = new String[2];
				res[0] = (String) msg.get("command");
				res[1] = (String) msg.get("x");
				res[2] = (String) msg.get("y");
				res[3] = (String) msg.get("letter");
			}
			if(tmp[0].equals("Word")) {
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
			if(message.length>0) {
				if(message[0].equals("Connect")) {
					response.put("command",message[0]);
					response.put("playername",message[1]);
				}
				if(message[0].equals("alert")) {
					response.put("command",message[0]);
					response.put("content",message[1]);
					if(message[1].equals("start"))
					{
						response.put("content",message[1]);
						response.put("number",message[2]);
						response.put("num",message[3]);
						for(int i=1;i<=message.length-4;i++)
						{
							String o=i+"";
							response.put(o,message[i+3]);
						}
					}
				}
				if(message[0].equals("turn")) {
					response.put("command",message[0]);
					response.put("num",message[1]);
				}

				if(message[0].equals("score")) {
					response.put("command",message[0]);
					response.put("num",message[1]);
					response.put("state",message[1]);
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
				if(message[0].equals("Word")) {
					int len=message.length;
					int number = (len-1)/3;
					response.put("command",message[0]);
					for(int i=1;i<=number;i++)
					{
						response.put("x"+i,message[1]);
						response.put("y"+i,message[2]);
						response.put("letter"+i,message[3]);
					}
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
