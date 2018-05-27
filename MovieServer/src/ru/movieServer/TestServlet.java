package ru.movieServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    @Inject DBConnectionFilms dbConnection;   
    
    public TestServlet() { 	    	
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
        try {
            InetAddress addr = java.net.InetAddress.getLocalHost();    
            System.out.println(addr);
            String hostname = addr.getHostName();    
            System.out.println("Hostname of system = " + hostname);
            out.println(addr);
            out.println("Hostname of system = " + hostname);
         
            
        } catch (UnknownHostException e) {
            System.out.println(e);
        }
        
  		Film film = new Film();
		film.id = 0;
		film.year = 0;

		//out.println(dbConnection.getFilms(gson.toJson(film))); 

	    response.getWriter().append("Served at88: ").append(request.getLocalAddr()).append(request.getServerName()).append(request.getLocalName()).append(request.getRemoteHost());
	}

}
