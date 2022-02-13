package weddingPlanner_AM_PA.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import weddingPlanner_AM_PA.daos.MonsterDAO;
//import weddingPlanner_AM_PA.daos.ScientistDAO;
//import weddingPlanner_AM_PA.services.MonsterService;
//import weddingPlanner_AM_PA.services.ScientistService;
//import weddingPlanner_AM_PA.web.servlets.AuthServlet;
//import weddingPlanner_AM_PA.web.servlets.MonsterServlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import weddingPlanner_AM_PA.daos.UserDAO;
import weddingPlanner_AM_PA.services.UserService;
import weddingPlanner_AM_PA.web.servlets.AuthServlet;

@WebListener
public class ContextLoaderListener implements ServletContextListener{
	
//	private final Logger logger = LogManager.getLogger();

	@Override
	public void contextInitialized(ServletContextEvent sce) {
//		logger.info("Application is initiliazing.....");
		ObjectMapper mapper = new ObjectMapper();
		
		UserDAO userDAO = new UserDAO();
		
		
//		ScientistDAO scientistDAO = new ScientistDAO();
//		MonsterDAO monsterDAO = new MonsterDAO();
//		ScientistService scientistService = new ScientistService(scientistDAO);
		UserService userService = new UserService(userDAO);
//		MonsterService monsterService = new MonsterService(monsterDAO, scientistService);
//		
//		MonsterServlet monsterServlet = new MonsterServlet(monsterService, mapper);
//		AuthServlet authServlet = new AuthServlet(scientistService, mapper);
//		
		AuthServlet authServlet = new AuthServlet(userService, mapper);
		
		
		ServletContext context = sce.getServletContext();
//		context.addServlet("MonsterServlet", monsterServlet).addMapping("/monsters/*");
//		context.addServlet("AuthServlet", authServlet).addMapping("/auth");
		
		context.addServlet("AuthServlet", authServlet).addMapping("/auth");
		
//		logger.info("Application initiliazed!!! We do did it!~WOOO~");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		ServletContextListener.super.contextDestroyed(sce);
	}
	
	
}
