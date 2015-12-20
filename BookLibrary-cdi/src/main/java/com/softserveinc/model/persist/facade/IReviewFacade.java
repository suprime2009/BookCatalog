package com.softserveinc.model.persist.facade;

import java.util.List;

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.Review;

/**
 * IReviewFacade an interface that describes all facade methods for Review entity.
 *
 */
public interface IReviewFacade {
	
	/**
	 * Method returns all Review by commenter.
	 * @param commenterName
	 * @return List<Review>
	 */
	List<Review> findAllReviewsByCommenter(String commenterName);
	
	/**
	 * Method gets average rating from database for book in argument.
	 * @param book.
	 * @return double averageRating.
	 */
	double findAverageRatingForBook(Book book);
	
	/**
	 * Method gets review by id number from database.
	 * @param id number in database.
	 * @return Review.
	 */
	Author findById(String id);
	
	/**
	 * Method gets all reviews from database.
	 * @return List<Review>.
	 */
	List<Author> findAll();

}
