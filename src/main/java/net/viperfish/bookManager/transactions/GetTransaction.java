package net.viperfish.bookManager.transactions;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

import net.viperfish.bookManager.core.TransactionWithResult;

final class GetTransaction<T, ID extends Serializable> extends TransactionWithResult<T> {

	private CrudRepository<? extends T, ID> db;
	private ID id;

	public GetTransaction(ID id, CrudRepository<? extends T, ID> db) {
		this.db = db;
		this.id = id;
	}

	@Override
	public void execute() {
		T result = db.findOne(id);
		setResult(result);
	}

}
