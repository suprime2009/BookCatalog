package com.softserveinc.model.persist.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthorDTO implements Serializable, JsonFieldsHolder{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -837523043353366864L;
	
	private String idAuthor;
	private String firstName;
	private String secondName;
	
//	@JsonBackReference(value="book-author")
//	@JsonManagedReference(value="author-book")
	private List<BookDTO> books;
	
	

	public AuthorDTO(String idAuthor, String firstName, String secondName) {
		super();
		this.idAuthor = idAuthor;
		this.firstName = firstName;
		this.secondName = secondName;
	}

	@JsonCreator
	public AuthorDTO(@JsonProperty(AUTHOR_ID) String idAuthor,
			@JsonProperty(FIRST_NAME) String firstName, 
			@JsonProperty(SECOND_NAME) String secondName,  
			@JsonProperty(BOOKS) List<BookDTO> books) {
		super();
		this.idAuthor = idAuthor;
		this.firstName = firstName;
		this.secondName = secondName;
		this.books = books;
	}

	@JsonGetter(AUTHOR_ID)
	public String getIdAuthor() {
		return idAuthor;
	}

	@JsonSetter(AUTHOR_ID)
	public void setIdAuthor(String idAuthor) {
		this.idAuthor = idAuthor;
	}

	@JsonGetter(FIRST_NAME)
	public String getFirstName() {
		return firstName;
	}

	@JsonSetter(FIRST_NAME)
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@JsonGetter(SECOND_NAME)
	public String getSecondName() {
		return secondName;
	}

	@JsonSetter(SECOND_NAME)
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	@JsonGetter(BOOKS)
	public List<BookDTO> getBooks() {
		return books;
	}

	@JsonSetter(BOOKS)
	public void setBooks(List<BookDTO> books) {
		this.books = books;
	}


	
}
