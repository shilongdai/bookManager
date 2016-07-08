package net.viperfish.bookManager.transactions;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

import net.viperfish.bookManager.core.TransactionWithResult;

class PersistTransaction<T> extends TransactionWithResult<T> {

	private CrudRepository<T, ? extends Serializable> db;

	private T toSave;

	public PersistTransaction(T data, CrudRepository<T, ? extends Serializable> db) {
		this.toSave = data;
		this.db = db;
	}

	@Override
	public void execute() {
		this.setResult(db.save(toSave));
	}

}
