package net.viperfish.bookManager.core;

import java.util.concurrent.ExecutionException;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

@Validated
public interface BookService extends CRUDService<Book, Long>, SearchService<Book, String> {
	public @NotNull Report generateReport() throws ExecutionException;

}
