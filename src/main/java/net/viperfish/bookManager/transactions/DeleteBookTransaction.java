package net.viperfish.bookManager.transactions;

import net.viperfish.bookManager.core.Book;
import net.viperfish.bookManager.core.BookBuilder;
import net.viperfish.bookManager.core.BookDatabase;
import net.viperfish.bookManager.core.TransactionWithResult;

class DeleteBookTransaction extends TransactionWithResult<Book> {

	private BookDatabase db;
	private Long id;

	public DeleteBookTransaction(Long id, BookDatabase db) {
		this.id = id;
		this.db = db;
	}

	@Override
	public void execute() {
		BookBuilder deleted = db.findOne(id);
		db.delete(id);
		this.setResult(deleted.build());
	}

}
