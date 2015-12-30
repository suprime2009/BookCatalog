package com.softserveinc.model.persist.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.Review;

/**
 * ReviewFacade class is an implementation facade operations for Review entity.
 * This class is @Stateless.
 *
 */
@Stateless
public class ReviewFacade implements ReviewFacadeLocal, ReviewFacadeRemote {

	private static Logger log = LoggerFactory.getLogger(ReviewFacade.class);

	@PersistenceContext(unitName = "primary")
	EntityManager entityManager;

	@EJB
	private ReviewFacadeLocal reviewFacadeLocal;

	public ReviewFacade() {
	}

	@Override
	public List<Review> findAllReviewsByCommenter(String commenterName) {
		Query query = entityManager.createNamedQuery(Review.FIND_ALL_REVIEWS_BY_COMMENTER_NAME);
		query.setParameter("par", commenterName);
		List<Review> list = (List<Review>) query.getResultList();
		log.info("Method findAllReviewsByCommenter: " + "By commenterName={} has been found {} reviews", commenterName,
				list.size());
		return list;
	}

	@Override
	public Double findAverageRatingForBook(Book book) {
		Query query = entityManager.createNamedQuery(Review.FIND_AVERAGE_RATING_FOR_BOOK);
		query.setParameter("par", book);
		Optional<Double> aveRatingOptional = (Optional<Double>) query.getSingleResult();
		double aveRating = aveRatingOptional.or(0.0).doubleValue();
		log.info("Method findAverageRatingForBook: found average rating " + "for book={}", book);
		return aveRating;
	}

	@Override
	public Author findById(String id) {
		return reviewFacadeLocal.findById(id);
	}

	@Override
	public List<Author> findAll() {
		return reviewFacadeLocal.findAll();
	}

}
