package net.viperfish.bookManager;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;

import net.viperfish.bookManager.filters.PostSecLoggingFilter;

@Order(2)
public class PostSecurityFilterBootStrap implements WebApplicationInitializer {

	public PostSecurityFilterBootStrap() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		FilterRegistration.Dynamic registration = servletContext.addFilter("postSecLoggingFilter",
				new PostSecLoggingFilter());
		registration.addMappingForUrlPatterns(null, false, "/*");
	}

}
