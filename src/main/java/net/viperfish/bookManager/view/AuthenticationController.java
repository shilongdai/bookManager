package net.viperfish.bookManager.view;

import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import net.viperfish.bookManager.core.UserPrincipal;

@Controller
public class AuthenticationController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginPage(Map<String, Object> model) {
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
				&& SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserPrincipal) {
			return new ModelAndView(new RedirectView("/book", true));
		}
		model.put("loginForm", new UserForm());
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/login", params = { "error" })
	public String loginError() {
		return "loginError";
	}

}
