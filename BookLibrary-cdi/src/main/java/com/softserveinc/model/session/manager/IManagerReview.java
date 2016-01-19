package com.softserveinc.model.session.manager;

import com.softserveinc.exception.BookCatalogException;
import com.softserveinc.exception.ReviewManagerException;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.Review;

/**
 * IManagerBook is an interface that describes all business methods for Book
 * entity
 */
public interface IManagerReview {

	public void createReview(Review review) throws ReviewManagerException, BookCatalogException;

	public void deleteReview(String idReview) throws ReviewManagerException;

	public void updateReview(Review review) throws ReviewManagerException;

	public double getAverageRating(Book book);

}
