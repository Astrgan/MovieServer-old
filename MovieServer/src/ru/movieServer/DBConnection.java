package ru.movieServer;

import java.io.FilenameFilter;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
	
	@Resource(lookup="java:/MariaDB")
	private DataSource dataSource;
	private Gson gson;
	Statement st;
	ResultSet rs;
	ArrayList <Film> films;
	
	@PostConstruct
	void PostConstructur(){
		gson = new Gson();
		
	}
	
	public String getFilms(String JSONfilter) {
		
		
		
		try (Connection con = dataSource.getConnection()){
			films = new ArrayList<>();
			st = con.createStatement();
			
//		    rs =st.executeQuery("SELECT * FROM films");
			rs =st.executeQuery(generationSQL(JSONfilter));
	
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
	
	String generationSQL(String JSONfilter) {
		
		Film filmFiltr = gson.fromJson(JSONfilter, Film.class);
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("SELECT * FROM films WHERE true");
		
		
		if(filmFiltr.id != 0) builder.append(" and films.id_film=" + filmFiltr.id);
		if(filmFiltr.year != 0) builder.append(" and films.year_of_release =" + filmFiltr.year);
		
		System.out.println(builder.toString());
		return builder.toString();
	}
}
