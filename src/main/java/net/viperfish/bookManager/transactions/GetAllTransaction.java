package net.viperfish.bookManager.transactions;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import net.viperfish.bookManager.core.TransactionWithResult;

class GetAllTransaction<T> extends TransactionWithResult<Collection<T>> {

	private CrudRepository<T, ? extends Serializable> db;

	public GetAllTransaction(CrudRepository<T, ? extends Serializable> repo) {
		this.db = repo;
	}

	@Override
	public void execute() {
		List<T> result = new LinkedList<>();
		for (T i : db.findAll()) {
			result.add(i);
		}
		this.setResult(result);
	}

}
