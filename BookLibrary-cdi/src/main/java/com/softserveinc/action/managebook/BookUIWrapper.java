package com.softserveinc.action.managebook;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;

public class BookUIWrapper extends Book{
	
	private boolean selected;
	
	public BookUIWrapper(Book book) {
		setIdBook(book.getIdBook());
		setBookName(book.getBookName());
		setPublisher(book.getPublisher());
		setIsbn(book.getIsbn());
		setAuthors(book.getAuthors());
		setCreatedDate(book.getCreatedDate());
		setRating(book.getRating());
		setYearPublished(book.getYearPublished());
	}
	

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		System.out.println("SetSelected" + selected + getBookName());

	}

}
