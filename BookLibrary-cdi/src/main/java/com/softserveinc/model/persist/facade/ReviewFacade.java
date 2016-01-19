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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.softserveinc.exception.BookCatalogException;
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
		query.setParameter("par", book);
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
	public Review findById(String id)  {
		Review review = reviewHome.findByID(id);
		return review;
	}

	@Override
	public List<Review> findAll() {
		return reviewHome.findAll();
	}

	@Override
	public List<Review> findReviewsForBook(Book book, int firstRow, int countRows) {
		long start = System.currentTimeMillis();
		
		System.out.println("befor method findReviewsForBook");

		String quer = "SELECT DISTINCT r FROM Review r JOIN r.book b WHERE r.book like :par";
		Query query = entityManager.createQuery(quer);
		query.setParameter("par", book);
		query.setFirstResult(firstRow);
		query.setMaxResults(countRows);
		List<Review> list = query.getResultList();
		
		System.out.println("after method findReviewsForBook");
		return list;
	}

	@Override

	public int findCountReviewForBook(Book book) {
//		StringBuilder sbForDataTable = new StringBuilder();
//		sbForDataTable.append(SELECT).append(COUNT).append("( DISTINCT " + R + ") ").append(FROM);
//		sbForDataTable.append(Book.class.getName()).append(' ').append(B);
//		sbForDataTable.append(JOIN).append(R).append('.').append("book ");
		String quer = "SELECT COUNT(DISTINCT r) FROM Review r JOIN r.book b WHERE r.book like :par";
		Query query = entityManager.createQuery(quer);
		query.setParameter("par", book);
		long count;
		count = (long) query.getSingleResult();
		return (int) count;
	}

	@Override
	public int findCountBooksByRating(int rating) {
		String qu = "select COUNT(distinct r.book) from Review  r group by r.book having floor(avg(r.rating))= " + rating;
		Query query = entityManager.createQuery(qu);
		List<Integer> result = (List<Integer>) query.getResultList();
	
		return result.size();
	}

}
