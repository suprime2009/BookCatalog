package com.softserveinc.model.persist.home;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.model.persist.entity.Book;

/**
  * BookHome class is an implementation of CRUD operations for Book entity.
 * This class is @Stateless.
 *
 */
@Stateless
public class BookHome implements BookHomeLocal, BookHomeRemote {
	
	private static Logger log = LoggerFactory.getLogger(BookHome.class);
	
	@PersistenceContext(unitName = "primary")
	private EntityManager entityManager;

	public BookHome() {
	}

	public Book create(Book object) {
		entityManager.persist(object);
		log.info("Entity {} has been successfully created", object);
		return object;
	}

	public void update(Book object) {
		entityManager.merge(object);
		log.info("Entity {} has been successfully updated", object);
	}

	public void delete(Book object) {
		entityManager.remove(object);
		log.info("Entity {} has been successfully deleted", object);

	}

	public Book findByID(String id) {
		Book object = (Book) entityManager.find(Book.class, id);
		log.info("Entity {} has been successfully found by id = {}",object, id);
		return object;
		
	}

	public List<Book> findAll() {
		TypedQuery<Book> query = entityManager.createNamedQuery(Book.FIND_ALL_BOOKS, Book.class);
		List<Book> results = query.getResultList();
		log.info("List<Author> has been successfully created. Method findAll() finished");
		return results;
	}
}