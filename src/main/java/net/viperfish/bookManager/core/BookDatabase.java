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
public interface BookDatabase extends CrudRepository<BookBuilder, Long>, PagingAndSortingRepository<BookBuilder, Long>,
		SearchableDatabase<Long> {

	public Page<BookBuilder> findByIdIn(Iterable<Long> ids, Pageable pageable);

	public BookBuilder findOneByIdAndOwner_Id(Long id, Long owner);

	public Iterable<BookBuilder> findBooksByOwner_Id(Long owner);

	public Long countByOwner_Id(Long owner);

	public Iterable<BookBuilder> findAllByIdInAndOwner_Id(Iterable<Long> ids, Long owner);

	public void deleteByIdAndOwner_Id(Long id, Long owner);

	public void deleteAllByOwner_Id(Long owner);

	public Iterable<BookBuilder> findAllByOwner_Id(Long owner, Sort sort);

	public Page<BookBuilder> findAllByOwner_Id(Long owner, Pageable page);

	public Page<BookBuilder> findByIdInAndOwner_Id(Iterable<Long> ids, Long owner, Pageable page);

	public void deleteByIdInAndOwner_Id(Iterable<Long> ids, Long owner);

}
