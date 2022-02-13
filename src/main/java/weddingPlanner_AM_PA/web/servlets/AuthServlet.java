package weddingPlanner_AM_PA.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import weddingPlanner_AM_PA.services.UserService;

public class AuthServlet extends HttpServlet{

	private final UserService userService;
	private final ObjectMapper mapper;
	
	public AuthServlet(UserService userService, ObjectMapper mapper) {
		this.userService = userService;
		this.mapper = mapper;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        resp.getWriter().write("AuthServlet <h1>" + firstName + " " + lastName + "</h1>");
	}
}
