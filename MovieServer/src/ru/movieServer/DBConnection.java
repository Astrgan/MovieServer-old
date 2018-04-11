package ru.movieServer;

import java.io.FilenameFilter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.sql.DataSource;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Stateless
public class DBConnection {
	
	//@Resource(lookup="java:/MariaDB")
	@Resource(lookup="java:/MySqlDS")
	
	@EJB
	ListAllFilms listAllFilms;
	private DataSource dataSource;
	private Gson gson;
	Statement st;
	ResultSet rs;
	ArrayList <Film> films;
	
	@PostConstruct
	void PostConstructur(){
		gson = new Gson();
		
	}
	
	public String send(String JSON) {
		System.out.println("send");
		
		String result = null;
		JsonObject jObject = gson.fromJson(JSON, JsonObject.class);
		
		System.out.println(jObject.get("command").toString());
		System.out.println(jObject.get("value").toString());
		
		if(jObject.get("command").toString().equals("\"select\"")){
			result = getFilms(gson.fromJson(jObject.get("value").toString(), Film.class));
		}
		
		if(jObject.get("command").toString().equals("\"arrayAllNamesFilms\"")){
			result = gson.toJson(listAllFilms.getListAllFilms());
		}
		
		return result;
	}
	
	public String getFilms(Film filmFilter) {
		
		System.out.println("getFilms");
		
		try (Connection con = dataSource.getConnection()){
			films = new ArrayList<>();
			st = con.createStatement();
			
//		    rs =st.executeQuery("SELECT * FROM films");
			rs =st.executeQuery(generationSQL(filmFilter));
	
	        while (rs.next()) {
	        	Film film = new Film();
	        	film.id = rs.getInt("id_film");
	            film.name = rs.getString("name_film");
	            film.poster = rs.getString("poster");
	            film.description = rs.getString("description");
	            film.year = rs.getInt("year_of_release");
	            films.add(film);
	            
	        }
	        
        
		}catch (Exception e) {

	        e.printStackTrace();
	    }
		
		//JsonArray jsonArray = new JsonParser().parse(gson.toJson(films)).getAsJsonArray();
		return gson.toJson(films);
	}
	
	String generationSQL(Film filmFilter) {
		System.out.println("generationSQL");
		StringBuilder builder = new StringBuilder();
		
		builder.append("SELECT * FROM films WHERE true");
		
		
		if(filmFilter.id != 0) builder.append(" and films.id_film=" + filmFilter.id);
		if(filmFilter.year != 0) builder.append(" and films.year_of_release =" + filmFilter.year);
		
		System.out.println("SQL command: " + builder.toString());
		return builder.toString();
	}
}
