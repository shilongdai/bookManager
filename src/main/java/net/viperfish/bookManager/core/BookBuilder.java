package net.viperfish.bookManager.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import org.apache.lucene.analysis.charfilter.HTMLStripCharFilterFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.CharFilterDef;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.validator.constraints.Length;

@Entity
@Indexed
@Table(name = "Book")
@AnalyzerDef(name = "custom", tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), charFilters = {
		@CharFilterDef(factory = HTMLStripCharFilterFactory.class) }, filters = {
				@TokenFilterDef(factory = LowerCaseFilterFactory.class),
				@TokenFilterDef(factory = ASCIIFoldingFilterFactory.class),
				@TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
						@Parameter(name = "language", value = "English") }) })
public class BookBuilder implements Serializable {
	/**
	 * 
	 */

	private static final long serialVersionUID = 5855272950611670378L;
	@Length(min = 0, max = 50, message = "{book.authorLength}")
	private String author;
	@Length(min = 0, max = 100, message = "{book.titleLength}")
	private String title;
	private boolean available;
	@Past(message = "{book.publishedPast}")
	private Date published;
	private Long id; // identifier
	@Length(min = 0, max = 20, message = "{book.genreTooLong}")
	private String genre;
	@Length(min = 0, max = 40, message = "{book.langLength}")
	private String lang;
	@Length(min = 0, max = 10000, message = "{book.descLength}")
	private String description;
	@Length(min = 10, max = 13, message = "{book.isbnLength}")
	private String isbn;
	@Length(min = 0, max = 50, message = "{book.locationLength}")
	private String location;

	private UserPrincipal owner;

	public BookBuilder() {
		author = new String();
		title = new String();
		lang = new String();
		description = new String();
		isbn = new String();
		owner = new UserPrincipal();
		location = new String();
	}

	public BookBuilder(Book b) {
		this.author = b.getAuthor();
		this.available = b.isAvailable();
		this.description = b.getDescription();
		this.genre = b.getGenre();
		this.id = b.getId();
		this.lang = b.getLang();
		if (b.getPublished() != null)
			this.published = new Date(b.getPublished().getTime());
		this.title = b.getTitle();
		this.isbn = b.getIsbn();
		this.owner = b.getOwner();
		this.location = b.getLocation();
	}

	public BookBuilder(String author, String title, boolean available, Date published, Long id, String genre,
			String lang, String description, String isbn, UserPrincipal owner, String location) {
		super();
		this.author = author;
		this.title = title;
		this.available = available;
		this.published = published;
		this.id = id;
		this.genre = genre;
		this.lang = lang;
		this.description = description;
		this.isbn = isbn;
		this.owner = owner;
		this.location = location;
	}

	@Field(analyze = Analyze.YES)
	@Analyzer(definition = "custom")
	@Basic
	public String getAuthor() {
		return author;
	}

	public BookBuilder setAuthor(String author) {
		this.author = author;
		return this;
	}

	@Basic
	@Field(analyze = Analyze.YES)
	@Analyzer(definition = "custom")
	public String getTitle() {
		return title;
	}

	public BookBuilder setTitle(String title) {
		this.title = title;
		return this;
	}

	@Basic
	@Field(analyze = Analyze.YES)
	public boolean isAvailable() {
		return available;
	}

	public BookBuilder setAvailable(boolean available) {
		this.available = available;
		return this;
	}

	@Temporal(TemporalType.DATE)
	@Field(analyze = Analyze.NO)
	@DateBridge(resolution = Resolution.DAY)
	public Date getPublished() {
		return published;
	}

	public BookBuilder setPublished(Date published) {
		this.published = published;
		return this;
	}

	@Basic
	@Field(analyze = Analyze.YES)
	@Analyzer(definition = "custom")
	public String getDescription() {
		return description;
	}

	public BookBuilder setDescription(String description) {
		this.description = description;
		return this;
	}

	@DocumentId
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public BookBuilder setId(Long id) {
		this.id = id;
		return this;
	}

	@Field(analyze = Analyze.YES)
	@Analyzer(definition = "custom")
	@Basic
	public String getGenre() {
		return genre;
	}

	public BookBuilder setGenre(String genre) {
		this.genre = genre;
		return this;
	}

	@Basic
	@Field(analyze = Analyze.YES)
	@Analyzer(definition = "custom")
	public String getLang() {
		return lang;
	}

	public BookBuilder setLang(String lang) {
		this.lang = lang;
		return this;
	}

	@Basic
	@Analyzer(definition = "custom")
	@Field(analyze = Analyze.YES)
	public String getIsbn() {
		return isbn;
	}

	public BookBuilder setIsbn(String isbn) {
		this.isbn = isbn;
		return this;
	}

	public Book build() {
		return new Book(author, title, available, published, id, genre, lang, description, isbn, owner, location);
	}

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "UserId")
	public UserPrincipal getOwner() {
		return owner;
	}

	public BookBuilder setOwner(UserPrincipal owner) {
		this.owner = owner;
		return this;
	}

	@Field(analyze = Analyze.YES)
	@Analyzer(definition = "custom")
	@Basic
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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
		BookBuilder other = (BookBuilder) obj;
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

	@Override
	public String toString() {
		return "BookBuilder [author=" + author + ", title=" + title + ", available=" + available + ", published="
				+ published + ", id=" + id + ", genre=" + genre + ", lang=" + lang + ", description=" + description
				+ ", isbn=" + isbn + ", location=" + location + ", owner=" + owner + "]";
	}

}