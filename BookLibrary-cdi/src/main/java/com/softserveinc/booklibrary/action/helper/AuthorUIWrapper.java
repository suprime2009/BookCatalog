package com.softserveinc.booklibrary.action.helper;

import com.softserveinc.booklibrary.model.entity.Author;

/**
 * This class is a UI wrapper class for {@link Author} entity. Class may use in
 * UI Component.
 *
 */
public class AuthorUIWrapper extends UIWrapper {

	private Author author;
	private int countBooks;
	private int countReviews;
	private Double averageRating;

	public AuthorUIWrapper(Author author, int countBooks, int countReviews, Double averageRating) {
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
