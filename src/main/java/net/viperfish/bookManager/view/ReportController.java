package net.viperfish.bookManager.view;

import java.awt.Color;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.viperfish.bookManager.core.BookService;
import net.viperfish.bookManager.core.Report;

@Controller
@RequestMapping(value = "report")
public final class ReportController {

	private ColourPicker generator;

	private List<String> extractGenresFromEntries(Iterable<Entry<String, Integer>> iterable) {
		List<String> result = new LinkedList<>();
		for (Entry<String, ?> i : iterable) {
			result.add(i.getKey());
		}
		return result;
	}

	private List<Integer> extractNumbersFromEntries(Iterable<Entry<String, Integer>> m) {
		List<Integer> result = new LinkedList<>();
		for (Entry<?, Integer> i : m) {
			result.add(i.getValue());
		}
		return result;
	}

	private List<String> jsColours(int number) {
		List<String> result = new LinkedList<>();
		for (int i = 0; i < number; ++i) {
			Color next = generator.pick();
			StringBuilder builder = new StringBuilder();
			builder.append('"');
			builder.append('#');
			builder.append(Integer.toHexString(next.getRed()));
			builder.append(Integer.toHexString(next.getGreen()));
			builder.append(Integer.toHexString(next.getBlue()));
			builder.append('"');
			result.add(builder.toString());
		}
		this.generator.reset();
		return result;
	}

	@Autowired
	private BookService bookService;

	public ReportController() {
		generator = new ColourPicker();
	}

	private Collection<String> toJsStringArray(Collection<String> original) {
		List<String> result = new LinkedList<>();
		for (String i : original) {
			result.add("\"" + i + "\"");
		}
		return result;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String generateReport(Map<String, Object> model) throws ExecutionException {
		Report r = bookService.generateReport();
		model.put("genres", r.getGenreOverview());
		model.put("genreData", extractNumbersFromEntries(r.getGenreOverview()));
		model.put("genreString", toJsStringArray(extractGenresFromEntries(r.getGenreOverview())));
		model.put("colours", jsColours(r.getCategories()));
		model.put("report", r);
		model.put("langString", toJsStringArray(extractGenresFromEntries(r.getLanguageOverview().entrySet())));
		model.put("langData", extractNumbersFromEntries(r.getLanguageOverview().entrySet()));
		model.put("langColours", jsColours(r.getLanguages()));
		return "reportPage";
	}

}
