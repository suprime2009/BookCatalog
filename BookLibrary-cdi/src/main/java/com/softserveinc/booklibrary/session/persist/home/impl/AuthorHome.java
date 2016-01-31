package com.softserveinc.booklibrary.session.persist.home.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.session.persist.home.AuthorHomeLocal;
import com.softserveinc.booklibrary.session.persist.home.AuthorHomeRemote;

/**
 * The {@code AuthorHome} is a bean implementation class for homes operations
 * for {@link Author} entity. Bean exposes local business view
 * {@link AuthorHomeLocal} and remote business view {@link AuthorHomeRemote}.
 * This bean is a @Stateless bean.
 *
 */
@Stateless
public class AuthorHome implements AuthorHomeLocal, AuthorHomeRemote {

	private static Logger log = LoggerFactory.getLogger(AuthorHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	@EJB
	AuthorHomeLocal authorHome;

	public void create(Author object) {
		entityManager.persist(object);
		log.info("Entity {} has been successfully created", object);
	}

	public void update(Author object) {
		entityManager.merge(object);
		log.info("Entity {} has been successfully updated", object);
	}

	public void delete(Author object) {
		object = entityManager.getReference(Author.class, object.getIdAuthor());
		entityManager.remove(object);
		log.info("Entity {} has been successfully deleted", object);
	}

	public List<Author> findAll() {
		TypedQuery<Author> query = entityManager.createNamedQuery(Author.FIND_ALL_AUTHORS, Author.class);
		List<Author> results = new ArrayList<Author>();
		results = query.getResultList();
		log.info("Method finished. Has been found {} authors.", results.size());
		return results;
	}

	public Author findByID(String id) {

		Author object = entityManager.find(Author.class, id);
		if (object == null) {
			log.error("By id {} nothing found.", id);
		} else {
			log.info("Entity {} has been successfully found by id = {}", object, id);
		}
		return object;
	}

	@Override
	public void bulkRemove(List<Author> list) {
		Query query = entityManager.createNamedQuery(Author.BULK_REMOVE);
		query.setParameter("list", list);
		int count = query.executeUpdate();
		log.info("The method finished, {} were authors removed.", count);

	}

}
