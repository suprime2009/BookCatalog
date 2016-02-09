package com.softserveinc.booklibrary.session.persist.facade;

import java.util.List;

import org.richfaces.component.SortOrder;

import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.model.entity.Review;


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

	/**
	 * The method returns reviews for {@code Book}, result is limited and
	 * sorted. Limits sets by values {@code firstRow} and {@code countRows}
	 * passed by parameter. Result is sorted by {@code createdDate} in according
	 * to order {@link SortOrder} passed by parameter.
	 * 
	 * 
	 * @param book
	 *            - {@link Book} entity
	 * @param firstRow
	 *            - row number in database table (starts with 0, in dataBase
	 *            immutable value).
	 * @param countRows
	 *            - max count of objects that could be returned.
	 * @param order
	 *            - order for sorting {@link SortOrder}
	 * @return list - {@code List<Review>}
	 * @throws IllegalArgumentException
	 *             - if firstRow, countRows less that 0 or firstRow more that
	 *             countRows and if order not equal ascending or descending.
	 */
	List<Review> findReviewsForBook(Book book, int firstRow, int countRows, SortOrder order);

	/**
	 * The method returns List of all {@link Review} for {@link Book} passed by
	 * parameter.
	 * 
	 * @param book
	 *            entity.
	 * @return list of reviews.
	 */
	List<Review> findReviewsForBook(Book book);

	/**
	 * The methods gets count reviews for {@link Book} entity passed by
	 * parameter.
	 * 
	 * @param book
	 *            entity.
	 * @return int count of reviews
	 */
	int findCountReviewForBook(Book book);
	
	Review findLatestReviewForBook(Book book);
	
	List<Review> findLatestAddedReviews(int limit);

}
