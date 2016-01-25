package com.softserveinc.model.persist.entity;

import java.io.IOException;
import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.annotations.Expose;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * The persistent class for the book database table.
 * 
 */
@Entity
@Table(name = "book")
@NamedQueries({
@NamedQuery(name = Book.FIND_ALL_BOOKS, query = "SELECT b FROM Book b"),
@NamedQuery(name = Book.FIND_BOOKS_WITH_RATING, query = "SELECT DISTINCT b FROM Review r "
		+ "JOIN r.book b WHERE r.rating = :rat"),
@NamedQuery(name = Book.FIND_BOOK_BY_NAME, query = "SELECT b FROM Book b WHERE b.bookName LIKE :nam"),
@NamedQuery(name = Book.FIND_BOOK_BY_ISNBN, query = "SELECT b FROM Book b WHERE b.isbn LIKE :isb "),
@NamedQuery(name = Book.FIND_BOOKS_BY_PUBLISHER, query = "SELECT b FROM Book b WHERE b.publisher LIKE :pub "),
@NamedQuery(name = Book.FIND_BOOKS_BY_AUTHOR, query = "SELECT b FROM Book b JOIN b.authors a WHERE a = :auth "),
@NamedQuery(name = Book.FIND_BOOKS_BY_LIST_ID, query = "SELECT b FROM Book b WHERE b.idBook IN :list "),
@NamedQuery(name = Book.FIND_COUNT_BOOKS, query = "SELECT COUNT(b) FROM Book b ")
})
public class Book implements Serializable {
	
	//@SqlResultSetMapping(name="findBooksForDataTable",
	//entities={
//	        @EntityResult(entityClass=com.softserveinc.model.persist.entity.Book.class, fields={
//	            @FieldResult(name="rating", column="rat")})}, 
	//columns={
//	        @ColumnResult(name="rat")})
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7480370770263659296L;
	public static final String FIND_ALL_BOOKS = "Book.findAll";
	public static final String FIND_BOOKS_WITH_RATING = "Book.findBooksWithRating";
	public static final String FIND_BOOK_BY_NAME = "Book.findBookByName";
	public static final String FIND_BOOK_BY_ISNBN ="Book.findBookByISNBN";
	public static final String FIND_BOOKS_BY_PUBLISHER = "Book.findBooksByPublisher";
	public static final String FIND_BOOKS_BY_AUTHOR = "Book.findBooksByAuthor";
	public static final String FIND_COUNT_BOOKS = "Book.findCountBooks";
	public static final String FIND_BOOKS_BY_LIST_ID = "Book.findBooksByListId";


	@Id
	@Column(name = "book_id")
	private String idBook;

	@Column(name = "book_name")
	private String bookName;


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name = "isbn")
	private String isbn;

	@Column(name = "publisher")
	private String publisher;

	@Column(name = "year_published")
	private Integer yearPublished;

	
	@OrderColumn(name="author.secondName")
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "book_author", joinColumns = { @JoinColumn(name = "book_id") }, inverseJoinColumns = {
			@JoinColumn(name = "author_id") })
	@JsonManagedReference(value="1")
	private  Set<Author> authors;
	


	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.REMOVE, mappedBy="book")
	@JsonManagedReference
	private  Set<Review> reviews;
	


	@Transient
	@JsonIgnore
	private transient Double rating;

	public Book() {
	}
	
	
	


	public Book(String idBook, String bookName, String isbn, String publisher, Integer yearPublished) {
		super();
		this.idBook = idBook;
		this.bookName = bookName;
		this.isbn = isbn;
		this.publisher = publisher;
		this.yearPublished = yearPublished;
	}





	public Book(String bookName, String isbn, String publisher, Integer yearPublished, Set<Author> authors) {
		super();
		this.bookName = bookName;
		this.isbn = isbn;
		this.publisher = publisher;
		this.yearPublished = yearPublished;
		this.authors = authors;
	}
	
	@PrePersist
	private void generateId() {
		this.idBook = UUID.randomUUID().toString();
		this.createdDate = new Date();
	}
	
	public Set<Review> getReviews() {
		return reviews;
	}

	public String getIdBook() {
		return idBook;
	}

	public void setIdBook(String idBook) {
		this.idBook = idBook;
	}

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	

	@JsonIgnore
	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	@JsonIgnore
	public Double getRating() {
		return rating;
	}
	
	@JsonIgnore
	public void setRating(Double rating) {
		this.rating = rating;
	}

	public String getBookName() {
		return this.bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPublisher() {
		return this.publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	@JsonGetter("yearPublished")
	public Integer getYearPublished() {
		return yearPublished;
	}
	
	@JsonSetter("yearPublished")
	public void setYearPublished(Integer yearPublished) {
		this.yearPublished = yearPublished;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(idBook, bookName, createdDate, isbn, publisher, yearPublished, authors);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Book book = (Book) obj;
		return Objects.equal(idBook, book.idBook) 
				&& Objects.equal(bookName, book.bookName)
				&& Objects.equal(createdDate, book.createdDate) 
				&& Objects.equal(isbn, book.isbn)
				&& Objects.equal(publisher, book.publisher) 
				&& Objects.equal(yearPublished, book.yearPublished)
				&& Objects.equal(authors, book.authors);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).omitNullValues()
				.add("idBook", idBook)
				.add("bookName", bookName)
				.add("createdDate", createdDate)
				.add("isbn", isbn)
				.add("publisher", publisher)
				.add("yearPublished", yearPublished)
				.add("authors", authors)
				.toString();
	}

}