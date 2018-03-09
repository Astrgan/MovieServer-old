package ru.movieServer;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;

@Stateless
public class DBConnection {
	
	@Resource(lookup="java:/MySqlDS")
	private DataSource dataSource;

}
