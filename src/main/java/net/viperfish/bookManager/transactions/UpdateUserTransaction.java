package net.viperfish.bookManager.transactions;

import org.springframework.security.crypto.bcrypt.BCrypt;

import net.viperfish.bookManager.core.TransactionWithResult;
import net.viperfish.bookManager.core.UserPrincipal;
import net.viperfish.bookManager.core.UserPrincipalDatabase;

final class UpdateUserTransaction extends TransactionWithResult<UserPrincipal> {

	private UserPrincipalDatabase db;
	private Long id;
	private UserPrincipal toUpdate;
	private boolean updatePassword;

	UpdateUserTransaction(UserPrincipalDatabase db, Long id, UserPrincipal toUpdate, boolean updatePassword) {
		this.db = db;
		this.id = id;
		this.toUpdate = toUpdate;
		this.updatePassword = updatePassword;
	}

	@Override
	public void execute() {
		if (!db.exists(id)) {
			this.setResult(null);
			return;
		}
		toUpdate.setId(id);
		if (updatePassword) {
			toUpdate.setPassword(BCrypt.hashpw(toUpdate.getPassword(), BCrypt.gensalt()));
		}
		UserPrincipal updated = db.save(toUpdate);
		this.setResult(updated);

	}

}
