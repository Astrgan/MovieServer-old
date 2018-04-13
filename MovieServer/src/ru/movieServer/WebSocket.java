package ru.movieServer;

import javax.inject.Inject;
import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/ws")
public class WebSocket {

	@Inject DBConnection dbConnection;
	
	
	@OnMessage
	public String onMessage(String msg) {
		String str = dbConnection.send(msg);
	
		return str;
		
	}
}
