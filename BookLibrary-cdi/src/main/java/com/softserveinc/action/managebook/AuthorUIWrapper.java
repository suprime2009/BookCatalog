package com.softserveinc.action.managebook;

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;

public class AuthorUIWrapper extends UIWrapper {

	private Author author;
	private int countBooks;
	private int countReviews;
	private Double averageRating;

	public AuthorUIWrapper(Author author, int countBooks, int countReviews,  Double averageRating) {
		super();
		this.author = author;
		this.averageRating = averageRating;
		this.countBooks = countBooks;
		this.countReviews = countReviews;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}

	public int getCountBooks() {
		return countBooks;
	}

	public void setCountBooks(int countBooks) {
		this.countBooks = countBooks;
	}

	public int getCountReviews() {
		return countReviews;
	}

	public void setCountReviews(int countReviews) {
		this.countReviews = countReviews;
	}

}
