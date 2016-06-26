package net.viperfish.bookManager.transactions;

import net.viperfish.bookManager.core.Book;
import net.viperfish.bookManager.core.BookBuilder;
import net.viperfish.bookManager.core.BookDatabase;
import net.viperfish.bookManager.core.TransactionWithResult;

class EditBookTransaction extends TransactionWithResult<Book> {

	private Long id;
	private Book toMod;
	private BookDatabase db;

	public EditBookTransaction(Long id, Book newBook, BookDatabase db) {
		this.id = id;
		this.toMod = newBook;
		this.db = db;
	}

	@Override
	public void execute() {
		boolean contain = db.exists(id);
		if (!contain) {
			this.setResult(null);
			return;
		}
		BookBuilder toUpdate = new BookBuilder(toMod);
		toUpdate.setId(id);
		toUpdate = db.save(toUpdate);
		this.setResult(toUpdate.build());
	}

}
