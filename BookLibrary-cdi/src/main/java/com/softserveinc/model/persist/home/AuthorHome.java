package com.softserveinc.model.persist.home;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.model.persist.entity.Author;

/**
 * AuthorHome class is an implementation of CRUD operations for Author entity.
 * This class is @Stateless.
 *
 */
@Stateless
public class AuthorHome implements AuthorHomeLocal, AuthorHomeRemote {

	private static Logger log = LoggerFactory.getLogger(AuthorHome.class);
	
	@PersistenceContext(unitName = "primary")
	private EntityManager entityManager;

	public Author create(Author object) {
		entityManager.persist(object);
		log.info("Entity {} has been successfully created", object);
		return object;
	}

	public void update(Author object) {
		entityManager.merge(object);
		log.info("Entity {} has been successfully updated", object);
	}

	public void delete(Author object) {
		entityManager.remove(object);
		log.info("Entity {} has been successfully deleted", object);
	}

	public List<Author> findAll() {
		TypedQuery<Author> query = entityManager.createNamedQuery(Author.FIND_ALL_AUTHORS, Author.class);
		List<Author> results = query.getResultList();
		log.info("List<Author> has been successfully created. Method findAll() finished");
		return results;
	}

	public Author findByID(String id) {
		Author object = (Author) entityManager.find(Author.class, id);
		log.info("Entity {} has been successfully found by id = {}", object, id);
		return object;
	}

}
