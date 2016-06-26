package net.viperfish.bookManager.transactions;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.viperfish.bookManager.core.Book;
import net.viperfish.bookManager.core.BookBuilder;
import net.viperfish.bookManager.core.BookDatabase;
import net.viperfish.bookManager.core.Report;
import net.viperfish.bookManager.core.ReportBuilder;
import net.viperfish.bookManager.core.TransactionWithResult;

class ReportGenerationTransaction extends TransactionWithResult<Report> {

	private BookDatabase db;
	private List<Book> allBooks;

	public ReportGenerationTransaction(BookDatabase db) {
		this.db = db;
	}

	private List<Book> iterableToList(Iterable<BookBuilder> iter) {
		List<Book> result = new LinkedList<>();
		for (BookBuilder i : iter) {
			result.add(i.build());
		}
		return result;
	}

	@Override
	public void execute() {
		ReportBuilder rb = new ReportBuilder();
		allBooks = iterableToList(db.findAll());
		rb.setTotal(genTotal());
		rb.setAuthors(genAuthors());
		rb.setAvailable(calculateAvailProp());
		rb.setCategories(genCategoryCount());
		rb.setLanguages(genLangCount());
		rb.setPublishDates(genDates());
		rb.setGenreOverview(genGenreOverview());
		this.setResult(rb.build());
	}

	private int genTotal() {
		return allBooks.size();
	}

	private int genAuthors() {
		Set<String> authors = new HashSet<>();
		for (Book i : allBooks) {
			authors.add(i.getAuthor());
		}
		return authors.size();
	}

	private BigDecimal calculateAvailProp() {
		if (allBooks.size() == 0) {
			return new BigDecimal(100);
		}
		int numAvailable = 0;
		for (Book i : allBooks) {
			if (i.isAvailable()) {
				numAvailable++;
			}
		}
		BigDecimal avail = new BigDecimal(numAvailable);
		return avail.divide(new BigDecimal(allBooks.size()), 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100),
				new MathContext(4, RoundingMode.HALF_UP));
	}

	private int genCategoryCount() {
		Set<String> category = new HashSet<>();
		for (Book i : allBooks) {
			category.add(i.getGenre());
		}
		return category.size();
	}

	private int genLangCount() {
		Set<String> langs = new HashSet<>();
		for (Book i : allBooks) {
			langs.add(i.getLang());
		}
		return langs.size();
	}

	private List<Date> genDates() {
		List<Date> result = new LinkedList<>();
		for (Book i : allBooks) {
			result.add(i.getPublished());
		}
		return result;
	}

	private Map<String, Integer> genGenreOverview() {
		Map<String, Integer> result = new HashMap<>();
		for (Book i : allBooks) {
			if (result.containsKey(i.getGenre())) {
				result.put(i.getGenre(), result.get(i.getGenre()) + 1);
			} else {
				result.put(i.getGenre(), 1);
			}
		}
		return result;
	}

}
