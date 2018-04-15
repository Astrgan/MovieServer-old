package ru.movieServer;

import javax.ejb.EJB;
import javax.inject.Inject;
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
	
	@Inject 
	DBConnection dbConnection;
	@EJB
	ListAllFilms listAllFilms;
	
	@Produces({"application/json"})
	@GET
	public String getListFilms() {
		
		return listAllFilms.getListAllFilms();
		
		//return Response.ok(listAllFilms.getListAllFilms(), MediaType.APPLICATION_JSON).build();
		
	}

	@Consumes("application/json")
	@Produces({"application/json"})
	@POST
	public String getFilms(Film filmFilter) {
		
		System.out.println(filmFilter.name);
		System.out.println(filmFilter.id);
		System.out.println(filmFilter.year);
		
		return dbConnection.getFilms(filmFilter);
		//return Response.ok("{\"Hello\": \"kittrtrtrte\"}", MediaType.APPLICATION_JSON).build();
	}



}
