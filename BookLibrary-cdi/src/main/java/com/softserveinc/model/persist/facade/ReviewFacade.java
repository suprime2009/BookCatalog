package com.softserveinc.model.persist.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.Review;

@Stateless
public class ReviewFacade implements ReviewFacadeLocal, ReviewFacadeRemote {

	private static Logger log = LoggerFactory.getLogger(ReviewFacade.class);
	@PersistenceContext(unitName = "primary")
	EntityManager entityManager;

	public ReviewFacade() {
	}

	public List<Review> findAllReviewsByCommenter(String commenterName) {
		Query query = entityManager.createNamedQuery(Review.FIND_ALL_REVIEWS_BY_COMMENTER_NAME);
		query.setParameter("par", commenterName);
		return (List<Review>) query.getResultList();
	}

	@Override
	public double findAverageRatingForBook(Book book) {
		Query query = entityManager.createNamedQuery(Review.FIND_AVERAGE_RATING_FOR_BOOK);
		query.setParameter("par", book);
		double averageRating = (double) query.getSingleResult();
		return averageRating;
	}

}
