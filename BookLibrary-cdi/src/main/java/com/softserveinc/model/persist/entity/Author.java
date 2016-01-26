package com.softserveinc.model.persist.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.base.MoreObjects;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * The persistent class for the author database table.
 * 
 */
@Entity
@Table(name = "author")
@NamedQueries({ @NamedQuery(name = Author.FIND_ALL_AUTHORS, query = "SELECT a FROM Author a"),
		@NamedQuery(name = Author.FIND_AUTHOR_BY_FULL_NAME, query = "SELECT DISTINCT a FROM Author a WHERE a.firstName LIKE :fn AND a.secondName LIKE :sn"),
		@NamedQuery(name = Author.BULK_REMOVE, query = "DELETE FROM Author a WHERE a IN :list"),
		@NamedQuery(name = Author.FIND_ALL_AUTHORS_BY_BOOK, query = "SELECT a FROM Author a JOIN a.books b WHERE b = :bk"),
		@NamedQuery(name = Author.FIND_AUTHOR_RATING, query = "SELECT AVG(r.rating) FROM Author a LEFT JOIN a.books b LEFT JOIN b.reviews r WHERE a = :author"),
		@NamedQuery(name = Author.FIND_AUTHORS_BY_LIST_ID, query = "SELECT a FROM Author a WHERE a.idAuthor IN :list "),
		@NamedQuery(name = Author.FIND_AUTHORS_NAMES_FOR_AUTOCOMPLETE, query = "SELECT CONCAT(a.secondName, ' ', a.firstName) FROM Author a WHERE a.secondName LIKE :sn or a.firstName LIKE :sn")})
public class Author implements Serializable {

	private static final long serialVersionUID = -4647534114628862508L;
	
	public static final String FIND_ALL_AUTHORS = "Author.findAll";
	public static final String FIND_AUTHOR_BY_FULL_NAME = "Author.findAuthorByFullName";
	public static final String FIND_ALL_AUTHORS_BY_BOOK = "Author.findAllAuthorsByBook";
	public static final String FIND_AUTHORS_NAMES_FOR_AUTOCOMPLETE = "Author.findAuthorsFullNamesForAutocomplete";
	public static final String FIND_AUTHOR_RATING = "Author.findAuthorAvegareRating";
	public static final String BULK_REMOVE = "Author.bulkRemove";
	public static final String FIND_AUTHORS_BY_LIST_ID = "Author.findAuthorsByListId";

	@Id
	@Column(name = "author_id")
	@JsonIgnore
	private String idAuthor;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "second_name")
	private String secondName;

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name = "book_author", joinColumns = { @JoinColumn(name = "author_id") }, 
	inverseJoinColumns = {@JoinColumn(name = "book_id") })
	@JsonBackReference(value="1")
	private List<Book> books;

	public Author() {
	}

	public Author(String firstName, String secondName) {
		super();
		this.firstName = firstName;
		this.secondName = secondName;
	}
	
	@PrePersist
	private void generateId() {
		this.idAuthor = UUID.randomUUID().toString();
		this.createdDate = new Date();
	}

	public String getIdAuthor() {
		return this.idAuthor;
	}
	
	public void setIdAuthor(String idAuthor) {
		this.idAuthor = idAuthor;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idAuthor, createdDate, firstName, secondName);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Author author = (Author) obj;
		return Objects.equals(idAuthor, author.idAuthor) && Objects.equals(createdDate, author.createdDate)
				&& Objects.equals(firstName, author.firstName) && Objects.equals(secondName, author.secondName);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).omitNullValues().add("idAuthor", idAuthor)
				.add("createdDate", createdDate).add("firstName", firstName).add("secondName", secondName).toString();
	}
}