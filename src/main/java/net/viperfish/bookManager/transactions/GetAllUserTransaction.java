package net.viperfish.bookManager.transactions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.viperfish.bookManager.core.TransactionWithResult;
import net.viperfish.bookManager.core.UserPrincipal;
import net.viperfish.bookManager.core.UserPrincipalDatabase;

final class GetAllUserTransaction extends TransactionWithResult<Page<UserPrincipal>> {

	private UserPrincipalDatabase db;
	private Pageable page;

	public GetAllUserTransaction(Pageable paging, UserPrincipalDatabase db) {
		this.db = db;
		this.page = paging;
	}

	@Override
	public void execute() {
		this.setResult(db.findAll(page));
	}

}
