package net.viperfish.bookManager.core;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

@Component
public class Genres implements ServletContextAware {

	private String[] genres;
	private String defaultGenre;

	public Genres() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		String genres = servletContext.getInitParameter("genres");
		this.genres = genres.split(",");
		this.defaultGenre = servletContext.getInitParameter("defaultGenre");
	}

	public String[] values() {
		return this.genres.clone();
	}

	public String getDefault() {
		return this.defaultGenre;
	}

}
