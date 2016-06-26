package net.viperfish.bookManager.transactions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.viperfish.bookManager.core.BookBuilder;
import net.viperfish.bookManager.core.BookDatabase;
import net.viperfish.bookManager.core.TransactionWithResult;

class GetAllTransaction extends TransactionWithResult<Page<BookBuilder>> {

	private BookDatabase db;
	private Pageable paging;

	public GetAllTransaction(Pageable paging, BookDatabase db) {
		this.db = db;
		this.paging = paging;
	}

	@Override
	public void execute() {
		this.setResult(db.findAll(paging));
	}

}
