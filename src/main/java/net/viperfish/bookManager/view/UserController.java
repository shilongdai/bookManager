package net.viperfish.bookManager.view;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import net.viperfish.bookManager.core.UserExistException;
import net.viperfish.bookManager.core.UserPrincipal;
import net.viperfish.bookManager.core.UserPrincipalService;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserPrincipalService userService;

	@Autowired
	private MessageSource i18n;

	private Logger logger;

	public UserController() {
		this.logger = LogManager.getLogger();
	}

	private int pageCountFix(int orig) {
		if (orig == 0) {
			return orig + 1;
		}
		return orig;
	}

	private static enum Type {
		USER, ADMIN;
	}

	private void addUserModel(Map<String, Object> model, UserPrincipal form, Type t) {
		model.put("method", "post");
		if (t == Type.USER) {
			model.put("target", "/user/newUser");
		}
		if (t == Type.ADMIN) {
			model.put("target", "/user/newAdmin");
		}
		model.put("form", form);
		model.put("canEditUsername", true);
	}

	private void editUserModel(Map<String, Object> model, UserPrincipal form) {
		model.put("method", "put");
		model.put("target", "/user/" + form.getId());
		model.put("form", form);
		model.put("canEditUsername", false);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String userPage(Map<String, Object> model, Pageable paging) throws ExecutionException {
		Page<UserPrincipal> result = userService.getAll(paging);
		model.put("currentPage", paging.getPageNumber());
		model.put("users", result.getContent());
		model.put("pageCount", pageCountFix(result.getTotalPages()));
		return "userManager";
	}

	@RequestMapping(value = "newUser", method = RequestMethod.GET)
	public String createUserPage(Map<String, Object> model) {
		addUserModel(model, new UserPrincipal(), Type.USER);
		return "userForm";
	}

	@RequestMapping(value = "newUser", method = RequestMethod.POST)
	public ModelAndView createUser(@Valid UserPrincipal newUser, BindingResult error, Map<String, Object> model)
			throws ExecutionException {
		if (error.hasErrors()) {
			List<String> messages = Utils.INSTANCE.fieldErrors2String(error.getFieldErrors());
			model.put("errors", messages);
			addUserModel(model, newUser, Type.USER);
			return new ModelAndView("userForm");
		}
		try {
			userService.addUser(newUser);
		} catch (RuntimeException e) {
			if (e.getCause() instanceof UserExistException) {
				List<String> errors = new LinkedList<>();
				errors.add(i18n.getMessage("user.exists", null, LocaleContextHolder.getLocale()));
				model.put("errors", errors);
				addUserModel(model, newUser, Type.USER);
				return new ModelAndView("userForm");
			} else {
				throw e;
			}
		}
		return new ModelAndView(new RedirectView("/user", true));
	}

	@RequestMapping(value = "newAdmin", method = RequestMethod.GET)
	public String createAdminPage(Map<String, Object> model) {
		addUserModel(model, new UserPrincipal(), Type.ADMIN);
		return "userForm";
	}

	@RequestMapping(value = "newAdmin", method = RequestMethod.POST)
	public ModelAndView createAdmin(@Valid UserPrincipal newUser, BindingResult error, Map<String, Object> model)
			throws ExecutionException {
		if (error.hasErrors()) {
			List<String> messages = Utils.INSTANCE.fieldErrors2String(error.getFieldErrors());
			model.put("errors", messages);
			addUserModel(model, newUser, Type.ADMIN);
			return new ModelAndView("userForm");
		}
		try {
			userService.addAdmin(newUser);
		} catch (RuntimeException e) {
			if (e.getCause() instanceof UserExistException) {
				List<String> errors = new LinkedList<>();
				errors.add(i18n.getMessage("user.exists", null, LocaleContextHolder.getLocale()));
				model.put("errors", errors);
				addUserModel(model, newUser, Type.ADMIN);
				return new ModelAndView("userForm");
			} else {
				throw e;
			}
		}
		return new ModelAndView(new RedirectView("/user", true));
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public View deleteUser(@PathVariable("id") Long id) throws ExecutionException {
		userService.remove(id);
		return new RedirectView("/user", true);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public String editUserPage(Map<String, Object> model, @PathVariable("id") Long id) throws ExecutionException {
		editUserModel(model, userService.get(id));
		return "userForm";
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ModelAndView editUser(@PathVariable("id") Long id, @Valid UserPrincipal edit, BindingResult errors,
			Map<String, Object> model) throws ExecutionException {
		boolean updatePass = edit.getPassword().length() != 0;
		if (errors.hasErrors() && updatePass) {
			List<String> messages = Utils.INSTANCE.fieldErrors2String(errors.getFieldErrors());
			model.put("errors", messages);
			editUserModel(model, edit);
			return new ModelAndView("userForm");
		}

		UserPrincipal original = userService.get(id);
		edit.setAuthorities(original.getAuthorities());
		if (!updatePass) {
			edit.setPassword(original.getPassword());
			userService.shallowUpdateUser(id, edit);
		} else {
			userService.update(id, edit);
		}
		return new ModelAndView(new RedirectView("/user", true));
	}

}
