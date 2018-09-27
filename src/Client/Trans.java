package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Trans {

	public static String[] read(DataInputStream in) {
		String res[] = new String[2];
		try {
			JSONParser parser = new JSONParser();
			JSONObject msg = (JSONObject) parser.parse(in.readUTF());
			res[0] = (String) msg.get("command");
			res[1] = (String) msg.get("content");
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
			response.put("command",message[0]);
			response.put("content",message[1]);
			out.writeUTF(response.toJSONString());
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
