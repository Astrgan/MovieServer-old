package ru.movieServer;

import javax.inject.Inject;
import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/ws")
public class WebSocket {

	@Inject DBConnection dbConnection;
	
	@OnMessage
	public String onMessage(String msg) {
		System.out.println(msg);
		return "Hello from Server :)))";
		
	}
}
