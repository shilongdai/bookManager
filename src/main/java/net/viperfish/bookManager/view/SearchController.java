package net.viperfish.bookManager.view;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.viperfish.bookManager.core.Book;

@RequestMapping(value = "/advancedSearch")
@Controller
public class SearchController {

	public SearchController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(method = RequestMethod.GET)
	public String advancedSearchPage(Map<String, Object> model) {
		List<Book> books = new LinkedList<>();
		model.put("currentPage", 0);
		model.put("pageNumber", 1);
		model.put("books", books);
		return "advancedSearch";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String searchAdvancedForm(@RequestParam(value = "q", required = true) List<String> queries,
			@RequestParam(value = "author", required = false) List<String> authors,
			@RequestParam(value = "language", required = false) List<String> languages,
			@RequestParam(value = "lowerBound", required = false) List<Date> lowerBounds,
			@RequestParam(value = "upperBound", required = false) List<Date> upperBounds, boolean availability,
			@RequestParam(value = "isbn", required = false) List<String> isbns) {

		return "advancedSearch";
	}

}
