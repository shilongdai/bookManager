package net.viperfish.bookManager.transactions;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

import net.viperfish.bookManager.core.TransactionWithResult;

class RemoveTransaction<T, ID extends Serializable> extends TransactionWithResult<T> {

	private CrudRepository<T, ID> repo;

	private ID id;

	public RemoveTransaction(ID id, CrudRepository<T, ID> db) {
		this.id = id;
		this.repo = db;
	}

	@Override
	public void execute() {
		T toDelete = repo.findOne(id);
		if (toDelete != null) {
			repo.delete(id);
		}
		this.setResult(toDelete);
	}

}
