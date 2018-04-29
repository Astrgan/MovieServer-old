package ru.movieServer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Lists {

	@XmlElement public String[] jsonAllFilms;
	@XmlElement public String[] jsonAllGenres;
	@XmlElement public String[] jsonCountries;
	@XmlElement public String[] years;
	@XmlElement public String[] countries;
	@XmlElement public String[] actors;
	@XmlElement public String[] writers;
}
