package ru.movieServer.users;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Comment {

	@XmlElement public String comment;
	@XmlElement public String token;
	@XmlElement public int id_film; 
	@XmlElement public String date;
	@XmlElement public String name;
}
