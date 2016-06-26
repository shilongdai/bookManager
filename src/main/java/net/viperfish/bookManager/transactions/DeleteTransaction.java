package net.viperfish.bookManager.transactions;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

import net.viperfish.bookManager.core.TransactionWithResult;

final class DeleteTransaction<T, ID extends Serializable> extends TransactionWithResult<T> {

	private CrudRepository<? extends T, ID> db;
	private ID id;

	public DeleteTransaction(ID id, CrudRepository<? extends T, ID> db) {
		this.db = db;
		this.id = id;
	}

	@Override
	public void execute() {
		T toDel = db.findOne(id);
		db.delete(id);
		this.setResult(toDel);
	}

}
