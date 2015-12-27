package com.softserveinc.model.persist.entity;

import java.io.Serializable;
import javax.persistence.*;


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
		@NamedQuery(name = Author.FIND_ALL_AUTHORS_BY_BOOK, query = "SELECT a FROM Author a JOIN a.books b WHERE b = :bk"), })
public class Author implements Serializable {

	private static final long serialVersionUID = -4647534114628862508L;
	
	public static final String FIND_ALL_AUTHORS = "Author.findAll";
	public static final String FIND_AUTHOR_BY_FULL_NAME = "Author.findAuthorByFullName";
	public static final String FIND_ALL_AUTHORS_BY_BOOK = "Author.findAllAuthorsByBook";

	@Id
	@Column(name = "author_id")
	private String idAuthor;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "second_name")
	private String secondName;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "book_author", joinColumns = { @JoinColumn(name = "author_id") }, 
	inverseJoinColumns = {@JoinColumn(name = "book_id") })
	private Set<Book> books;

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

	public String getIdauthor() {
		return this.idAuthor;
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
		return this.secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
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

//	@Override
//	public String toString() {
//		return MoreObjects.toStringHelper(this).omitNullValues().add("idAuthor", idAuthor)
//				.add("createdDate", createdDate).add("firstName", firstName).add("secondName", secondName).toString();
//	}
}