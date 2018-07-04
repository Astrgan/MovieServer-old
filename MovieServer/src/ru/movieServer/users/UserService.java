package ru.movieServer.users;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


@Path("/users")
public class UserService {
	
	final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
	}
	
	@EJB
	DBConnectionUsers DBCUser;

	@Path("{command}")
//	@Consumes(MediaType.TEXT_PLAIN)
	@POST
	public String UsersProcess (@PathParam("command") String command, @FormParam("username") String username, @FormParam("email") String email, @FormParam("password") String password) {
		
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
		
		if(validate(email)) {
			
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
			
		}else {
			String response = "{"
					+ "\"status\": " + 2 + ","
					+ "\"message\":\"Некоректный EMAIL\""
					+ "}";
			return response;
		}
		return null;
	

	}
	
	
}