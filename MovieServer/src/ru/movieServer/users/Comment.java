package ru.movieServer.users;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Comment {

	@XmlElement String comment;
	@XmlElement String token;
}
