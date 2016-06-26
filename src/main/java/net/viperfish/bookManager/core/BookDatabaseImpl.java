package net.viperfish.bookManager.core;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.FatalBeanException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.EntityManagerProxy;
import org.springframework.transaction.annotation.Transactional;

public class BookDatabaseImpl implements SearchableDatabase<Long> {

	@PersistenceContext
	private EntityManager entityManager;

	private EntityManagerProxy proxy;

	private FullTextEntityManager getFullTextEntityManager() {
		return Search.getFullTextEntityManager(proxy.getTargetEntityManager());
	}

	@Transactional
	@Override
	public Page<SearchResult<Long>> search(String query, Pageable page) {
		FullTextEntityManager manager = this.getFullTextEntityManager();

		QueryBuilder builder = manager.getSearchFactory().buildQueryBuilder().forEntity(BookBuilder.class).get();

		Query luceneQuery = builder.keyword().fuzzy()
				.onFields("author", "title", "lang", "description", "isbn", "genre", "location").matching(query)
				.createQuery();

		FullTextQuery q = manager.createFullTextQuery(luceneQuery, BookBuilder.class);
		q.setProjection(FullTextQuery.ID, FullTextQuery.SCORE);

		long total = q.getResultSize();
		q.setFirstResult(page.getOffset()).setMaxResults(page.getPageSize());

		@SuppressWarnings("unchecked")
		List<Object[]> result = q.getResultList();
		List<SearchResult<Long>> fine = new LinkedList<>();
		for (Object[] i : result) {
			fine.add(new SearchResult<Long>((Long) i[0], (float) i[1]));
		}
		return new PageImpl<>(fine, page, total);
	}

	@PostConstruct
	public void init() {
		if (!(this.entityManager instanceof EntityManagerProxy)) {
			throw new FatalBeanException("Entity Manager" + this.entityManager + " is not a proxy");
		}
		this.proxy = (EntityManagerProxy) this.entityManager;
	}

}
