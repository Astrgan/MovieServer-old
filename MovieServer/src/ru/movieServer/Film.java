package ru.movieServer;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ru.movieServer.users.Comment;

@XmlRootElement
public class Film {

	@XmlElement public int id;
	@XmlElement public int year;
	@XmlElement public String poster;
	@XmlElement public String name;
	@XmlElement public String description;	
	@XmlElement public String[] genres;
	@XmlElement public String[] countries;
	@XmlElement public String[] actors;
	@XmlElement public String[] writers;
	@XmlElement public ArrayList<Comment> comments;

}
