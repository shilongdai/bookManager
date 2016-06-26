package net.viperfish.bookManager.transactions;

import net.viperfish.bookManager.core.Book;
import net.viperfish.bookManager.core.BookBuilder;
import net.viperfish.bookManager.core.BookDatabase;
import net.viperfish.bookManager.core.TransactionWithResult;

class AddBookTransaction extends TransactionWithResult<Long> {

	private Book toAdd;
	private BookDatabase db;

	AddBookTransaction(Book book, BookDatabase db) {
		this.toAdd = book;
		this.db = db;
	}

	@Override
	public void execute() {
		BookBuilder added = this.db.save(new BookBuilder(toAdd));
		this.setResult(added.getId());

	}

}
