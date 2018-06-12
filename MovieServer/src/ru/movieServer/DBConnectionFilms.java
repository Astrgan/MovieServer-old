package ru.movieServer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

import ru.movieServer.users.Comment;

@Stateless
public class DBConnectionFilms {
	
	//@Resource(lookup="java:/MariaDB")
	@Resource(lookup="java:/MySqlDS")
	private DataSource dataSource;


	Statement st;
	ResultSet rs;
	ArrayList <Film> films;
	
	@PostConstruct
	void PostConstructur(){

		
	}
		
	public ArrayList<Film> getFilms(Film filmFilter, String host) {
		
		try (Connection con = dataSource.getConnection()){
		
			films = new ArrayList<>();
			st = con.createStatement();		
			rs =st.executeQuery(generationSQL(filmFilter));
	
	        while (rs.next()) {
	        	Film film = new Film();
	        	film.id = rs.getInt("id_film");
	            film.name = rs.getString("name_film");
	            film.poster = rs.getString("poster");
	            film.description = rs.getString("description");
	            film.year = rs.getInt("year_of_release");
	            film.genres = rs.getString("genres").split(", ");
	            film.countries = rs.getString("countries").split(", ");
	            film.actors = rs.getString("actors").split(", ");
	            film.writers = rs.getString("writers").split(", ");
	            film.comments = getComments(film.id);
	            films.add(film);
	            
	        }
	        st.close();
	        rs.close();
        
		}catch (Exception e) {

	        e.printStackTrace();
	    }
		
		//JsonArray jsonArray = new JsonParser().parse(gson.toJson(films)).getAsJsonArray();
		return films;
	}
	
	private ArrayList<Comment> getComments(int id) {

		try(
				Connection con = dataSource.getConnection();
				PreparedStatement st = con.prepareStatement("select * from comments inner join users on comments.email=users.email where id_film = ?  order by date")	){
			
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			ArrayList<Comment> comments = new ArrayList<>();
			
			while(rs.next()) {
				Comment comment = new Comment();
				comment.comment = rs.getString("comment");
				comment.date = rs.getString("date");
				comment.name = rs.getString("name_user");
				comments.add(comment);
			}

			return comments;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	String generationSQL(Film filmFilter) {

		StringBuilder builder = new StringBuilder();
		
		builder.append("select *, (select group_concat(names_film.name_film  SEPARATOR ' / ') from names_film where names_film.id_film = films.id_film) as name_film, (select group_concat(connections_genres.genre  SEPARATOR ', ') from connections_genres where connections_genres.film = films.id_film) as genres, (select group_concat(connections_countries.country  SEPARATOR ', ') from connections_countries where connections_countries.film = films.id_film) as countries, (select group_concat(connections_actors.actor  SEPARATOR ', ') from connections_actors where connections_actors.film = films.id_film) as actors, (select group_concat(connections_writers.writers  SEPARATOR ', ') from connections_writers where connections_writers.film = films.id_film) as writers FROM films WHERE true");
				
		if(filmFilter.id != 0) builder.append(" and films.id_film =" + filmFilter.id);
		if(filmFilter.year != 0) builder.append(" and films.year_of_release =" + filmFilter.year);		
		if(filmFilter.name !=null & filmFilter.name != "") builder.append(" and films.id_film = (select names_film.id_film from names_film where names_film.name_film = \"" + filmFilter.name + "\")");
		if(filmFilter.genres[0] != "" & filmFilter.genres[0] != null) builder.append(" and films.id_film in(select connections_genres.film from connections_genres where connections_genres.genre=\""+ filmFilter.genres[0] +"\")");
		if(filmFilter.countries[0] != "" & filmFilter.countries[0] != null) builder.append(" and films.id_film in(select connections_countries.film from connections_countries where connections_countries.country=\""+ filmFilter.countries[0] + "\")");
		
		if(filmFilter.writers[0] != "" & filmFilter.writers[0] != null) builder.append(" and films.id_film in(select connections_writers.film from connections_writers where connections_writers.writers=\""+ filmFilter.writers[0]  +"\")");
		if(filmFilter.actors[0] != "" & filmFilter.actors[0] != null) builder.append(" and films.id_film in(select connections_actors.film from connections_actors where connections_actors.actor=\""+ filmFilter.actors[0]  +"\")");
		
		
		System.out.println(builder.toString());
		
		return builder.toString();
	}
}
