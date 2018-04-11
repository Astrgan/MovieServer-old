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

@Lock(READ)
@Singleton
@Startup
public class ListAllFilms {
	
	@Resource(lookup="java:/MySqlDS")
	private DataSource dataSource;
	
	Statement st;
	ResultSet rs;
	public ArrayList<String> listAllFilms = new ArrayList<String>();
	@PostConstruct
    public void init() {
		try (Connection con = dataSource.getConnection()){
			st = con.createStatement();
			
		    rs =st.executeQuery("SELECT name_film->\"$[0]\" as name1, name_film->\"$[1]\" as name2 FROM films");

	
	        while (rs.next()) {
	        	listAllFilms.add(rs.getString(1));
	        	listAllFilms.add(rs.getString(2));            
	        }
	        System.out.println("SINGLTON START");
	        System.out.println(listAllFilms.toString());
	        
    }catch(Exception e) {
    	e.printStackTrace();
    }
  }

	public ArrayList<String> getListAllFilms() {
		return listAllFilms;
	}
}
