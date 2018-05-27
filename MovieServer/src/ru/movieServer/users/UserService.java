package ru.movieServer.users;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/users")
public class UserService {

	@Path("{command}")
//	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/x-www-form-urlencoded")
	@POST
	public String addUser (User user, @PathParam("command") String command, @FormParam("username") String username, @FormParam("password") String password) {
				
		if(command.equals("add")) ;
		
		if(command.equals("auth")) {
			return "auth ok username: " + username + "password: " + password;
		}
		return null;
	

	}
	
	
}
