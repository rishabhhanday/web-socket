import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import javax.websocket.server.PathParam;
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
	broadcast(outgoingMessage);
}

@OnClose
public void handleClose(Session userSession,CloseReason cr,@PathParam("value") String value){
	System.out.println(cr.getReasonPhrase());
}

public void broadcast(ChatMessage outgoingMessage) throws IOException, EncodeException {
	for(Session allSession : chatroomUsers) {
		allSession.getBasicRemote().sendObject(outgoingMessage);
		
	}
}
@OnError
public void onError(Session session, Throwable thr) {
	System.out.println("exception is ");
	thr.printStackTrace();
	
}


}
