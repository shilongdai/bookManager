package net.viperfish.bookManager.transactions;

import java.util.Set;

import net.viperfish.bookManager.core.UserAuthority;
import net.viperfish.bookManager.core.UserPrincipal;
import net.viperfish.bookManager.core.UserPrincipalDatabase;

final class CreateRootUserTransaction extends CreateUserTransaction {

	public CreateRootUserTransaction(UserPrincipal toCreate, UserPrincipalDatabase db) {
		super(toCreate, db);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void roles(Set<UserAuthority> roles) {
		UserAuthority toAdd = new UserAuthority();
		toAdd.setAuthority("admin");
		UserAuthority user = new UserAuthority();
		user.setAuthority("user");
		roles.add(toAdd);
		roles.add(user);
	}

}
