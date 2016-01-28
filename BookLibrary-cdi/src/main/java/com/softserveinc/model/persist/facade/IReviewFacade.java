package com.softserveinc.model.persist.facade;

import java.util.List;
import java.util.Map;

import org.richfaces.component.SortOrder;

import com.softserveinc.exception.ReviewFacadeException;
import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.Review;
import com.softserveinc.model.session.util.DataTableSearchHolder;
import com.softserveinc.model.session.util.ReviewRatingFieldsEnum;

/**
 * IReviewFacade is an interface that describes all facade (read) operations for
 * {@link Review} entity.
 *
 */
public interface IReviewFacade {

	public static final String PERSISTANCE_UNIT_PRIMARY = "primary";

	/**
	 * Method returns all Review by commenter name.
	 * 
	 * @param commenterName
	 *            {@code String}
	 * @return List of Reviews.
	 */
	List<Review> findAllReviewsByCommenter(String commenterName);

	/**
	 * Method gets average rating from database for book in argument. If book
	 * not yet graded (has no one rating), method returns null.
	 * 
	 * @param book
	 *            {@link Book} entity.
	 * @return Double averageRating for book.
	 */
	Double findAverageRatingForBook(Book book);

	/**
	 * Method gets review by id number from database.
	 * 
	 * @param id
	 *            number in database.
	 * @return review {@link Review}.
	 */
	Review findById(String id);

	/**
	 * Method gets all reviews from database.
	 * 
	 * @return List of Reviews.
	 */
	List<Review> findAll();

	List<Review> findReviewsForBook(Book book, int firstRow, int countRows, SortOrder order)
			throws ReviewFacadeException;

	List<Review> findReviewsForBook(Book book);

	int findCountReviewForBook(Book book);

}
