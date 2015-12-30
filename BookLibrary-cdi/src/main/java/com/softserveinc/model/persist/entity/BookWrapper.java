package com.softserveinc.model.persist.entity;

import javax.ejb.Stateful;

public enum BookWrapper {
	BOOK_BUSINESS_WRAPPER("idBook", "bookName", "createdDate", "isbn", "publisher", "yearPublished", "authors",
			"rating"), BOOK_UI_WRAPPER("idBook", "Book name", "Created date", "ISBN Number", "Publisher",
					"Year of published", "Authors", "Average rating");

	public final String id;
	public final String bookName;
	public final String createdDate;
	public final String isbn;
	public final String publisher;
	public final String yearPublished;
	public final String authors;
	public final String rating;

	private BookWrapper(String id, String bookName, String createdDate, String isbn, String publisher,
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

	public static String convertFromWrapperUIToWrapper(String value) {

		switch (value) {
		case "idBook":
			return BOOK_BUSINESS_WRAPPER.id;
		case "Book Name":
			return BOOK_BUSINESS_WRAPPER.bookName;
		case "Created date":
			return BOOK_BUSINESS_WRAPPER.createdDate;
		case "ISBN Number":
			return BOOK_BUSINESS_WRAPPER.isbn;
		case "Publisher":
			return BOOK_BUSINESS_WRAPPER.publisher;
		case "Year of published":
			return BOOK_BUSINESS_WRAPPER.yearPublished;
		case "Authors":
			return BOOK_BUSINESS_WRAPPER.authors;
		case "Average rating":
			return BOOK_BUSINESS_WRAPPER.rating;
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
