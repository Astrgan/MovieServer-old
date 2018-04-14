package ru.movieServer;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/films")
public class FilmService {
	
	@EJB
	ListAllFilms listAllFilms;
	
	@Produces({"application/json"})
	@GET
	public Response getListFilms() {
		
		//return listAllFilms.getListAllFilms();
		
		return Response.ok(listAllFilms.getListAllFilms(), MediaType.APPLICATION_JSON)
				.build();
		
	}

	//@Consumes("application/json")
	//@Produces({"application/json"})
	@POST
	public Response getFilms(/*Film filterFilm, @HeaderParam("Content-Type") String h*/) {
		
		//System.out.println("@HeaderParam: " + h);
		//System.out.println(filterFilm.name);
		System.out.println("POST");
		
		
		return Response.ok("{\"Hello\": \"kittrtrtrte\"}", MediaType.APPLICATION_JSON)
				.build();
	}



}
