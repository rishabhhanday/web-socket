import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonException;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class ChatMessageDecoder implements Decoder.Text<ChatMessage>{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ChatMessage decode(String message) throws DecodeException {
		System.out.println("in decode");
		ChatMessage chatMessage= new ChatMessage();
		try {
		chatMessage.setReciever((Json.createReader(new StringReader(message)).readObject()).getString("reciever"));
		chatMessage.setMessage((Json.createReader(new StringReader(message)).readObject()).getString("message"));
		return chatMessage;
		}
		catch(Exception ex) {
			chatMessage.setMessage((Json.createReader(new StringReader(message)).readObject()).getString("message"));
			return chatMessage;
		}
		
	}

	
	@Override
    public boolean willDecode(String msg) {
		try {
			System.out.println(msg);
		System.out.println("will decode");
		JSONObject obj = (JSONObject) JSONValue.parse(msg);
		System.out.println(obj.get("message"));
        
            Json.createReader((new StringReader(msg)));
            return true;
        } catch (JsonException e){
        	e.printStackTrace();
            return false;
        }
    }

}
