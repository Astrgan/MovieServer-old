package ru.movieServer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Lock;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.sql.DataSource;


import static javax.ejb.LockType.*;



@Singleton
@Startup
public class ListAllFilms {
	
	//@Resource(lookup="java:/MariaDB")
	@Resource(lookup="java:/MySqlDS")
	private DataSource dataSource;
		 
	Lists lists = new Lists();

	@PostConstruct
    public void init() {
	
		
		try (	
				Connection con = dataSource.getConnection();
				Statement st = con.createStatement();
				ResultSet rsFilms = st.executeQuery("SELECT * FROM names_film");
			){
			
			ArrayList<String> listAllFilms = new ArrayList<String>();		
	        while (rsFilms.next()) listAllFilms.add(rsFilms.getString(1));         	         
	        lists.jsonAllFilms = (String[]) listAllFilms.toArray(new String[0]); 
	        System.out.println(lists.jsonAllFilms);
	        
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
			lists.jsonAllGenres = (String[]) listAllGenres.toArray(new String[0]);
			
		}catch(Exception e) {
	    	e.printStackTrace();
	    }
		
		try (	
				Connection con = dataSource.getConnection();
				Statement st = con.createStatement();
				ResultSet rsGenres = st.executeQuery("SELECT year_of_release FROM films GROUP BY year_of_release");
			){
			
			ArrayList<String> listAllYears = new ArrayList<String>();
			while (rsGenres.next()) listAllYears.add(rsGenres.getString(1)); 
			lists.years = (String[]) listAllYears.toArray(new String[0]);
			
		}catch(Exception e) {
	    	e.printStackTrace();
	    }
		
		try (	
				Connection con = dataSource.getConnection();
				Statement st = con.createStatement();
				ResultSet rsGenres = st.executeQuery("select countries.country from countries");
			){
			
			ArrayList<String> listAllCountries = new ArrayList<String>();
			while (rsGenres.next()) listAllCountries.add(rsGenres.getString(1)); 
			lists.countries = (String[]) listAllCountries.toArray(new String[0]);
			
		}catch(Exception e) {
	    	e.printStackTrace();
	    }
		
		
		try (	
				Connection con = dataSource.getConnection();
				Statement st = con.createStatement();
				ResultSet rsActors = st.executeQuery("select name_actor from actors");
			){
			
			ArrayList<String> listAllActors = new ArrayList<String>();
			while (rsActors.next()) listAllActors.add(rsActors.getString(1)); 
			lists.actors = (String[]) listAllActors.toArray(new String[0]);
			
		}catch(Exception e) {
	    	e.printStackTrace();
	    }
		
		
		try (	
				Connection con = dataSource.getConnection();
				Statement st = con.createStatement();
				ResultSet rsWriters = st.executeQuery("select name_writers from writers");
			){
			
			ArrayList<String> listAllWriters = new ArrayList<String>();
			while (rsWriters.next()) listAllWriters.add(rsWriters.getString(1)); 
			lists.actors = (String[]) listAllWriters.toArray(new String[0]);
			
		}catch(Exception e) {
	    	e.printStackTrace();
	    }
		
  }
	@Lock(READ)
	public Lists getLists() {
		return lists;
	}

}
