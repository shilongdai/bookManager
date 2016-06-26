package net.viperfish.bookManager.core;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class Report {
	private int total;
	private int categories;
	private int authors;
	private int languages;
	private Map<String, Integer> genreOverview;
	private List<Date> publishDates;
	private BigDecimal available;

	private List<Date> copyDates(List<Date> src) {
		List<Date> result = new LinkedList<>();
		if (src == null) {
			return result;
		}
		for (Date i : src) {
			if (i == null) {
				continue;
			}
			result.add(new Date(i.getTime()));
		}
		return result;
	}

	public Report(int total, int categories, int authors, int languages, Map<String, Integer> genreOverview,
			List<Date> publishDates, BigDecimal available) {
		super();
		this.total = total;
		this.categories = categories;
		this.authors = authors;
		this.languages = languages;
		this.genreOverview = new HashMap<>(genreOverview);
		this.available = available;

		this.publishDates = copyDates(publishDates);
	}

	public int getTotal() {
		return total;
	}

	public int getCategories() {
		return categories;
	}

	public int getAuthors() {
		return authors;
	}

	public int getLanguages() {
		return languages;
	}

	public Iterable<Entry<String, Integer>> getGenreOverview() {
		return new HashMap<String, Integer>(this.genreOverview).entrySet();
	}

	public List<Date> getPublishDates() {
		return new LinkedList<>(this.publishDates);
	}

	public BigDecimal getAvailable() {
		return available;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + authors;
		result = prime * result + ((available == null) ? 0 : available.hashCode());
		result = prime * result + categories;
		result = prime * result + ((genreOverview == null) ? 0 : genreOverview.hashCode());
		result = prime * result + languages;
		result = prime * result + ((publishDates == null) ? 0 : publishDates.hashCode());
		result = prime * result + total;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Report other = (Report) obj;
		if (authors != other.authors)
			return false;
		if (available == null) {
			if (other.available != null)
				return false;
		} else if (!available.equals(other.available))
			return false;
		if (categories != other.categories)
			return false;
		if (genreOverview == null) {
			if (other.genreOverview != null)
				return false;
		} else if (!genreOverview.equals(other.genreOverview))
			return false;
		if (languages != other.languages)
			return false;
		if (publishDates == null) {
			if (other.publishDates != null)
				return false;
		} else if (!publishDates.equals(other.publishDates))
			return false;
		if (total != other.total)
			return false;
		return true;
	}

}
