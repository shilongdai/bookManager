package net.viperfish.bookManager.transactions;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import net.viperfish.bookManager.core.TransactionWithResult;

class GetAllPagedTransaction<T> extends TransactionWithResult<Page<T>> {

	private PagingAndSortingRepository<T, ? extends Serializable> db;
	private Pageable page;

	public GetAllPagedTransaction(Pageable page, PagingAndSortingRepository<T, ? extends Serializable> repo) {
		this.db = repo;
		this.page = page;
	}

	@Override
	public void execute() {
		this.setResult(db.findAll(page));

	}

}
