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
	
	@Resource(lookup="java:/MySqlDS")
	private DataSource dataSource;
	
	Statement st;
	ResultSet rs;
	ArrayList<String> listAllFilms = new ArrayList<String>();
	String json;

	private Gson gson;
	@PostConstruct
    public void init() {
		gson = new Gson();
		try (Connection con = dataSource.getConnection()){
			st = con.createStatement();
			
		    rs =st.executeQuery("SELECT JSON_UNQUOTE(name_film->\"$[0]\") as name1, JSON_UNQUOTE(name_film->\"$[1]\") as name2 FROM films");

	
	        while (rs.next()) {
	        	listAllFilms.add(rs.getString(1));
	        	listAllFilms.add(rs.getString(2));            
	        }
	        json = gson.toJson(listAllFilms);
	        System.out.println(json);
	        
    }catch(Exception e) {
    	e.printStackTrace();
    }
  }
	@Lock(READ)
	public String getListAllFilms() {
		return json;
	}
}
