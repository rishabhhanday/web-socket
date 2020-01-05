import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import javax.json.Json;
public class ChatMessageEncoder implements Encoder.Text<ChatMessage>{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(ChatMessage message) throws EncodeException {
		return Json.createObjectBuilder().add("name",message.getUserName()).add("message",message.getMessage()).build().toString();
	}

}
