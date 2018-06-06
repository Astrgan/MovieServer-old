package ru.movieServer.users;


import javax.ejb.EJB;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


@Path("/users")
public class UserService {
	
	@EJB
	DBConnectionUsers DBCUser;

	@Path("{command}")
//	@Consumes(MediaType.TEXT_PLAIN)
	@POST
	public String UsersProcess (@PathParam("command") String command, @FormParam("username") String username, @FormParam("email") String email, @FormParam("password") String password) {
		
		System.out.println("UsersProcess");
		
		if(command.equals("add")) {
			
			System.out.println("add");
			String status = DBCUser.addUser(username, email ,password);
			
			String response = "{\"status\":\"" + status +"\"}";
			System.out.println(response);
			return response;
			
			
		}
		
		if(command.equals("auth")) {
			System.out.println("{\"status\": 1}");
			
			return "{\"status\": 1}";
			
		} 
		return null;
	

	}
	
	
}