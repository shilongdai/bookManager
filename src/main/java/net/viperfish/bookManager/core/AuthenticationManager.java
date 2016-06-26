package net.viperfish.bookManager.core;

public interface AuthenticationManager {
	public boolean verify(String user, String password);

	public void register(String user, String password);

	public void remove(String user);

	public void edit(String user, String password);
}
