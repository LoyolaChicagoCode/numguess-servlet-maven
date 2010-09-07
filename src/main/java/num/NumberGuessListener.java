package num;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author laufer
 * 
 */
public class NumberGuessListener implements ServletContextListener,
		HttpSessionListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("context destroyed");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("context initialized");
		final ServletContext application = event.getServletContext();
		application.setAttribute(NumberGuessServlet.ATTRIBUTE_APPDATA,
				new NumberGuessAppData());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent event) {
		System.out.println("session created");
		final HttpSession session = event.getSession();
		final NumberGuessBean model = new NumberGuessBean();
		session.setAttribute(NumberGuessServlet.ATTRIBUTE_GUESS, model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		System.out.println("session destoyed");
	}
}
