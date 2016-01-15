package com.softserveinc.model.persist.home;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.exception.BookCatalogException;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.Review;

/**
 * ReviewHome class is an implementation of CRUD operations for Review entity.
 * This class is @Stateless.
 *
 */
@Stateless
public class ReviewHome implements ReviewHomeLocal, ReviewHomeRemote {

	private static Logger log = LoggerFactory.getLogger(ReviewHome.class);

	@PersistenceContext(unitName = "primary")
	EntityManager entityManager;

	public ReviewHome() {
	}

	public Review create(Review object) {
		entityManager.persist(object);
		log.info("Review= {} has been successfully created", object);
		return object;
	}

	public void update(Review object) {
		entityManager.merge(object);
		log.info("Entity {} has been successfully updated", object);
	}

	public void delete(Review object) {
		object = entityManager.getReference(Review.class, object.getIdreview());
		entityManager.remove(object);

		log.info("Entity {} has been successfully deleted", object);
	}

	public Review findByID(String id) {
		Review object = null;
		String erorrMessage = "";
			if (id == null) {
				erorrMessage = "Propery id could not be null";
				throw new IllegalArgumentException(erorrMessage);
			}
			object = entityManager.find(Review.class, id);
			try {
			if (object == null) {
				erorrMessage = "Passed id is not present in database.";
				throw new BookCatalogException(erorrMessage);
			}
			log.info("Entity {} has been successfully found by id ={} ", object, id);
			} catch (BookCatalogException e) {
				log.error("By id={}, has not found Review. {}", id, e.getMessage());
			}
		return object;
	}

	public List<Review> findAll() {
		TypedQuery<Review> query = entityManager.createNamedQuery(Review.FIND_ALL_REVIEWS, Review.class);
		List<Review> results = query.getResultList();
		log.info("List<Review> has been successfully created. Method findAll() finished");
		return results;
	}
}
