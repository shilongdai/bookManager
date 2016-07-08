package net.viperfish.bookManager.transactions;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import net.viperfish.bookManager.core.UserAuthority;
import net.viperfish.bookManager.core.UserPrincipal;
import net.viperfish.bookManager.core.UserPrincipalDatabase;
import net.viperfish.bookManager.core.UserPrincipalService;

@Service("transactionalUserPrincipalService")
class TransactionalUserPrincipalService implements UserPrincipalService {

	@Autowired
	private UserPrincipalDatabase db;

	@Autowired
	private TransactionalUtils utils;

	@PreAuthorize("#id == principal.id or hasAuthority('admin')")
	@Override
	public UserPrincipal get(@P("id") Long id) throws ExecutionException {
		GetTransaction<UserPrincipal, Long> trans = new GetTransaction<UserPrincipal, Long>(id, db);
		return utils.transactionToResult(trans);
	}

	@PreAuthorize("hasAuthority('admin')")
	@Override
	public UserPrincipal add(UserPrincipal t) throws ExecutionException {
		AddUserTransaction trans = new AddUserTransaction(t, db);
		return utils.transactionToResult(trans);
	}

	@PreAuthorize("hasAuthority('admin')")
	@Override
	public UserPrincipal remove(Long id) throws ExecutionException {
		RemoveTransaction<UserPrincipal, Long> trans = new RemoveTransaction<UserPrincipal, Long>(id, db);
		return utils.transactionToResult(trans);
	}

	@PreAuthorize("#id == principal.id or hasAuthority('admin')")
	@Override
	public UserPrincipal update(@P("id") Long id, UserPrincipal updated) throws ExecutionException {
		UpdateUserTransaction trans = new UpdateUserTransaction(db, id, updated, true);
		return utils.transactionToResult(trans);
	}

	@PreAuthorize("hasAuthority('admin')")
	@Override
	public Page<UserPrincipal> getAll(Pageable page) throws ExecutionException {
		GetAllPagedTransaction<UserPrincipal> trans = new GetAllPagedTransaction<>(page, db);
		return utils.transactionToResult(trans);
	}

	@PreAuthorize("hasAuthority('admin')")
	@Override
	public Collection<UserPrincipal> getAll() throws ExecutionException {
		GetAllTransaction<UserPrincipal> trans = new GetAllTransaction<>(db);
		return utils.transactionToResult(trans);
	}

	@PreAuthorize("#id == principal.id or hasAuthority('admin')")
	@Override
	public UserPrincipal shallowUpdateUser(@P("id") Long id, UserPrincipal p) throws ExecutionException {
		UpdateUserTransaction trans = new UpdateUserTransaction(db, id, p, false);
		return utils.transactionToResult(trans);

	}

	@PreAuthorize("hasAuthority('admin') or isAnonymous()")
	@Override
	public UserPrincipal addUser(UserPrincipal user) throws ExecutionException {
		user.getAuthorities().add(new UserAuthority("user"));
		AddUserTransaction trans = new AddUserTransaction(user, db);
		return utils.transactionToResult(trans);
	}

	@PreAuthorize("hasAuthority('admin')")
	@Override
	public UserPrincipal addAdmin(UserPrincipal admin) throws ExecutionException {
		admin.getAuthorities().add(new UserAuthority("admin"));
		admin.getAuthorities().add(new UserAuthority("user"));
		AddUserTransaction trans = new AddUserTransaction(admin, db);
		return utils.transactionToResult(trans);
	}

}
