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
import com.softserveinc.model.session.util.DataTableSearchHolder;
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

	@Override
	public List<Review> findReviewsForBook(DataTableSearchHolder dataTableSearchHolder, Book book) {
		long start = System.currentTimeMillis();

		StringBuilder sbForDataTable = new StringBuilder();
		sbForDataTable.append(SELECT).append(R).append(FROM);
		sbForDataTable.append(Book.class.getName()).append(' ').append(B);
		sbForDataTable.append(JOIN).append(R).append('.').append("book ");
		sbForDataTable.append(ORDER_BY).append(B).append('.').append("book ").append(DESC);
		Query query = entityManager.createQuery(sbForDataTable.toString());
		query.setFirstResult(dataTableSearchHolder.getFirstRow());
		query.setMaxResults(dataTableSearchHolder.getRowsPerPage());
		List<Review> list = query.getResultList();
		return list;
	}

	@Override
	public int findCountReviewForBook(DataTableSearchHolder dataTableSearchHolder, Book book) {
		StringBuilder sbForDataTable = new StringBuilder();
		sbForDataTable.append(SELECT).append(COUNT).append("( DISTINCT " + R + ") ").append(FROM);
		sbForDataTable.append(Book.class.getName()).append(' ').append(B);
		sbForDataTable.append(JOIN).append(R).append('.').append("book ");
		Query query = entityManager.createQuery(sbForDataTable.toString());
		long count;
		count = (long) query.getSingleResult();
		return (int) count;
	}

}
