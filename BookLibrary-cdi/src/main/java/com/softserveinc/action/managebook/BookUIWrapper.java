package com.softserveinc.action.managebook;

import com.softserveinc.model.persist.entity.Book;

public class BookUIWrapper extends UIWrapper {
	
	private Book book;
	
	BookUIWrapper(Book book) {
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
