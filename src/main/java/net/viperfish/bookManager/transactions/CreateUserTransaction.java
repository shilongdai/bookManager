package net.viperfish.bookManager.transactions;

import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCrypt;

import net.viperfish.bookManager.core.TransactionWithResult;
import net.viperfish.bookManager.core.UserAuthority;
import net.viperfish.bookManager.core.UserExistException;
import net.viperfish.bookManager.core.UserPrincipal;
import net.viperfish.bookManager.core.UserPrincipalDatabase;

abstract class CreateUserTransaction extends TransactionWithResult<UserPrincipal> {

	private UserPrincipalDatabase db;
	private UserPrincipal toCreate;

	public CreateUserTransaction(UserPrincipal toCreate, UserPrincipalDatabase db) {
		this.db = db;
		this.toCreate = toCreate;
	}

	protected abstract void roles(Set<UserAuthority> roles);

	@Override
	public void execute() {
		UserPrincipal exist = db.findByUsername(toCreate.getUsername());
		if (exist != null) {
			throw new UserExistException();
		}
		String bcryptPassword = BCrypt.hashpw(toCreate.getPassword(), BCrypt.gensalt());
		toCreate.setPassword(bcryptPassword);
		this.roles(toCreate.getAuthorities());
		this.setResult(db.save(toCreate));
	}

}
