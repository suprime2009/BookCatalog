package com.softserveinc.model.persist.facade;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.richfaces.component.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.softserveinc.exception.BookCatalogException;
import com.softserveinc.exception.ReviewFacadeException;
import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.Review;
import com.softserveinc.model.persist.home.ReviewHomeLocal;
import com.softserveinc.model.session.util.DataTableSearchHolder;
import com.softserveinc.model.session.util.ReviewRatingFieldsEnum;
import com.softserveinc.model.session.util.SQLCommandConstants;

/**
 * ReviewFacade class is an implementation facade operations for Review entity.
 * This class is @Stateless.
 *
 */
@Stateless
public class ReviewFacade implements ReviewFacadeLocal, ReviewFacadeRemote, SQLCommandConstants {

	private static Logger log = LoggerFactory.getLogger(ReviewFacade.class);

	@PersistenceContext(unitName = "primary")
	EntityManager entityManager;

	@EJB
	private ReviewHomeLocal reviewHome;

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
		query.setParameter("par", book.getIdBook());
		Double aveRating = null;
		try {
			aveRating = (Double) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		log.info("Method findAverageRatingForBook: found average rating " + "for book={}", book);
		return aveRating;
	}

	@Override
	public Review findById(String id) {
		Review review = reviewHome.findByID(id);
		return review;
	}

	@Override
	public List<Review> findAll() {
		return reviewHome.findAll();
	}

	@Override
	public List<Review> findReviewsForBook(Book book, int firstRow, int countRows, SortOrder order)
			throws ReviewFacadeException {
		long start = System.currentTimeMillis();
		log.debug("The method starts. Input values for method: firstRow={}, countRows={}, sortOrder={}, book={}",
				firstRow, countRows, order, book);
		if (firstRow < 0 && countRows < 0) {
			String errorMessage = "First row and count rows must not be negative values";
			log.error(errorMessage);
			throw new ReviewFacadeException(errorMessage);
		}

		TypedQuery<Review> query = null;
		if (order.equals(SortOrder.ascending)) {
			query = (TypedQuery<Review>) entityManager.createNamedQuery(Review.FIND_REVIEWS_BY_BOOK_ORDER_ASC);
		} else if (order.equals(SortOrder.descending)) {
			query = (TypedQuery<Review>) entityManager.createNamedQuery(Review.FIND_REVIEWS_BY_BOOK_ORDER_DESC);
		} else {
			String errorMessage = "Sort order should be present and can't be unsorted.";
			log.error(errorMessage);
			throw new ReviewFacadeException(errorMessage);
		}

		query.setParameter("book", book.getIdBook());
		query.setFirstResult(firstRow);
		query.setMaxResults(countRows);
		List<Review> list = query.getResultList();
		long end = System.currentTimeMillis();
		log.info("The method successfully finished. That took {} milliseconds. Has been found {} books", (end - start),
				list.size());
		return list;
	}

	@Override
	public List<Review> findReviewsForBook(Book book) {
		long start = System.currentTimeMillis();
		TypedQuery<Review> query = (TypedQuery<Review>) entityManager
				.createNamedQuery(Review.FIND_REVIEWS_BY_BOOK_ORDER_DESC);
		query.setParameter("book", book.getIdBook());
		List<Review> list = query.getResultList();
		long end = System.currentTimeMillis();
		log.info("The method successfully finished. That took {} milliseconds. Has been found {} books", (end - start),
				list.size());
		return list;
	}

	@Override
	public int findCountReviewForBook(Book book) {
		Query query = entityManager.createNamedQuery(Review.FIND_COUNT_REVIEWS_FOR_BOOK);
		query.setParameter("par", book.getIdBook());
		try {
			long count = (long) query.getSingleResult();
			log.info("The method done. {} review has been found for book={}", count, book);
			return (int) count;
		} catch (NoResultException e) {
			log.info("The method done. For book {} no one review found.", book);
			return 0;
		}
	}

	@Override
	public int findCountBooksByRating(int rating) {
		String qu = "select COUNT(distinct r.book) from Review  r group by r.book having floor(avg(r.rating))= "
				+ rating;
		Query query = entityManager.createQuery(qu);
		List<Integer> result = (List<Integer>) query.getResultList();

		return result.size();
	}

}
