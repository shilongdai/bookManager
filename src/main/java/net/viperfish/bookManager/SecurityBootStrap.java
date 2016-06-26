package net.viperfish.bookManager;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Order(1)
public class SecurityBootStrap extends AbstractSecurityWebApplicationInitializer {

	public SecurityBootStrap() {
		// TODO Auto-generated constructor stub
	}

	public SecurityBootStrap(Class<?>... configurationClasses) {
		super(configurationClasses);
	}

	@Override
	protected boolean enableHttpSessionEventPublisher() {
		return true;
	}

}
