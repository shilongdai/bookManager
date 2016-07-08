package net.viperfish.bookManager.view;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import net.viperfish.bookManager.core.Book;
import net.viperfish.bookManager.core.BookService;
import net.viperfish.bookManager.core.Genres;
import net.viperfish.bookManager.core.UserPrincipal;

@Controller
@RequestMapping(value = "book")
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private Genres genres;

	private org.apache.logging.log4j.Logger log;

	private Pageable sortByTitle(Pageable def) {
		Sort defaultSort = new Sort(new Sort.Order(Sort.Direction.ASC, "title"));
		PageRequest result = new PageRequest(def.getPageNumber(), def.getPageSize(), defaultSort);
		return result;
	}

	public BookController() {
		log = LogManager.getLogger();
	}

	private List<String> constraints2String(Set<ConstraintViolation<?>> set) {
		List<String> result = new LinkedList<>();
		for (ConstraintViolation<?> e : set) {
			result.add(e.getMessage());
		}
		return result;
	}

	private void fillInModelAdd(Map<String, Object> model, Book toAdd) {
		model.put("genre", genres.values());
		model.put("book", toAdd);
		model.put("target", "/book");
		model.put("type", "post");
	}

	private void fillInModelEdit(Map<String, Object> model, Book form, Long id) {
		model.put("genre", genres.values());
		model.put("book", form);
		model.put("target", "/book/" + id);
		model.put("type", "put");
	}

	private void setDefaultParameters(HttpSession session) {
		session.setAttribute("prevQuery", null);
		session.setAttribute("prevPage", new PageRequest(0, 10));
	}

	private int pageCountFix(int orig) {
		if (orig == 0) {
			return orig + 1;
		}
		return orig;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String allBooks(Map<String, Object> model, HttpSession session, Pageable paging) throws ExecutionException {
		paging = sortByTitle(paging);
		Page<Book> result = bookService.getAll(paging);
		model.put("books", result.getContent());
		model.put("pageCount", pageCountFix(result.getTotalPages()));
		model.put("currentPage", paging.getPageNumber());
		setDefaultParameters(session);
		return "root";
	}

	@RequestMapping(method = RequestMethod.GET, params = { "q", "page", "size" })
	public String keywordSearch(@RequestParam(value = "q") String query, HttpSession session, Map<String, Object> model,
			Pageable paging) throws ExecutionException {
		paging = sortByTitle(paging);
		session.setAttribute("prevPage", paging);
		Page<Book> result;
		if (query.length() > 0) {
			session.setAttribute("prevQuery", query);
			result = bookService.search(query, paging);
		} else {
			result = bookService.getAll(paging);
			setDefaultParameters(session);
		}

		model.put("books", result.getContent());
		model.put("q", query);
		model.put("pageCount", pageCountFix(result.getTotalPages()));
		model.put("currentPage", paging.getPageNumber());
		return "root";
	}

	@RequestMapping(method = RequestMethod.GET, params = { "usePrev", "prevPage" })
	public String prevQueries(Map<String, Object> model, HttpSession session) throws ExecutionException {
		String query = (String) session.getAttribute("prevQuery");
		Pageable prev = (Pageable) session.getAttribute("prevPage");
		if (prev == null) {
			prev = new PageRequest(0, 10);
		}
		Page<Book> result;

		prev = sortByTitle(prev);
		if (query == null) {
			result = bookService.getAll(prev);
		} else {
			result = bookService.search(query, prev);
		}

		model.put("books", result.getContent());
		model.put("q", query);
		model.put("pageCount", pageCountFix(result.getTotalPages()));
		model.put("currentPage", prev.getPageNumber());
		return "root";

	}

	@RequestMapping(method = RequestMethod.GET, params = { "usePrev", "page", "size" })
	public String paginationWithPrev(Map<String, Object> model, HttpSession session, Pageable paging)
			throws ExecutionException {
		paging = sortByTitle(paging);
		session.setAttribute("prevPage", paging);
		String query = (String) session.getAttribute("prevQuery");

		Page<Book> result;
		if (query == null) {
			result = bookService.getAll(paging);
		} else {
			result = bookService.search(query, paging);
		}

		model.put("books", result.getContent());
		model.put("q", query);
		model.put("pageCount", pageCountFix(result.getTotalPages()));
		model.put("currentPage", paging.getPageNumber());
		return "root";

	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView addBook(Book toAdd, BindingResult errors, Map<String, Object> model) throws ExecutionException {
		if (errors.getFieldError("bookBuilder.published") != null) {
			log.warn(errors.toString());
			fillInModelAdd(model, toAdd);
			model.put("errors", errors.getFieldErrors());
			return new ModelAndView("addBook");
		}
		try {
			Book result = bookService.add(toAdd);
			return new ModelAndView(new RedirectView("/book/" + result.getId(), true));
		} catch (ConstraintViolationException e) {
			fillInModelAdd(model, toAdd);
			model.put("errors", constraints2String((e.getConstraintViolations())));
			return new ModelAndView("addBook");
		}

	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String addBookPage(Map<String, Object> model, @AuthenticationPrincipal UserPrincipal user) {
		fillInModelAdd(model, new Book("", "", false, null, null, genres.getDefault(), "", "", "", user, ""));
		return "addBook";
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET, params = { "id" })
	public String editBookPage(Map<String, Object> model, @RequestParam(value = "id", required = true) Long id)
			throws ExecutionException {
		Book formObject = new Book(bookService.get(id));
		fillInModelEdit(model, formObject, id);
		return "addBook";

	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ModelAndView editBook(Book form, BindingResult errors, Map<String, Object> model,
			@PathVariable(value = "id") Long id) throws ExecutionException {
		if (errors.getFieldError("bookBuilder.published") != null) {
			log.warn(errors.toString());
			fillInModelEdit(model, form, id);
			model.put("errors", errors.getFieldErrors());
			return new ModelAndView("addBook");
		}
		try {
			Book result = bookService.update(id, form);
			return new ModelAndView(new RedirectView("/book/" + result.getId(), true));
		} catch (ConstraintViolationException e) {
			fillInModelEdit(model, form, id);
			model.put("errors", constraints2String((e.getConstraintViolations())));
			return new ModelAndView("addBook");
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public View deleteBook(@PathVariable(value = "id") Long id, Map<String, Object> model) throws ExecutionException {
		bookService.remove(id);
		model.put("usePrev", true);
		model.put("prevPage", true);
		return new RedirectView("/book", true);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public String viewBook(@PathVariable(value = "id") Long id, Map<String, Object> model) throws ExecutionException {
		Book result = bookService.get(id);
		if (result == null) {
			throw new BookNotFound(id.toString());
		}
		Book temp = new Book(result);
		result = temp.setDescription(StringEscapeUtils.escapeEcmaScript(temp.getDescription()));
		model.put("book", result);
		return "viewBook";
	}

	@ExceptionHandler(BookNotFound.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleBookNotFound() {
		return "404";
	}

}
