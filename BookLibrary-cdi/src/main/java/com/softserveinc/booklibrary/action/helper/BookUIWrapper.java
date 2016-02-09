package com.softserveinc.booklibrary.action.helper;

import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.model.entity.Review;

/**
 * This class is a wrapper class for Book entity. Class may use in UI Component.
 *
 */
public class BookUIWrapper extends UIWrapper {

	private Book book;

	private Double averagerating;
	private int countReviews;
	private Review latestReview;

	public BookUIWrapper(Book book) {
		super();
		this.book = book;
	}

	public BookUIWrapper(Book book, Double averagerating, int countReviews, Review latestReview) {
		super();
		this.book = book;
		this.averagerating = averagerating;
		this.countReviews = countReviews;
		this.latestReview = latestReview;
	}

	public Double getAveragerating() {
		return averagerating;
	}

	public void setAveragerating(Double averagerating) {
		this.averagerating = averagerating;
	}

	public int getCountReviews() {
		return countReviews;
	}

	public void setCountReviews(int countReviews) {
		this.countReviews = countReviews;
	}

	public Review getLatestReview() {
		return latestReview;
	}

	public void setLatestReview(Review latestReview) {
		this.latestReview = latestReview;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
}
