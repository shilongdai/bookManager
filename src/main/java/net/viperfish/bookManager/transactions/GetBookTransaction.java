package net.viperfish.bookManager.transactions;

import net.viperfish.bookManager.core.Book;
import net.viperfish.bookManager.core.BookDatabase;
import net.viperfish.bookManager.core.TransactionWithResult;

class GetBookTransaction extends TransactionWithResult<Book> {

	private BookDatabase db;
	private Long id;

	public GetBookTransaction(Long id, BookDatabase db) {
		this.id = id;
		this.db = db;
	}

	@Override
	public void execute() {
		this.setResult(db.findOne(id).build());
	}

}
