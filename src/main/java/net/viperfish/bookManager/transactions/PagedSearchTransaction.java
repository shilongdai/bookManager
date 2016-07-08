package net.viperfish.bookManager.transactions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.viperfish.bookManager.core.SearchableDatabase;
import net.viperfish.bookManager.core.TransactionWithResult;

class PagedSearchTransaction<T> extends TransactionWithResult<Page<T>> {

	private String query;
	private Pageable paging;

	private SearchableDatabase<T> db;

	PagedSearchTransaction(String query, Pageable paging, SearchableDatabase<T> db) {
		super();
		this.query = query;
		this.paging = paging;
		this.db = db;
	}

	@Override
	public void execute() {
		this.setResult(db.search(query, paging));
	}

}
