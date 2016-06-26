package net.viperfish.bookManager.transactions;

import java.util.Set;

import net.viperfish.bookManager.core.UserAuthority;
import net.viperfish.bookManager.core.UserPrincipal;
import net.viperfish.bookManager.core.UserPrincipalDatabase;

final class CreateRegularUserTransaction extends CreateUserTransaction {

	public CreateRegularUserTransaction(UserPrincipal toCreate, UserPrincipalDatabase db) {
		super(toCreate, db);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void roles(Set<UserAuthority> roles) {
		roles.add(new UserAuthority("user"));
	}

}
