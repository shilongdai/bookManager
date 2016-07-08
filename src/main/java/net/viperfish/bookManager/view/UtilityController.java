package net.viperfish.bookManager.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import net.viperfish.bookManager.core.Book;
import net.viperfish.bookManager.core.BookDatabase;

@Controller
@RequestMapping(value = "/util")
public class UtilityController {

	@Autowired
	private BookDatabase db;

	public UtilityController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(value = "rebuildIndex", method = RequestMethod.GET)
	public View rebuildIndex() {
		Iterable<Book> all = db.findAll();
		db.deleteAll();
		db.save(all);
		return new RedirectView("/book", true);
	}

	@RequestMapping(value = "updateGenres", method = RequestMethod.GET)
	public View updateGenres() {
		Iterable<Book> all = db.findAll();
		for (Book i : all) {
			i.setGenre(i.getGenre().replace('_', ' '));
			if (i.getGenre().equalsIgnoreCase("SelfHelp_Book")) {
				i.setGenre("Self Help Book");
			}
		}
		db.deleteAll();
		db.save(all);
		return new RedirectView("/book", true);
	}

}
