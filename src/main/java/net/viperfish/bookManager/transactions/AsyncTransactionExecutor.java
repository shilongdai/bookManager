package net.viperfish.bookManager.transactions;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import net.viperfish.bookManager.core.Transaction;
import net.viperfish.bookManager.core.TransactionExecutor;
import net.viperfish.bookManager.core.TransactionWithResult;

@Service
public class AsyncTransactionExecutor implements TransactionExecutor {

	public AsyncTransactionExecutor() {
	}

	@Override
	@Async
	public void run(Transaction transac) {
		transac.execute();
	}

	@Override
	@Async
	public <T> Future<T> call(TransactionWithResult<T> trans) {
		trans.execute();
		return new AsyncResult<T>(trans.getResult());
	}

}
