package ru.movieServer.users;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import ru.movieServer.Film;
import ru.movieServer.Lists;

@Path("/comment")
public class CommentService {

	
	@Consumes("application/json")
	@Produces({"application/json"})
	@GET
	public Lists getListComment() {		
		return null;
	}
	
	@Consumes("application/json")
	@Produces({"application/json"})
	@POST
	public String sendComment (Film filmFilter) {
				
		return null;

	}
	

}
