package com.softserveinc.model.persist.home;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		log.info("Entity {} has been successfully created", object);
		return object;
	}

	public void update(Review object) {
		entityManager.merge(object);
		log.info("Entity {} has been successfully updated", object);
	}

	public void delete(Review object) {
		entityManager.remove(object);
		log.info("Entity {} has been successfully deleted", object);
	}
	
	public Review findByID(String id) {
		Review object = (Review) entityManager.find(Review.class, id);
		log.info("Entity {} has been successfully found by id ={} ", object, id);
		return object;
	}

	public List<Review> findAll() {
		TypedQuery<Review> query = entityManager.createNamedQuery(Review.FIND_ALL_REVIEWS, Review.class);
		List<Review> results = query.getResultList();
		log.info("List<Review> has been successfully created. Method findAll() finished");
		return results;
	}
}
