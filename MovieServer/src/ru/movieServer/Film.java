package ru.movieServer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Film {

	@XmlElement public int id;
	@XmlElement public String poster;
	@XmlElement public String name;
	@XmlElement public String description;
	@XmlElement public int year;	

}
