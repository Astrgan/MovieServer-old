package ru.movieServer.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.UUID;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

@Stateless
public class DBConnectionUsers {
	
	@Resource(lookup="java:/MySqlDS")	
	private DataSource dataSource;
	
	public String addUser(String nameUser, String email, String pass) {
		
		try(
				Connection connection = dataSource.getConnection();
				PreparedStatement psLogin = connection.prepareStatement("select name_user from users where name_user = ?");
				PreparedStatement ps = connection.prepareStatement("insert into users(name_user, email, pass) value(?, ?, ?)")
			)
		{
			psLogin.setString(1, nameUser);
			ResultSet rs = psLogin.executeQuery();
			
			if (rs.next()) {
				if(rs.getString("name_user").equals(nameUser)) return "Пользователь с таким ИМЕНЕМ уже существует";
				rs.close();
			}
			
			ps.setString(1, nameUser);
			ps.setString(2, email);
			ps.setString(3, pass);
			ps.executeUpdate();
			
		}catch(SQLIntegrityConstraintViolationException e){
			System.out.println("Duplicate entry");
			//return "Duplicate entry";
			return "Пользователь с таким EMAIL уже существует";
		}catch (Exception e) {
			e.printStackTrace();
			//return "error add user";
			return "Неудалось зарегестрировать ВАС :(";
		}
		
		
		return "Поздравляем, Вы зарегестрированны!!!";
	}
	
	public String authUser(String email, String pass){
		
		try(
				Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement("select pass from users where email = ?");
				PreparedStatement psToken = connection.prepareStatement("INSERT INTO tokens(token, email, date_token) value (?, ?, SYSDATE())")
			)
		{
			System.out.println("email: " + email + " pass: " + pass);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				String passDB = rs.getString("pass");
				
				if(passDB.equals(pass)) {
					
					String uuid = email.hashCode() + pass.hashCode() + UUID.randomUUID().toString();
					psToken.setString(1, uuid);
					psToken.setString(2, email);
					psToken.executeUpdate();
					rs.close();
					return uuid;
				}else
					return null;
			}else
				return null;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
		
	}
	
	public String chekToken(String token) {
		
		try(
				Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement("select * from tokens inner join users on tokens.email = users.email where token = ?");
				
				
			){
			
			ps.setString(1, token);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				if(rs.getString("token").equals(token)) return rs.getString("name_user");
			}
			rs.close();
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;
		
	}

	public String logOut(String token) {

		try(
				Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement("DELETE FROM tokens WHERE token = ?");
				
				
			){
			System.out.println("logoutDB");
			ps.setString(1, token);
			ps.executeUpdate();
			
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		
		return null;
	}
	
}
