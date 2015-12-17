package com.softserveinc.model.persist.home;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.model.persist.entity.Book;

@Stateless
public class BookHome implements BookHomeLocal, BookHomeRemote {
	
	private static Logger log = LoggerFactory.getLogger(BookHome.class);
	@PersistenceContext(unitName = "primary")
	EntityManager entityManager;

	public BookHome() {
	}

	public Book create(Book object) {
		entityManager.persist(object);
		log.info("Entity " + object + "was successfully created");
		return object;
	}

	public void update(Book object) {
		entityManager.merge(object);
		log.info("Entity " + object + "was successfully updated");
	}

	public void delete(Book object) {
		entityManager.remove(object);
		log.info("Entity " + object + "was successfully deleted");

	}

	public Book findByID(String id) {
		Book object = (Book) entityManager.find(Book.class, id);
		log.info("Entity " + object + "was successfully found by id = " + id);
		return object;
		
	}

	public List<Book> findAll() {
		TypedQuery<Book> query = entityManager.createNamedQuery(Book.FIND_ALL_BOOKS, Book.class);
		List<Book> results = query.getResultList();
		log.info("List<Author> was successfully created. Method findAll() finished");
		System.out.println("Book Home");
		System.out.println("Book Home");
		System.out.println("Book Home");
		System.out.println("Book Home");
		System.out.println("Book Home");
		return results;
	}
}