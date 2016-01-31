package com.softserveinc.booklibrary.action.util;

import com.softserveinc.booklibrary.model.entity.Book;

/**
 * This class is a wrapper class for Book entity. 
 * Class may use in UI Component.
 *
 */
public class BookUIWrapper extends UIWrapper {
	
	private Book book;
	
	public BookUIWrapper(Book book) {
		super();
		this.book = book;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
}
