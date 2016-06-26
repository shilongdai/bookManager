package net.viperfish.bookManager.core;

import java.util.concurrent.Future;

public interface TransactionExecutor {
	public void run(Transaction trans);

	public <T> Future<T> call(TransactionWithResult<T> trans);
}
