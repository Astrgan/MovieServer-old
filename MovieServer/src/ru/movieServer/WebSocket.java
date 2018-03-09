package ru.movieServer;

import javax.inject.Inject;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/ws")
public class WebSocket {

	@Inject DBConnection dbConnection;
}
