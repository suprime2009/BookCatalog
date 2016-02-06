package com.softserveinc.booklibrary.session.persist.home.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.session.persist.home.BookHomeLocal;
import com.softserveinc.booklibrary.session.persist.home.BookHomeRemote;


/**
 * The {@code BookHome} is a bean implementation class for homes operations for
 * {@link Book} entity. Bean exposes local business view {@link BookHomeLocal}
 * and remote business view {@link BookHomeRemote}. This bean is a @Stateless
 * bean.
 *
 */
@Stateless
public class BookHome implements BookHomeLocal, BookHomeRemote {

	private static Logger log = LoggerFactory.getLogger(BookHome.class);

	@PersistenceContext(unitName="primary")
	private EntityManager entityManager;

	public BookHome() {
	}

	public void create(Book object) {
		entityManager.persist(object);
		log.info("Entity {} has been successfully created", object);
	}

	public void update(Book object) {
		entityManager.merge(object);
		log.info("Entity {} has been successfully updated", object);
	}

	public void delete(Book object) {
		object = entityManager.getReference(Book.class, object.getIdBook());
		entityManager.remove(object);
		log.info("Entity {} has been successfully deleted", object);
	}

	public Book findByID(String id) {
		Book object = entityManager.find(Book.class, id);
		if (object == null) {
			log.error("By id {} nothing found.", id);
		} else {
			log.info("Entity {} has been successfully found by id = {}", object, id);
		}
		return object;
	}

	public List<Book> findAll() {
		TypedQuery<Book> query = entityManager.createNamedQuery(Book.FIND_ALL_BOOKS, Book.class);
		List<Book> results = new ArrayList<Book>();
		results = query.getResultList();
		log.info("Method finished. Has been found {} books.", results.size());
		return results;
	}

	
	@Override
	@TransactionAttribute(TransactionAttributeType.MANDATORY)
	public int bulkRemove(List<Book> list) {
		Query query = entityManager.createNamedQuery(Book.BULK_REMOVE);
		query.setParameter("list", list);
		int count = query.executeUpdate();
		log.info("The method finished, {} were books removed.", count);
		return count;
	}

}