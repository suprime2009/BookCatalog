package com.softserveinc.model.session.util;



public class BookUIWrapper {
	

	private String idBook;
	private String bookName;
	
	private boolean selected;

	public BookUIWrapper(String idBook, String bookName, boolean selected) {
		super();
		this.idBook = idBook;
		this.bookName = bookName;
		this.selected = selected;
	}

	public String getIdBook() {
		return idBook;
	}

	public void setIdBook(String idBook) {
		this.idBook = idBook;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
	
	

}
