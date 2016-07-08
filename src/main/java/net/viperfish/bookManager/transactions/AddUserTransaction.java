package net.viperfish.bookManager.transactions;

import org.springframework.security.crypto.bcrypt.BCrypt;

import net.viperfish.bookManager.core.TransactionWithResult;
import net.viperfish.bookManager.core.UserPrincipal;
import net.viperfish.bookManager.core.UserPrincipalDatabase;

final class AddUserTransaction extends TransactionWithResult<UserPrincipal> {

	private UserPrincipalDatabase db;

	private UserPrincipal toAdd;

	public AddUserTransaction(UserPrincipal toAdd, UserPrincipalDatabase db) {
		this.toAdd = toAdd;
		this.db = db;
	}

	@Override
	public void execute() {
		toAdd.setPassword(BCrypt.hashpw(toAdd.getPassword(), BCrypt.gensalt()));
		this.setResult(db.save(toAdd));
	}

}
