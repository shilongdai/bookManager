package net.viperfish.bookManager.core;

import java.util.Date;
import java.util.HashSet;

import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;

public final class Book implements Comparable<Book> {

	@Length(min = 0, max = 50, message = "{book.authorLength}")
	private String author;
	@Length(min = 0, max = 100, message = "{book.titleLength}")
	private String title;
	private boolean available;
	@Past(message = "{book.publishedPast}")
	private Date published;
	private Long id; // identifier
	private String genre;
	@Length(min = 0, max = 40, message = "{book.langLength}")
	private String lang;
	@Length(min = 0, max = 10000, message = "{book.descLength}")
	private String description;
	@Length(min = 10, max = 13, message = "{book.isbnLength}")
	private String isbn;
	private UserPrincipal owner;
	@Length(min = 0, max = 50, message = "{book.locationLength}")
	private String location;

	public Book(String author, String title, boolean available, Date published, Long id, String genre, String lang,
			String description, String isbn, UserPrincipal owner, String location) {
		super();
		this.author = (author == null) ? new String() : author;
		this.title = (title == null) ? new String() : title;
		this.available = available;
		if (published != null) {
			this.published = new Date(published.getTime());
		}
		this.id = id;
		this.genre = genre;
		this.lang = (lang == null) ? new String() : lang;
		this.description = (description == null) ? new String() : description;
		this.isbn = (isbn == null) ? new String() : isbn;
		this.owner = owner;
		this.location = location;
	}

	public Book(Book src) {
		this(src.author, src.title, src.available, src.published, src.id, src.genre, src.lang, src.description,
				src.isbn, src.owner, src.location);
	}

	public Book(Book src, Long id) {
		this(src.author, src.title, src.available, src.published, id, src.genre, src.lang, src.description, src.isbn,
				src.owner, src.location);
	}

	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}

	public boolean isAvailable() {
		return available;
	}

	public Date getPublished() {
		if (this.published != null) {
			return new Date(this.published.getTime());
		}
		return null;
	}

	public Long getId() {
		return id;
	}

	public String getGenre() {
		return genre;
	}

	public String getLang() {
		return lang;
	}

	public String getDescription() {
		return description;
	}

	public String getIsbn() {
		return isbn;
	}

	public UserPrincipal getOwner() {
		UserPrincipal copy = new UserPrincipal();
		copy.setAccountNonExpired(this.owner.isAccountNonExpired());
		copy.setAccountNonLocked(this.owner.isAccountNonLocked());
		copy.setEnabled(this.owner.isEnabled());
		copy.setCredentialsNonExpired(this.owner.isCredentialsNonExpired());
		copy.setId(this.owner.getId());
		if (this.owner.getAuthorities() == null) {
			copy.setAuthorities(new HashSet<UserAuthority>());
		} else {
			copy.setAuthorities(new HashSet<>(this.owner.getAuthorities()));
		}
		copy.setPassword(this.owner.getPassword());
		copy.setUsername(this.owner.getUsername());
		return copy;
	}

	public String getLocation() {
		return location;
	}

	@Override
	public int compareTo(Book o) {
		if (this == o) {
			return 0;
		}
		if (o == null) {
			return 1;
		}
		if (this.equals(o)) {
			return 0;
		}
		if (this.title == null) {
			if (o.title != null) {
				return -1;
			}
		} else {
			if (o.title == null) {
				return 1;
			} else if (this.title.compareTo(o.title) != 0) {
				return this.title.compareTo(o.title);
			}
		}

		if (this.author == null) {
			if (o.author != null) {
				return -1;
			}
		} else {
			if (o.author == null) {
				return 1;
			} else if (this.author.compareTo(o.author) != 0) {
				return this.author.compareTo(o.author);
			}
		}

		if (this.genre == null) {
			if (o.genre != null) {
				return -1;
			}
		} else {
			if (o.genre == null) {
				return 1;
			} else if (this.genre.compareTo(o.genre) != 0) {
				return this.genre.compareTo(o.genre);
			}
		}

		if (this.lang == null) {
			if (o.lang != null) {
				return -1;
			}
		} else {
			if (o.lang == null) {
				return 1;
			} else if (this.lang.compareTo(o.lang) != 0) {
				return this.lang.compareTo(o.lang);
			}
		}

		if (this.published == null) {
			if (o.published != null) {
				return -1;
			}
		} else {
			if (o.published == null) {
				return 1;
			} else if (this.published.compareTo(o.published) != 0) {
				return this.published.compareTo(o.published);
			}
		}

		if (this.available) {
			if (!o.available) {
				return 1;
			}
		} else {
			if (o.available) {
				return -1;
			}
		}

		if (this.description == null) {
			if (o.description != null) {
				return -1;
			}
		} else {
			if (o.description == null) {
				return 1;
			} else if (this.description.compareTo(o.description) != 0) {
				return this.description.compareTo(o.description);
			}
		}

		if (this.isbn == null) {
			if (o.isbn != null) {
				return -1;
			}
		} else {
			if (o.isbn == null) {
				return 1;
			} else if (this.isbn.compareTo(o.isbn) != 0) {
				return this.isbn.compareTo(o.isbn);
			}
		}

		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + (available ? 1231 : 1237);
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result + ((lang == null) ? 0 : lang.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((published == null) ? 0 : published.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (available != other.available)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (genre == null) {
			if (other.genre != null)
				return false;
		} else if (!genre.equals(other.genre))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		if (lang == null) {
			if (other.lang != null)
				return false;
		} else if (!lang.equals(other.lang))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (published == null) {
			if (other.published != null)
				return false;
		} else if (!published.equals(other.published))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
