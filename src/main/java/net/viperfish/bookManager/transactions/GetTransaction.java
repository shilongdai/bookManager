package net.viperfish.bookManager.transactions;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

import net.viperfish.bookManager.core.TransactionWithResult;

class GetTransaction<T, ID extends Serializable> extends TransactionWithResult<T> {

	private CrudRepository<T, ID> db;

	private ID id;

	public GetTransaction(ID id, CrudRepository<T, ID> repo) {
		this.db = repo;
		this.id = id;
	}

	@Override
	public void execute() {
		this.setResult(db.findOne(id));
	}

}
