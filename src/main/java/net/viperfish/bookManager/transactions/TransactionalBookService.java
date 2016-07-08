package net.viperfish.bookManager.transactions;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import net.viperfish.bookManager.core.Book;
import net.viperfish.bookManager.core.BookDatabase;
import net.viperfish.bookManager.core.BookService;
import net.viperfish.bookManager.core.Report;

@Service("transactionalBookService")
class TransactionalBookService implements BookService {

	@Autowired
	private TransactionalUtils utils;

	private BookDatabase db;

	public TransactionalBookService() {
	}

	@Autowired
	public void setDB(BookDatabase db) {
		this.db = db;
	}

	private OwnerAwareBookDatabase ownerDB() {
		return new OwnerAwareBookDatabase(db);
	}

	@PreAuthorize("hasAuthority('user')")
	@Override
	public Book get(Long id) throws ExecutionException {
		GetTransaction<Book, Long> trans = new GetTransaction<Book, Long>(id, ownerDB());
		return utils.transactionToResult(trans);
	}

	@PreAuthorize("hasAuthority('user')")
	@Override
	public Book add(Book t) throws ExecutionException {
		PersistTransaction<Book> trans = new PersistTransaction<Book>(t, ownerDB());
		return utils.transactionToResult(trans);
	}

	@PreAuthorize("hasAuthority('user')")
	@Override
	public Book remove(Long id) throws ExecutionException {
		RemoveTransaction<Book, Long> trans = new RemoveTransaction<Book, Long>(id, ownerDB());
		return utils.transactionToResult(trans);
	}

	@PreAuthorize("hasAuthority('user')")
	@Override
	public Book update(Long id, Book updated) throws ExecutionException {
		updated.setId(id);
		PersistTransaction<Book> trans = new PersistTransaction<Book>(updated, ownerDB());
		return utils.transactionToResult(trans);
	}

	@PreAuthorize("hasAuthority('user')")
	@Override
	public Page<Book> getAll(Pageable page) throws ExecutionException {
		GetAllPagedTransaction<Book> trans = new GetAllPagedTransaction<>(page, ownerDB());
		return utils.transactionToResult(trans);
	}

	@PreAuthorize("hasAuthority('user')")
	@Override
	public Collection<Book> getAll() throws ExecutionException {
		GetAllTransaction<Book> trans = new GetAllTransaction<>(ownerDB());
		return utils.transactionToResult(trans);
	}

	@PreAuthorize("hasAuthority('user')")
	@Override
	public Page<Book> search(String query, Pageable paging) throws ExecutionException {
		PagedSearchTransaction<Book> trans = new PagedSearchTransaction<>(query, paging, ownerDB());
		return utils.transactionToResult(trans);
	}

	@PreAuthorize("hasAuthority('user')")
	@Override
	public Report generateReport() throws ExecutionException {
		ReportGenerationTransaction trans = new ReportGenerationTransaction(ownerDB());
		return utils.transactionToResult(trans);
	}

}
