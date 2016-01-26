package com.softserveinc.model.persist.home;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.model.persist.entity.Review;

/**
 * The {@code ReviewHome} is a bean implementation class for homes operations
 * for {@link Review} entity. Bean exposes local business view
 * {@link ReviewHomeLocal} and remote business view {@link ReviewHomeRemote}.
 * This bean is a @Stateless bean.
 *
 */
@Stateless
public class ReviewHome implements ReviewHomeLocal, ReviewHomeRemote {

	private static Logger log = LoggerFactory.getLogger(ReviewHome.class);

	@PersistenceContext(unitName = PERSISTANCE_UNIT_PRIMARY)
	EntityManager entityManager;

	public ReviewHome() {

	}

	public void create(Review object) {
		entityManager.persist(object);
		log.info("Entity {} has been successfully created", object);
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
		Review object = entityManager.find(Review.class, id);
		if (object == null) {
			log.error("By id {} nothing found.", id);
		} else {
			log.info("Entity {} has been successfully found by id = {}", object, id);
		}
		return object;
	}

	public List<Review> findAll() {
		TypedQuery<Review> query = entityManager.createNamedQuery(Review.FIND_ALL_REVIEWS, Review.class);
		List<Review> results = new ArrayList<Review>();
		results = query.getResultList();
		log.info("Method finished. Has been found {} reviews.", results.size());
		return results;
	}
}
