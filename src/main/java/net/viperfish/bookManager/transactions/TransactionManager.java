package net.viperfish.bookManager.transactions;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import net.viperfish.bookManager.core.Book;
import net.viperfish.bookManager.core.BookBuilder;
import net.viperfish.bookManager.core.BookDatabase;
import net.viperfish.bookManager.core.Report;
import net.viperfish.bookManager.core.TransactionExecutor;
import net.viperfish.bookManager.core.TransactionWithResult;
import net.viperfish.bookManager.core.UserPrincipal;
import net.viperfish.bookManager.core.UserPrincipalDatabase;

@Service
@Validated
public class TransactionManager {
	private BookDatabase db;
	private UserPrincipalDatabase users;
	private static TransactionExecutor pool;
	private static Logger logger;

	@Autowired
	public void setDatabase(BookDatabase db) {
		this.db = db;
	}

	@Autowired
	public void setUsers(UserPrincipalDatabase db) {
		this.users = db;
	}

	@Autowired
	public void setPool(TransactionExecutor exec) {
		TransactionManager.pool = exec;
	}

	static class QueuedAsyncTransactionWithResult<T> extends TransactionWithResult<T> {

		private TransactionWithResult<? extends T> trans;
		private Future<? extends T> result;

		public QueuedAsyncTransactionWithResult(TransactionWithResult<? extends T> toExecute) {
			this.trans = toExecute;
		}

		@Override
		public void execute() {
			result = pool.call(trans);
			logger.info("Submitting " + trans.getClass().getName());
		}

		@Override
		public boolean isDone() {
			return result.isDone();
		}

		@Override
		public T getResult() {
			try {
				return result.get();
			} catch (InterruptedException e) {
				return null;
			} catch (ExecutionException e) {
				throw new RuntimeException(e.getCause());
			}
		}

	}

	public TransactionManager() {
	}

	static {
		logger = LogManager.getLogger();
	}

	@PreAuthorize("hasAuthority('user')")
	public TransactionWithResult<Long> getAddBookTransaction(@Valid Book toAdd) {
		return new QueuedAsyncTransactionWithResult<>(new AddBookTransaction(toAdd, new OwnerAwareBookDatabase(db)));
	}

	@PreAuthorize("hasAuthority('user')")
	public TransactionWithResult<Page<BookBuilder>> getAllTransaction(@NotNull Pageable paging) {
		return new QueuedAsyncTransactionWithResult<>(new GetAllTransaction(paging, new OwnerAwareBookDatabase(db)));
	}

	@PreAuthorize("hasAuthority('user')")
	public TransactionWithResult<Book> getGetTransaction(@NotNull Long id) {
		return new QueuedAsyncTransactionWithResult<>(new GetBookTransaction(id, new OwnerAwareBookDatabase(db)));
	}

	@PreAuthorize("hasAuthority('user')")
	public TransactionWithResult<Book> getDeleteTransaction(@NotNull Long id) {
		return new QueuedAsyncTransactionWithResult<>(new DeleteBookTransaction(id, new OwnerAwareBookDatabase(db)));
	}

	@PreAuthorize("hasAuthority('user')")
	public TransactionWithResult<Book> getModifyTransaction(@NotNull Long id, @Valid Book newBook) {
		return new QueuedAsyncTransactionWithResult<>(
				new EditBookTransaction(id, newBook, new OwnerAwareBookDatabase(db)));
	}

	@PreAuthorize("hasAuthority('user')")
	public TransactionWithResult<Report> getReportGenTransaction() {
		return new QueuedAsyncTransactionWithResult<>(new ReportGenerationTransaction(new OwnerAwareBookDatabase(db)));
	}

	@PreAuthorize("hasAuthority('user')")
	public TransactionWithResult<Page<BookBuilder>> getSearchTransaction(@NotNull String query,
			@NotNull Pageable paging) {
		return new QueuedAsyncTransactionWithResult<>(
				new SearchBooksTransaction(query, paging, new OwnerAwareBookDatabase(db)));
	}

	@PreAuthorize("hasAuthority('admin')")
	public TransactionWithResult<UserPrincipal> createAdmin(@Valid UserPrincipal toCreate) {
		return new QueuedAsyncTransactionWithResult<>(new CreateRootUserTransaction(toCreate, users));
	}

	public TransactionWithResult<UserPrincipal> createUser(@Valid UserPrincipal toCreate) {
		return new QueuedAsyncTransactionWithResult<>(new CreateRegularUserTransaction(toCreate, users));
	}

	@PreAuthorize("#id == principal.id or hasAuthority('admin')")
	public TransactionWithResult<UserPrincipal> getUserTransaction(@P("id") Long id) {
		return new QueuedAsyncTransactionWithResult<>(new GetTransaction<UserPrincipal, Long>(id, users));
	}

	@PreAuthorize("#id == principal.id or hasAuthority('admin')")
	public TransactionWithResult<UserPrincipal> deleteUserTransaction(@P("id") Long id) {
		return new QueuedAsyncTransactionWithResult<>(new DeleteTransaction<UserPrincipal, Long>(id, users));
	}

	@PreAuthorize("#id == principal.id or hasAuthority('admin')")
	public TransactionWithResult<UserPrincipal> updateUserTransaction(@P("id") Long id, UserPrincipal update,
			boolean updatePass) {
		return new QueuedAsyncTransactionWithResult<>(new UpdateUserTransaction(users, id, update, updatePass));
	}

	@PreAuthorize("hasAuthority('admin')")
	public TransactionWithResult<Page<UserPrincipal>> allUserTransaction(Pageable paging) {
		return new QueuedAsyncTransactionWithResult<>(new GetAllUserTransaction(paging, users));
	}

}
