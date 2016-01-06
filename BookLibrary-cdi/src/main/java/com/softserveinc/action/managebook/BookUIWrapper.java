package com.softserveinc.action.managebook;

import com.softserveinc.model.persist.entity.Book;

/**
 * This class is a UI wrapper for Book entity. Class has an additional property - boolean variable .
 *  If variable equals true  - book has been selected on UI. 
 *
 */
public class BookUIWrapper {
	
	/**
	 * If true - book selected. Default - false.
	 */
	private boolean selected;
	
	/**
	 * Book entity
	 */
	private Book book;
	
	public BookUIWrapper(Book book) {
		this.book = book;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;

	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
}
