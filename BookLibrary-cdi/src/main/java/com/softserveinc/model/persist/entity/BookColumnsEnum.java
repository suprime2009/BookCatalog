package com.softserveinc.model.persist.entity;

import javax.ejb.Stateful;

public enum BookColumnsEnum {
	BOOK_BUSINESS_VIEW("idBook", "bookName", "createdDate", "isbn", "publisher", "yearPublished", "authors",
			"rating"), 
	BOOK_UI_VIEW("idBook", "Book name", "Created date", "ISBN Number", "Publisher",
					"Year of published", "Authors", "Average rating");

	public final String id;
	public final String bookName;
	public final String createdDate;
	public final String isbn;
	public final String publisher;
	public final String yearPublished;
	public final String authors;
	public final String rating;

	private BookColumnsEnum(String id, String bookName, String createdDate, String isbn, String publisher,
			String yearPublished, String authors, String rating) {
		this.id = id;
		this.bookName = bookName;
		this.createdDate = createdDate;
		this.isbn = isbn;
		this.publisher = publisher;
		this.yearPublished = yearPublished;
		this.authors = authors;
		this.rating = rating;
	}
	
	public static String convertToUIView(String value) {

		switch (value) {
		case "idBook":
			return BOOK_UI_VIEW.id;
		case "bookName":
			return BOOK_UI_VIEW.bookName;
		case "createdDate":
			return BOOK_UI_VIEW.createdDate;
		case "isbn":
			return BOOK_UI_VIEW.isbn;
		case "publisher":
			return BOOK_UI_VIEW.publisher;
		case "yearPublished":
			return BOOK_UI_VIEW.yearPublished;
		case "authors":
			return BOOK_UI_VIEW.authors;
		case "rating":
			return BOOK_UI_VIEW.rating;
		default:
			throw new IllegalArgumentException();
		}
	}
	
	

	public String getId() {
		return id;
	}

	public String getBookName() {
		return bookName;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getYearPublished() {
		return yearPublished;
	}

	public String getAuthors() {
		return authors;
	}

	public String getRating() {
		return rating;
	}

}
