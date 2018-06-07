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
			
			String token = DBCUser.authUser(email, password);
			String code;
			if(token != null) code = "0";
			else code = "1";
			String response = "{"
					+ "\"status\": " + code + ","
					+ "\"token\":\""+ token + "\","
					+ "\"message\":\"Неверный EMAIL или ПАРОЛЬ\""
					+ "}";
			System.out.println(response);
			
			return response;
			
		} 
		
		if(command.equals("chekToken")) {
			String code = "1";
			String name = DBCUser.chekToken(password);
			if(name != null) code = "0";
			String response = "{"
					+ "\"status\": " + code + ","
					+ "\"name\":\""+ name + "\""
					+ "}";
			return response;
		}
		
		if(command.equals("logout")) {
			System.out.println("logout");
			String code = "1";
			String name = DBCUser.logOut(password);
			if(name != null) code = "0";
			
			String response = "{"
					+ "\"status\": " + code + ","
					+ "\"name\":\""+ name + "\""
					+ "}";
			return response;
		}
		return null;
	

	}
	
	
}