package ru.movieServer.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

@Stateless
public class DBConnectionComments {
	
	@Resource(lookup="java:/MySqlDS")	
	private DataSource dataSource;
	
	public int sendComment(Comment comment) {
		
		try(
				Connection connection = dataSource.getConnection();
				PreparedStatement psTokens = connection.prepareStatement("select email from tokens where token = ?;");
				PreparedStatement psComments = connection.prepareStatement("insert into comments(comment, email, date) value(?, ?, SYSDATE())")
			)
		{
			psTokens.setString(1, comment.token);
			ResultSet rs = psTokens.executeQuery();
			
			if(rs.next()) {
				psComments.setString(1, comment.comment);
				psComments.setString(2, rs.getString("email"));
				psComments.executeUpdate();
				return 0;
			}else
				return 1;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 1;
		
	}
}
