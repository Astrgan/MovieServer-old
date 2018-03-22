package ru.movieServer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

@Stateless
public class DBConnection {
	
	@Resource(lookup="java:/MySqlDS")
	private DataSource dataSource;
	private Gson gson;
	Statement st;
	ResultSet rs;
	ArrayList <Film> films;
	@PostConstruct
	void PostConstructur(){
		gson = new Gson();
		
	}
	
	public String getAllFilms() {
		try (Connection con = dataSource.getConnection()){
			films = new ArrayList<>();
			st = con.createStatement();
		    rs =st.executeQuery("SELECT * FROM films.films");
			
	        while (rs.next()) {
	        	Film film = new Film();
	            film.name = rs.getString("name_film");
	            film.poster = rs.getString("poster");
	            film.description = rs.getString("description");
	            film.year = rs.getInt("year_of_release");
	            films.add(film);
	            
	        }
	        
        
		}catch (Exception e) {

	        e.printStackTrace();
	    }
		
		JsonArray jsonArray = new JsonParser().parse(gson.toJson(films)).getAsJsonArray();
		return gson.toJson(films);
	}
}
