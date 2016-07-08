package net.viperfish.bookManager.transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.viperfish.bookManager.core.UserPrincipal;
import net.viperfish.bookManager.core.UserPrincipalDatabase;
import net.viperfish.bookManager.core.UserService;

@Service
class JPAUserService implements UserService {

	@Autowired
	private UserPrincipalDatabase db;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserPrincipal user = db.findByUsername(username);
		user.getAuthorities().size();
		user.getPassword();
		return user;
	}

}
