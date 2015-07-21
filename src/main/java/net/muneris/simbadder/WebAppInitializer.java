package net.muneris.simbadder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * This class sets the application context such as dispatcher servlet parameters,
 * error pages and other properties.
 * 
 * @author Adam Fitzpatrick (adam@muneris.net)
 */
public class WebAppInitializer implements WebApplicationInitializer {

	/**
	 * Registers the dispatcher servlet, which serves as the foundation element
	 * for Spring to determine how to handle incoming requests.
	 * TODO Add error page mapping
	 */
	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		ServletRegistration.Dynamic registration =
		        servletContext.addServlet("dispatcher", new DispatcherServlet());
		      registration.setLoadOnStartup(1);
		      registration.addMapping("/");
	}

	

}
