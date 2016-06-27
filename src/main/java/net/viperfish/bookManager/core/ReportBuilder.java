package net.viperfish.bookManager.core;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class ReportBuilder {

	private int total;
	private int categories;
	private int authors;
	private int languages;
	private Map<String, Integer> genreOverview;
	private List<Date> publishDates;
	private BigDecimal available;
	private Map<String, Integer> languageOverview;

	public ReportBuilder(int total, int categories, int authors, int languages, Map<String, Integer> genreOverview,
			List<Date> publishDates, BigDecimal available, Map<String, Integer> langOverview) {
		super();
		this.total = total;
		this.categories = categories;
		this.authors = authors;
		this.languages = languages;
		this.genreOverview = genreOverview;
		this.publishDates = publishDates;
		this.available = available;
		this.languageOverview = langOverview;
	}

	public ReportBuilder() {
		genreOverview = new HashMap<>();
		publishDates = new LinkedList<Date>();
		available = new BigDecimal(0);
	}

	public int getTotal() {
		return total;
	}

	public ReportBuilder setTotal(int total) {
		this.total = total;
		return this;
	}

	public int getCategories() {
		return categories;
	}

	public ReportBuilder setCategories(int categories) {
		this.categories = categories;
		return this;
	}

	public int getAuthors() {
		return authors;
	}

	public ReportBuilder setAuthors(int authors) {
		this.authors = authors;
		return this;
	}

	public int getLanguages() {
		return languages;
	}

	public ReportBuilder setLanguages(int languages) {
		this.languages = languages;
		return this;
	}

	public Map<String, Integer> getGenreOverview() {
		return genreOverview;
	}

	public ReportBuilder setGenreOverview(Map<String, Integer> genreOverview) {
		this.genreOverview = genreOverview;
		return this;
	}

	public List<Date> getPublishDates() {
		return publishDates;
	}

	public ReportBuilder setPublishDates(List<Date> publishDates) {
		this.publishDates = publishDates;
		return this;
	}

	public BigDecimal getAvailable() {
		return available;
	}

	public ReportBuilder setAvailable(BigDecimal available) {
		this.available = available;
		return this;
	}

	public Map<String, Integer> getLanguageOverview() {
		return languageOverview;
	}

	public void setLanguageOverview(Map<String, Integer> languageOverview) {
		this.languageOverview = languageOverview;
	}

	public Report build() {
		return new Report(total, categories, authors, languages, genreOverview, publishDates, available,
				languageOverview);
	}

}
