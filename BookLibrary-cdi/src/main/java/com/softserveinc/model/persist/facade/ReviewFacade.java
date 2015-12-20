package com.softserveinc.model.persist.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		log.info("Method findAllReviewsByCommenter: "
				+ "By commenterName={} has been found {} reviews",commenterName, list.size());
		return list;
	}

	@Override
	public double findAverageRatingForBook(Book book) {
		Query query = entityManager.createNamedQuery(Review.FIND_AVERAGE_RATING_FOR_BOOK);
		query.setParameter("par", book);
		if (query.getSingleResult() == null) {
			log.info("Method findAverageRatingForBook: book={} has no one review, returned 0", book);
			return 0;
		} else {
			log.info("Method findAverageRatingForBook: found average rating "
					+ "for book={}", book);
			return (double) query.getSingleResult();
		}
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
