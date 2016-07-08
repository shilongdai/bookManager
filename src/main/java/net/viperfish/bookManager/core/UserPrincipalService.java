package net.viperfish.bookManager.core;

import java.util.concurrent.ExecutionException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

@Validated
public interface UserPrincipalService extends CRUDService<UserPrincipal, Long> {
	public @Valid @NotNull UserPrincipal shallowUpdateUser(@NotNull Long id, @Valid @NotNull UserPrincipal p)
			throws ExecutionException;

	public @Valid @NotNull UserPrincipal addUser(@NotNull @Valid UserPrincipal user) throws ExecutionException;

	public @Valid @NotNull UserPrincipal addAdmin(UserPrincipal admin) throws ExecutionException;
}
