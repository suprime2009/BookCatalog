package com.softserveinc.booklibrary.session.persist.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.richfaces.component.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.model.entity.Review;
import com.softserveinc.booklibrary.session.persist.facade.ReviewFacadeLocal;
import com.softserveinc.booklibrary.session.persist.facade.ReviewFacadeRemote;
import com.softserveinc.booklibrary.session.persist.home.ReviewHomeLocal;

/**
 * The {@code ReviewFacade} class is an implementation facade (read) operations
 * for {@link Review} entity. This class is @Stateless.
 *
 */
@Stateless
public class ReviewFacade implements ReviewFacadeLocal, ReviewFacadeRemote {

	private static Logger log = LoggerFactory.getLogger(ReviewFacade.class);

	@PersistenceContext
	EntityManager entityManager;

	@EJB
	private ReviewHomeLocal reviewHome;

	public ReviewFacade() {
	}

	@Override
	public List<Review> findAllReviewsByCommenter(String commenterName) {
		TypedQuery<Review> query = entityManager.createNamedQuery(Review.FIND_ALL_REVIEWS_BY_COMMENTER_NAME,
				Review.class);
		query.setParameter("par", commenterName);
		List<Review> list = query.getResultList();
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
		return reviewHome.findByID(id);
	}

	@Override
	public List<Review> findAll() {
		return reviewHome.findAll();
	}

	@Override
	public List<Review> findReviewsForBook(Book book, int firstRow, int countRows, SortOrder order) {
		long start = System.currentTimeMillis();
		log.debug("The method starts. Input values for method: firstRow={}, countRows={}, sortOrder={}, book={}",
				firstRow, countRows, order, book);
		if (firstRow < 0 && countRows < 0) {
			String errorMessage = "First row and count rows must not be negative values";
			log.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);
		}

		TypedQuery<Review> query = null;
		if (order.equals(SortOrder.ascending)) {
			query = entityManager.createNamedQuery(Review.FIND_REVIEWS_BY_BOOK_ORDER_ASC, Review.class);
		} else if (order.equals(SortOrder.descending)) {
			query = entityManager.createNamedQuery(Review.FIND_REVIEWS_BY_BOOK_ORDER_DESC, Review.class);
		} else {
			String errorMessage = "Sort order should be present and can't be unsorted.";
			log.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);
		}

		query.setParameter("book", book.getIdBook());
		query.setFirstResult(firstRow);
		query.setMaxResults(countRows);
		List<Review> list = query.getResultList();
		log.info("The method successfully finished. That took {} milliseconds. Has been found {} books",
				(System.currentTimeMillis() - start), list.size());
		return list;
	}

	@Override
	public List<Review> findReviewsForBook(Book book) {
		long start = System.currentTimeMillis();
		TypedQuery<Review> query = entityManager.createNamedQuery(Review.FIND_REVIEWS_BY_BOOK_ORDER_DESC, Review.class);
		query.setParameter("book", book.getIdBook());
		List<Review> list = query.getResultList();
		log.info("The method successfully finished. That took {} milliseconds. Has been found {} books",
				(System.currentTimeMillis() - start), list.size());
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
	public Review findLatestReviewForBook(Book book) {
		log.info("The method start. Inconing book {}", book);
		Query query = entityManager.createQuery(
				"SELECT r FROM Review r WHERE r.book = :bok AND r.createdDate = (SELECT MAX(r.createdDate) FROM Review r  WHERE r.book = :bok )");
		query.setParameter("bok", book);

		try {
			Review review = (Review) query.getSingleResult();
			log.info("The method finished. For book={} has been found review = {}", book, review);
			return review;
		} catch (NoResultException e) {
			log.error("The book= {} hasn't reviews.", book);
			return null;
		} catch (NonUniqueResultException e) {
			log.error("The book {} has more than one reviews, created at same time. The method returned one of them.", book);
			List<Review> list = query.getResultList();
			return list.get(0);
		}

	}

	@Override
	public List<Review> findLatestAddedReviews(int limit) {
		TypedQuery<Review> query = entityManager.createNamedQuery(Review.FIND_ALL_REVIEWS_SORTED_BY_DATE, Review.class);
		query.setMaxResults(limit);
		List<Review> review = query.getResultList();
		log.info("The method done. Has been found {} reviews.", review.size());
		return review;
	}

}
