package weddingPlanner_AM_PA.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import weddingPlanner_AM_PA.models.User;
import weddingPlanner_AM_PA.services.UserService;
import weddingPlanner_AM_PA.web.dto.LoginCredentials;
import weddingPlanner_AM_PM.exceptions.AuthenticationException;
import weddingPlanner_AM_PM.exceptions.InvalidRequestException;

public class AuthServlet extends HttpServlet {

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

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//		LoginCredentials loginCreds = mapper.readValue(req.getInputStream(), LoginCredentials.class);
//		resp.getWriter().write("<h1>AuthServlet doPost </h1>" + loginCreds.getUsername());
		try {
			LoginCredentials loginCreds = mapper.readValue(req.getInputStream(), LoginCredentials.class);
			User authenticatedUser = userService.authenticateUser(loginCreds.getUsername(), loginCreds.getPassword());
			HttpSession httpSession = req.getSession(true);
			httpSession.setAttribute("authUser", authenticatedUser);
		} catch (InvalidRequestException | UnrecognizedPropertyException e) {
			// TODO: handle exception
			resp.setStatus(400);
		} catch (AuthenticationException e) {
			// TODO: handle exception
			resp.setStatus(401);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resp.setStatus(500);
		}
	}
}
