package com.softserveinc.model.persist.home;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.model.persist.entity.Author;
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

	@Transactional
	public void delete(Book object) {
		object = entityManager.getReference(Book.class, object.getIdBook());
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

	@Override
	public void bulkRemove(List<Book> list) {
		Query query = entityManager.createNamedQuery(Book.BULK_REMOVE);
		query.setParameter("list", list);
		query.executeUpdate();
		log.info("The method done.");
		
	}
}