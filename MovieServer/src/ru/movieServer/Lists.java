package ru.movieServer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Lists {

	@XmlElement String[] jsonAllFilms;
	@XmlElement String[] jsonAllGenres;
	@XmlElement String[] jsonCountries;
	@XmlElement String[] years;
	@XmlElement String[] countries;
}
