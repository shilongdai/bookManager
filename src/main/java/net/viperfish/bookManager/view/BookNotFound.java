package net.viperfish.bookManager.view;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public final class BookNotFound extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5423886167373183789L;

	public BookNotFound() {
		// TODO Auto-generated constructor stub
	}

	public BookNotFound(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public BookNotFound(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public BookNotFound(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public BookNotFound(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

}
