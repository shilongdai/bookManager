package net.viperfish.bookManager.transactions;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;

import net.viperfish.bookManager.core.BookBuilder;
import net.viperfish.bookManager.core.BookDatabase;
import net.viperfish.bookManager.core.SearchResult;
import net.viperfish.bookManager.core.UserAuthority;
import net.viperfish.bookManager.core.UserPrincipal;

final class OwnerAwareBookDatabase implements BookDatabase {

	private BookDatabase db;
	private UserPrincipal owner;
	private static UserAuthority root = new UserAuthority("admin");

	public OwnerAwareBookDatabase(BookDatabase db) {
		this.db = db;
		owner = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	private UserPrincipal owner() {
		return owner;
	}

	private boolean isAdmin() {
		return owner().getAuthorities().contains(root);
	}

	@Override
	public <S extends BookBuilder> S save(S entity) {
		entity.setOwner(owner());
		return db.save(entity);
	}

	@Override
	public <S extends BookBuilder> Iterable<S> save(Iterable<S> entities) {
		for (BookBuilder i : entities) {
			i.setOwner(owner());
		}
		return db.save(entities);
	}

	@Override
	public BookBuilder findOne(Long id) {
		if (isAdmin()) {
			return db.findOne(id);
		}
		return db.findOneByIdAndOwner_Id(id, owner().getId());
	}

	@Override
	public boolean exists(Long id) {
		return db.exists(id);
	}

	@Override
	public Iterable<BookBuilder> findAll() {
		if (isAdmin()) {
			return db.findAll();
		}
		return db.findBooksByOwner_Id(owner().getId());
	}

	@Override
	public Iterable<BookBuilder> findAll(Iterable<Long> ids) {
		if (isAdmin()) {
			return db.findAll(ids);
		}
		return db.findAllByIdInAndOwner_Id(ids, owner().getId());
	}

	@Override
	public long count() {
		if (isAdmin()) {
			return db.count();
		}
		return db.countByOwner_Id(owner().getId());
	}

	@Override
	public void delete(Long id) {
		if (isAdmin()) {
			db.delete(id);
			;
		} else {
			db.deleteByIdAndOwner_Id(id, owner().getId());
		}
	}

	@Override
	public void delete(BookBuilder entity) {
		if (isAdmin()) {
			db.delete(entity);
		} else {
			db.deleteByIdAndOwner_Id(entity.getId(), owner().getId());
		}
	}

	@Override
	public void delete(Iterable<? extends BookBuilder> entities) {
		if (isAdmin()) {
			db.delete(entities);
		} else {
			List<Long> ids = new LinkedList<>();
			for (BookBuilder i : entities) {
				ids.add(i.getId());
			}
			db.deleteByIdInAndOwner_Id(ids, owner().getId());
		}
	}

	@Override
	public void deleteAll() {
		if (isAdmin()) {
			db.deleteAll();
		} else {
			db.deleteAllByOwner_Id(owner().getId());
		}
	}

	@Override
	public Iterable<BookBuilder> findAll(Sort sort) {
		if (isAdmin()) {
			return db.findAll(sort);
		}
		return db.findAllByOwner_Id(owner().getId(), sort);
	}

	@Override
	public Page<BookBuilder> findAll(Pageable pageable) {
		if (isAdmin()) {
			return db.findAll(pageable);
		}
		return db.findAllByOwner_Id(owner().getId(), pageable);
	}

	@Override
	public Page<SearchResult<Long>> search(String query, Pageable page) {
		return db.search(query, page);
	}

	@Override
	public Page<BookBuilder> findByIdIn(Iterable<Long> ids, Pageable pageable) {
		return db.findByIdInAndOwner_Id(ids, owner().getId(), pageable);
	}

	@Override
	public BookBuilder findOneByIdAndOwner_Id(Long id, Long owner) {
		return db.findOneByIdAndOwner_Id(id, owner);
	}

	@Override
	public Iterable<BookBuilder> findBooksByOwner_Id(Long owner) {
		return db.findBooksByOwner_Id(owner);
	}

	@Override
	public Long countByOwner_Id(Long owner) {
		return db.countByOwner_Id(owner);
	}

	@Override
	public Iterable<BookBuilder> findAllByIdInAndOwner_Id(Iterable<Long> ids, Long owner) {
		return db.findAllByIdInAndOwner_Id(ids, owner);
	}

	@Override
	public void deleteByIdAndOwner_Id(Long id, Long owner) {
		db.deleteByIdAndOwner_Id(id, owner);

	}

	@Override
	public void deleteAllByOwner_Id(Long owner) {
		db.deleteAllByOwner_Id(owner);
	}

	@Override
	public Iterable<BookBuilder> findAllByOwner_Id(Long owner, Sort sort) {
		return db.findAllByOwner_Id(owner, sort);
	}

	@Override
	public Page<BookBuilder> findAllByOwner_Id(Long owner, Pageable page) {
		return db.findAllByOwner_Id(owner, page);
	}

	@Override
	public Page<BookBuilder> findByIdInAndOwner_Id(Iterable<Long> ids, Long owner, Pageable page) {
		return db.findByIdInAndOwner_Id(ids, owner, page);
	}

	@Override
	public void deleteByIdInAndOwner_Id(Iterable<Long> ids, Long owner) {
		db.deleteByIdInAndOwner_Id(ids, owner);

	}

}
