package net.viperfish.bookManager.core;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserService extends UserDetailsService {

}
