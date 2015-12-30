package com.softserveinc.model.persist.entity;

public enum AuthorWrapper {

	AUTHOR_BUSINESS_WRAPPER("idAuthor", "createdDate", "firstName", "secondName", "books"),
	AUTHOR_UI_WRAPPER("idAuthor", "createdDate", "First Name", "Second Name", "Author books");
	
	
	
	public final String idAuthor;
	public final String createdDate;
	public final String firstName;
	public final String secondName;
	public final String books;
	
	private AuthorWrapper(String idAuthor, String createdDate, String firstName, String secondName, String books) {
		this.idAuthor = idAuthor;
		this.createdDate = createdDate;
		this.firstName = firstName;
		this.secondName = secondName;
		this.books = books;
	}

	public String getIdAuthor() {
		return idAuthor;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public String getBooks() {
		return books;
	}
	
	
	
	
	




}


