package net.viperfish.bookManager.transactions;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.viperfish.bookManager.core.BookBuilder;
import net.viperfish.bookManager.core.BookDatabase;
import net.viperfish.bookManager.core.SearchResult;
import net.viperfish.bookManager.core.TransactionWithResult;

class SearchBooksTransaction extends TransactionWithResult<Page<BookBuilder>> {

	private String keyword;
	private BookDatabase db;
	private Pageable paging;

	public SearchBooksTransaction(String keyword, Pageable paging, BookDatabase db) {
		this.keyword = keyword;
		this.db = db;
		this.paging = paging;
	}

	@Override
	public void execute() {
		Page<SearchResult<Long>> result = db.search(keyword, paging);
		List<Long> buffer = new LinkedList<>();
		for (SearchResult<Long> i : result.getContent()) {
			buffer.add(i.getResult());
		}
		setResult(db.findByIdIn(buffer, paging));
	}

}
