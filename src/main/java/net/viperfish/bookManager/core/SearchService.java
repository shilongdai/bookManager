package net.viperfish.bookManager.core;

import java.util.concurrent.ExecutionException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

@Validated
public interface SearchService<T, Q> {
	public @NotNull Page<T> search(@NotNull @Valid Q query, @NotNull Pageable paging) throws ExecutionException;

}
