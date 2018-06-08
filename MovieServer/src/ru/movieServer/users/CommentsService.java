package ru.movieServer.users;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/comment")
public class CommentsService {
	
	@EJB
	DBConnectionComments DBCComments;
	
	@Consumes("application/json")
	@Produces({"application/json"})
	@POST
	public String sendComment(Comment comment) {
		
		int code = DBCComments.sendComment(comment);
		
		String response = "{"
				+ "\"status\": " + code 
				+ "}";
		
		return response;
	}

}
