package be.duo.elastic.controller;

import javax.servlet.ServletContextEvent;

public class ServerListener implements javax.servlet.ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//TODO
		//check if elkstack is running
		//if not > startup
	}
	
	
}
