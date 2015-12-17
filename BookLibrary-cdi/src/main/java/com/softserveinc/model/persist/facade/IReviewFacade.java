package com.softserveinc.model.persist.facade;

import java.util.List;

import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.Review;

/**
 * Describes all facade methods for Review entity.
 *
 */
public interface IReviewFacade {
	
	/**
	 * Method returns all Review by commenter.
	 * @param commenterName
	 * @return List<Review>
	 */
	List<Review> findAllReviewsByCommenter(String commenterName);
	
	double findAverageRatingForBook(Book book);

}
