package ru.movieServer;

import java.sql.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(lookup="java:/MySqlDS")
	private DataSource dataSource;
	Statement st;
	ResultSet rs;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		PrintWriter out = response.getWriter();
	    try (Connection con = dataSource.getConnection()){
	        st = con.createStatement();
	        rs =st.executeQuery("SELECT DATABASE()");

	        while (rs.next()) {
	            String count = rs.getString("DATABASE()");
	            out.println("Database: " + count);
	        }


	    } catch (Exception e) {

	        e.printStackTrace();
	    }

	    response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
