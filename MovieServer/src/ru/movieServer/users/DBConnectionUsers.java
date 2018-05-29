package ru.movieServer.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

@Stateless
public class DBConnectionUsers {
	
	@Resource(lookup="java:/MySqlDS")	
	private DataSource dataSource;
	
	public String addUser(String nameUser, String pass) {
		
		try(
				Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement("insert into users(name_user, pass) value(?, ?)")
			)
		{
			ps.setString(1, nameUser);
			ps.setString(2, pass);
			ps.executeUpdate();
			
		}catch(SQLIntegrityConstraintViolationException e){
			System.out.println("Duplicate entry");
			return "Duplicate entry";
		}catch (Exception e) {
			e.printStackTrace();
			return "error add user";
		}
		
		
		return "User added";
	}
}
