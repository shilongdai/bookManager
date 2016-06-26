package net.viperfish.bookManager.transactions;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

import net.viperfish.bookManager.core.TransactionWithResult;

final class UpdateTransaction<T, ID extends Serializable> extends TransactionWithResult<T> {

	private CrudRepository<T, ID> db;
	private ID id;
	private T toUpdate;

	public UpdateTransaction(ID id, T toUpdate, CrudRepository<T, ID> db) {
		this.id = id;
		this.db = db;
		this.toUpdate = toUpdate;
	}

	@Override
	public void execute() {
		if (!db.exists(id)) {
			this.setResult(null);
			return;
		}

		toUpdate = db.save(toUpdate);
		this.setResult(toUpdate);
	}

}
