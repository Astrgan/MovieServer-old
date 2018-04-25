package ru.movieServer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.DependsOn;
import javax.ejb.Lock;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.sql.DataSource;

import com.google.gson.Gson;

import static javax.ejb.LockType.*;



@Singleton
@Startup
public class ListAllFilms {
	
	//@Resource(lookup="java:/MariaDB")
	@Resource(lookup="java:/MySqlDS")
	private DataSource dataSource;
		 
	String jsonAllFilms;
	String jsonAllGenres;

	private Gson gson;
	@PostConstruct
    public void init() {
		gson = new Gson();
		
		try (	
				Connection con = dataSource.getConnection();
				Statement st = con.createStatement();
				ResultSet rsFilms = st.executeQuery("SELECT * FROM names_film");
			){
			
			ArrayList<String> listAllFilms = new ArrayList<String>();		
	        while (rsFilms.next()) listAllFilms.add(rsFilms.getString(1));         	         
	        jsonAllFilms = gson.toJson(listAllFilms); 
	        System.out.println(jsonAllFilms);
	        
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	
		try (	
				Connection con = dataSource.getConnection();
				Statement st = con.createStatement();
				ResultSet rsGenres = st.executeQuery("SELECT genre FROM genres");
			){
			
			ArrayList<String> listAllGenres = new ArrayList<String>();
			while (rsGenres.next()) listAllGenres.add(rsGenres.getString(1)); 
			jsonAllGenres = gson.toJson(listAllGenres);
			
		}catch(Exception e) {
	    	e.printStackTrace();
	    }
		
		
		
		
  }
	@Lock(READ)
	public String getListAllFilms() {
		System.out.println("class ListAllFilms - " + jsonAllFilms);
		return jsonAllFilms;
	}
	
	@Lock(READ)
	public String getListAllGenres() {
		return jsonAllGenres;
	}
}
