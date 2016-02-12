package com.softserveinc.booklibrary.rest.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.softserveinc.booklibrary.rest.util.JsonFieldsHolder;

/**
 * This class is DTO for {@link Book} entity. It use to transfer entity in JSON
 * format.
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BookDTO implements Serializable, JsonFieldsHolder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 316690988948506254L;

	private String idBook;
	private String bookName;
	private String isbn;
	private String publisher;
	private Integer yearPublished;

	private List<AuthorDTO> authors;

	public BookDTO(String idBook, String bookName, String isbn, String publisher, Integer yearPublished) {
		super();
		this.idBook = idBook;
		this.bookName = bookName;
		this.isbn = isbn;
		this.publisher = publisher;
		this.yearPublished = yearPublished;
	}

	@JsonCreator
	public BookDTO(@JsonProperty(BOOK_ID) String idBook, @JsonProperty(BOOK_NAME) String bookName,
			@JsonProperty(ISBN) String isbn, @JsonProperty(PUBLISHER) String publisher,
			@JsonProperty(YEAR_PUBLISHED) Integer yearPublished, @JsonProperty(AUTHORS) List<AuthorDTO> authors) {
		super();
		this.idBook = idBook;
		this.bookName = bookName;
		this.isbn = isbn;
		this.publisher = publisher;
		this.yearPublished = yearPublished;
		this.authors = authors;
	}

	@JsonGetter(BOOK_ID)
	public String getIdBook() {
		return idBook;
	}

	@JsonSetter(BOOK_ID)
	public void setIdBook(String idBook) {
		this.idBook = idBook;
	}

	@JsonGetter(BOOK_NAME)
	public String getBookName() {
		return bookName;
	}

	@JsonSetter(BOOK_NAME)
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	@JsonGetter(ISBN)
	public String getIsbn() {
		return isbn;
	}

	@JsonSetter(ISBN)
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@JsonGetter(PUBLISHER)
	public String getPublisher() {
		return publisher;
	}

	@JsonSetter(PUBLISHER)
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@JsonGetter(YEAR_PUBLISHED)
	public Integer getYearPublished() {
		return yearPublished;
	}

	@JsonSetter(YEAR_PUBLISHED)
	public void setYearPublished(Integer yearPublished) {
		this.yearPublished = yearPublished;
	}

	@JsonGetter(AUTHORS)
	public List<AuthorDTO> getAuthors() {
		return authors;
	}

	@JsonSetter(AUTHORS)
	public void setAuthors(List<AuthorDTO> authors) {
		this.authors = authors;
	}

}
