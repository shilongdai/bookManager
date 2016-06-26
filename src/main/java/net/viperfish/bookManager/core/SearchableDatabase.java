package net.viperfish.bookManager.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchableDatabase<T> {
	public Page<SearchResult<T>> search(String query, Pageable page);
}
