package net.viperfish.bookManager.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@Transactional
public interface BookDatabase
		extends CrudRepository<Book, Long>, PagingAndSortingRepository<Book, Long>, SearchableDatabase<Book> {

	public Page<Book> findByIdIn(Iterable<Long> ids, Pageable pageable);

	public Book findOneByIdAndOwner_Id(Long id, Long owner);

	public Iterable<Book> findBooksByOwner_Id(Long owner);

	public Long countByOwner_Id(Long owner);

	public Iterable<Book> findAllByIdInAndOwner_Id(Iterable<Long> ids, Long owner);

	public void deleteByIdAndOwner_Id(Long id, Long owner);

	public void deleteAllByOwner_Id(Long owner);

	public Iterable<Book> findAllByOwner_Id(Long owner, Sort sort);

	public Page<Book> findAllByOwner_Id(Long owner, Pageable page);

	public Page<Book> findByIdInAndOwner_Id(Iterable<Long> ids, Long owner, Pageable page);

	public void deleteByIdInAndOwner_Id(Iterable<Long> ids, Long owner);

}
