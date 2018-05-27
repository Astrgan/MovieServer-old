package ru.movieServer.users;

import javax.annotation.Resource;
import javax.sql.DataSource;

public class DBConnectionUsers {
	@Resource(lookup="java:/MySqlDS")
	
	private DataSource dataSource;
	
	
}
