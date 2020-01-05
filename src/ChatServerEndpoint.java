import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value="/chatServerEndpoint", encoders= { ChatMessageEncoder.class }, decoders= { ChatMessageDecoder.class })
public class ChatServerEndpoint  {
static Set<Session> chatroomUsers	= Collections.synchronizedSet(new HashSet<Session>());

@OnOpen
public void handleOpen(Session userSession) {
	chatroomUsers.add(userSession);
	System.out.println("connected }}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}");
}

@OnMessage
public void handleMessage(ChatMessage incomingMessage,Session userSession)throws IOException,EncodeException{
	System.out.println("here ++++++++++++++++++++++++++++");
	String username = (String)userSession.getUserProperties().get("username");
	ChatMessage outgoingMessage = new ChatMessage();
	if(username==null) {
		userSession.getUserProperties().put("username",incomingMessage.getMessage());
		outgoingMessage.setUserName(incomingMessage.getMessage());
		outgoingMessage.setMessage("new user connected as: "+incomingMessage.getMessage());
		//userSession.getBasicRemote().sendObject(outgoingMessage);
		
	}
	else {
		outgoingMessage.setUserName(username);
		outgoingMessage.setMessage(incomingMessage.getMessage());
		
	}
	if(incomingMessage.getReciever()=="")
	broadcast(outgoingMessage);
	else
		unicast(outgoingMessage);
}

@OnClose
public void handleClose(Session userSession) {
	chatroomUsers.remove(userSession);
	
}

@OnError
public void onError(Session session, Throwable thr) {
	System.out.println("exception is ");
	thr.printStackTrace();
	
}

public void broadcast(ChatMessage outgoingMessage) throws IOException, EncodeException {
	for(Session allSession : chatroomUsers) {
		allSession.getBasicRemote().sendObject(outgoingMessage);
		
	}
}
public void unicast(ChatMessage outgoingMessage) throws IOException, EncodeException {
	for(Session recieverSession : chatroomUsers) {
		String username = (String)recieverSession.getUserProperties().get("username");
		if(username.equals(outgoingMessage.getReciever())) {
			recieverSession.getBasicRemote().sendObject(outgoingMessage);
		}
		
	}
	
}


}
