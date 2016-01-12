package com.softserveinc.model.persist.facade;

import java.util.List;
import java.util.Map;

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.Review;
import com.softserveinc.model.session.util.DataTableSearchHolder;
import com.softserveinc.model.session.util.ReviewRatingFieldsEnum;

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
	Double findAverageRatingForBook(Book book);
	
	/**
	 * Method gets review by id number from database.
	 * @param id number in database.
	 * @return Review.
	 */
	Review findById(String id);
	
	/**
	 * Method gets all reviews from database.
	 * @return List<Review>.
	 */
	List<Author> findAll();
	

	List<Review> findReviewsForBook(Book book, int firstRow, int countRows);
	
	int findCountReviewForBook(Book book);
	
	Map<Integer, Integer> findCountBooksByRating();



}
