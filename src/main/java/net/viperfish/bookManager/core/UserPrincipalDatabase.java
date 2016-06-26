package net.viperfish.bookManager.core;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserPrincipalDatabase
		extends CrudRepository<UserPrincipal, Long>, PagingAndSortingRepository<UserPrincipal, Long> {

	public UserPrincipal findByUsername(String username);

}
