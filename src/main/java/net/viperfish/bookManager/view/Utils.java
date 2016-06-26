package net.viperfish.bookManager.view;

import java.util.LinkedList;
import java.util.List;

import org.springframework.validation.FieldError;

public enum Utils {
	INSTANCE;
	public List<String> fieldErrors2String(Iterable<FieldError> errors) {
		List<String> result = new LinkedList<>();
		for (FieldError i : errors) {
			result.add(i.getDefaultMessage());
		}
		return result;
	}
}
