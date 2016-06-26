package net.viperfish.bookManager.view;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import net.viperfish.bookManager.core.BookBuilder;
import net.viperfish.bookManager.core.Genres;
import net.viperfish.bookManager.core.TransactionWithResult;
import net.viperfish.bookManager.core.UserPrincipal;
import net.viperfish.bookManager.transactions.TransactionManager;

@Controller
@RequestMapping(value = "book")
public class BookController {

	@Autowired
	private TransactionManager transMger;

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

	private void fillInModelAdd(Map<String, Object> model, BookBuilder toAdd) {
		model.put("genre", genres.values());
		model.put("book", toAdd);
		model.put("target", "/book");
		model.put("type", "post");
	}

	private void fillInModelEdit(Map<String, Object> model, BookBuilder form, Long id) {
		model.put("genre", genres.values());
		model.put("book", form);
		model.put("target", "/book/" + id);
		model.put("type", "put");
	}

	private void setDefaultParameters(HttpSession session) {
		session.setAttribute("prevQuery", null);
		session.setAttribute("prevPage", new PageRequest(0, 10));
	}

	private List<Book> buildersToBook(Iterable<BookBuilder> src) {
		List<Book> result = new LinkedList<>();
		for (BookBuilder i : src) {
			result.add(i.build());
		}
		return result;
	}

	private int pageCountFix(int orig) {
		if (orig == 0) {
			return orig + 1;
		}
		return orig;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String allBooks(Map<String, Object> model, HttpSession session, Pageable paging) {
		paging = sortByTitle(paging);
		TransactionWithResult<Page<BookBuilder>> getAll = transMger.getAllTransaction(paging);
		getAll.execute();
		Page<BookBuilder> result = getAll.getResult();
		model.put("books", buildersToBook(result.getContent()));
		model.put("pageCount", pageCountFix(result.getTotalPages()));
		model.put("currentPage", paging.getPageNumber());
		setDefaultParameters(session);
		return "root";
	}

	@RequestMapping(method = RequestMethod.GET, params = { "q", "page", "size" })
	public String keywordSearch(@RequestParam(value = "q") String query, HttpSession session, Map<String, Object> model,
			Pageable paging) {
		paging = sortByTitle(paging);
		session.setAttribute("prevPage", paging);
		TransactionWithResult<Page<BookBuilder>> trans;
		if (query.length() > 0) {
			session.setAttribute("prevQuery", query);
			trans = transMger.getSearchTransaction(query, paging);
		} else {
			trans = transMger.getAllTransaction(paging);
			setDefaultParameters(session);
		}
		trans.execute();

		Page<BookBuilder> result = trans.getResult();

		model.put("books", buildersToBook(result.getContent()));
		model.put("q", query);
		model.put("pageCount", pageCountFix(result.getTotalPages()));
		model.put("currentPage", paging.getPageNumber());
		return "root";
	}

	@RequestMapping(method = RequestMethod.GET, params = { "usePrev", "prevPage" })
	public String prevQueries(Map<String, Object> model, HttpSession session) {
		String query = (String) session.getAttribute("prevQuery");
		Pageable prev = (Pageable) session.getAttribute("prevPage");

		TransactionWithResult<Page<BookBuilder>> filteredTrans;
		if (query == null) {
			filteredTrans = transMger.getAllTransaction(prev);
		} else {
			filteredTrans = transMger.getSearchTransaction(query, prev);
		}

		filteredTrans.execute();
		Page<BookBuilder> result = filteredTrans.getResult();

		model.put("books", buildersToBook(result.getContent()));
		model.put("q", query);
		model.put("pageCount", pageCountFix(result.getTotalPages()));
		model.put("currentPage", prev.getPageNumber());
		return "root";

	}

	@RequestMapping(method = RequestMethod.GET, params = { "usePrev", "page", "size" })
	public String paginationWithPrev(Map<String, Object> model, HttpSession session, Pageable paging) {
		paging = sortByTitle(paging);
		session.setAttribute("prevPage", paging);
		String query = (String) session.getAttribute("prevQuery");
		TransactionWithResult<Page<BookBuilder>> operation;
		if (query == null) {
			operation = transMger.getAllTransaction(paging);
		} else {
			operation = transMger.getSearchTransaction(query, paging);
		}
		operation.execute();
		Page<BookBuilder> result = operation.getResult();

		model.put("books", buildersToBook(result.getContent()));
		model.put("q", query);
		model.put("pageCount", pageCountFix(result.getTotalPages()));
		model.put("currentPage", paging.getPageNumber());
		return "root";

	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView addBook(BookBuilder toAdd, BindingResult errors, Map<String, Object> model) {
		if (errors.getFieldError("bookBuilder.published") != null) {
			log.warn(errors.toString());
			fillInModelAdd(model, toAdd);
			model.put("errors", errors.getFieldErrors());
			return new ModelAndView("addBook");
		}
		try {
			TransactionWithResult<Long> trans = transMger.getAddBookTransaction(toAdd.build());
			trans.execute();
			Long id;
			id = trans.getResult();
			return new ModelAndView(new RedirectView("/book/" + id, true));
		} catch (ConstraintViolationException e) {
			fillInModelAdd(model, toAdd);
			model.put("errors", constraints2String((e.getConstraintViolations())));
			return new ModelAndView("addBook");
		}

	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String addBookPage(Map<String, Object> model, @AuthenticationPrincipal UserPrincipal user) {
		fillInModelAdd(model, new BookBuilder("", "", false, null, null, genres.getDefault(), "", "", "", user, ""));
		return "addBook";
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET, params = { "id" })
	public String editBookPage(Map<String, Object> model, @RequestParam(value = "id", required = true) Long id) {
		TransactionWithResult<Book> getEdit = transMger.getGetTransaction(id);
		getEdit.execute();
		BookBuilder formObject = new BookBuilder(getEdit.getResult());
		fillInModelEdit(model, formObject, id);
		return "addBook";

	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ModelAndView editBook(BookBuilder form, BindingResult errors, Map<String, Object> model,
			@PathVariable(value = "id") Long id) {
		if (errors.getFieldError("bookBuilder.published") != null) {
			log.warn(errors.toString());
			fillInModelEdit(model, form, id);
			model.put("errors", errors.getFieldErrors());
			return new ModelAndView("addBook");
		}
		try {
			TransactionWithResult<Book> transaction = transMger.getModifyTransaction(id, form.build());
			transaction.execute();

			Book result;
			result = transaction.getResult();
			if (result == null) {
				throw new IllegalArgumentException("Invalid book to modify:" + form.build().toString());
			}
			return new ModelAndView(new RedirectView("/book/" + result.getId(), true));
		} catch (ConstraintViolationException e) {
			fillInModelEdit(model, form, id);
			model.put("errors", constraints2String((e.getConstraintViolations())));
			return new ModelAndView("addBook");
		}
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public View deleteBook(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		TransactionWithResult<Book> deleteBook = transMger.getDeleteTransaction(id);
		deleteBook.execute();
		deleteBook.getResult();
		model.put("usePrev", true);
		model.put("prevPage", true);
		return new RedirectView("/book", true);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public String viewBook(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		TransactionWithResult<Book> getBook = transMger.getGetTransaction(id);
		getBook.execute();
		Book result = getBook.getResult();
		if (result == null) {
			throw new BookNotFound(id.toString());
		}
		BookBuilder temp = new BookBuilder(result);
		result = temp.setDescription(StringEscapeUtils.escapeEcmaScript(temp.getDescription())).build();
		model.put("book", result);
		return "viewBook";
	}

	@ExceptionHandler(BookNotFound.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleBookNotFound() {
		return "404";
	}

}
