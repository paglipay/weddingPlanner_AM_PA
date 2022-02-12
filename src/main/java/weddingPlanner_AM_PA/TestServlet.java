package weddingPlanner_AM_PA;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* Servlet Lifecyle
 * 
 * 1- initiliazation - specifically calling a .init(), this method is called ONCE during the lifetime of the servley
 * 
 * 2 - service() the servlet instance can service client request
 * 
 * 3 - destory() - takes the servlet out of service - this method is only called once at the very end of a web servers life
 */


public class TestServlet extends HttpServlet {
	
	@Override
		public void init() throws ServletException {
			System.out.println("Init has been called for TestServlet");
		}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        resp.getWriter().write("<h1>" + firstName + " " + lastName + "</h1>");
	}
	
	@Override
		public void destroy() {
			System.out.println("TestServlet Destroyer");
		}

}
