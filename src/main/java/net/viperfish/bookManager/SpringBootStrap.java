package net.viperfish.bookManager;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import net.viperfish.bookManager.filters.PreSescLoggingFilter;

@Order(0)
public class SpringBootStrap implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.getServletRegistration("default").addMapping("/resources/*");

		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(RootApplicationContextConfig.class);
		servletContext.addListener(new ContextLoaderListener(rootContext));

		AnnotationConfigWebApplicationContext servletContextConfig = new AnnotationConfigWebApplicationContext();
		servletContextConfig.register(ServletApplicationContextConfig.class);
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("springDispatcher",
				new DispatcherServlet(servletContextConfig));

		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");

		FilterRegistration.Dynamic registration = servletContext.addFilter("preSecFilter", new PreSescLoggingFilter());
		registration.addMappingForUrlPatterns(null, false, "/*");

		FilterRegistration.Dynamic reg = servletContext.addFilter("encoding",
				new CharacterEncodingFilter("UTF-8", true));
		reg.addMappingForUrlPatterns(null, false, "/*");
	}

}
