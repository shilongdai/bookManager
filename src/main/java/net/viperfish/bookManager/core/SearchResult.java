package net.viperfish.bookManager.core;

public final class SearchResult<T> {

	private T result;
	private float relevence;

	public SearchResult(T src, float relevence) {
		this.relevence = relevence;
		this.result = src;
	}

	public T getResult() {
		return result;
	}

	public float getRelevence() {
		return relevence;
	}

}
