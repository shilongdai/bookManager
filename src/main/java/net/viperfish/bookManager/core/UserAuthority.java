package net.viperfish.bookManager.core;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

import org.springframework.security.core.GrantedAuthority;

@Embeddable
public final class UserAuthority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8111123215921297996L;
	private String authority;

	public UserAuthority() {
	}

	public UserAuthority(String authority) {
		super();
		this.authority = authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Basic
	@Override
	public String getAuthority() {
		return authority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authority == null) ? 0 : authority.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAuthority other = (UserAuthority) obj;
		if (authority == null) {
			if (other.authority != null)
				return false;
		} else if (!authority.equals(other.authority))
			return false;
		return true;
	}

}
