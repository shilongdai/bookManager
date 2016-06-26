package net.viperfish.bookManager.view;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import net.viperfish.bookManager.core.TransactionWithResult;
import net.viperfish.bookManager.core.UserExistException;
import net.viperfish.bookManager.core.UserPrincipal;
import net.viperfish.bookManager.transactions.TransactionManager;

@Controller
public class RootController {

	@Autowired
	private TransactionManager manager;

	@Autowired
	private MessageSource i18n;

	public RootController() {
	}

	private UserPrincipal formToPrincipal(UserForm form) {
		UserPrincipal toAdd = new UserPrincipal();
		toAdd.setUsername(form.getUsername());
		toAdd.setPassword(form.getPassword());
		toAdd.setAccountNonExpired(true);
		toAdd.setAccountNonLocked(true);
		toAdd.setCredentialsNonExpired(true);
		toAdd.setEnabled(true);
		return toAdd;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public View redirectToBook() {
		return new RedirectView("/book", true);
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerPage(Map<String, Object> model) {
		model.put("form", new UserForm());
		return "signUp";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(UserForm form, BindingResult errors, Map<String, Object> model) {
		if (errors.hasErrors()) {
			List<String> messages = Utils.INSTANCE.fieldErrors2String(errors.getFieldErrors());
			model.put("errors", messages);
			model.put("form", form);
			return new ModelAndView("signUp");
		}
		UserPrincipal newUser = formToPrincipal(form);
		TransactionWithResult<UserPrincipal> addUser = manager.createUser(newUser);
		addUser.execute();
		try {
			addUser.getResult();
		} catch (RuntimeException e) {
			if (e.getCause() instanceof UserExistException) {
				List<String> error = new LinkedList<>();
				error.add(i18n.getMessage("user.exists", null, LocaleContextHolder.getLocale()));
				model.put("errors", error);
				model.put("form", form);
				return new ModelAndView("signUp");
			} else {
				throw e;
			}
		}
		return new ModelAndView(new RedirectView("/login", true));
	}

}
