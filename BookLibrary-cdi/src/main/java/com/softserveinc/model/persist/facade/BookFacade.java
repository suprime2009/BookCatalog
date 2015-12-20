package com.softserveinc.model.persist.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.home.BookHome;
import com.softserveinc.model.persist.home.BookHomeLocal;

/**
 * BookFacade class is an implementation facade operations for Book entity.
 * This class is @Stateless.
 *
 */
@Stateless
public class BookFacade implements BookFacadeLocal, BookFacadeRemote {

	private static Logger log = LoggerFactory.getLogger(BookFacade.class);
	
	@PersistenceContext(unitName = "primary")
	EntityManager entityManager;
	
	@EJB
	BookHomeLocal bookHomeLocal;
	
	public BookFacade() {
		
	}

//	public List<Book> findBooksWithRating(int rating) {
//		Query query = entityManager.createNamedQuery(Book.FIND_BOOKS_WITH_RATING);
//		query.setParameter("rat", rating);
//		List<Book> list = (List<Book>) query.getResultList();
//		log.info("Method findBooksWithRating: by rating={} has been found {} books.");
//		return list;
//	}

	@Override
	public List<Book> findBookByName(String name) {
		Query query = entityManager.createNamedQuery(Book.FIND_BOOK_BY_NAME);
		query.setParameter("nam", name);
		List<Book> list = (List<Book>) query.getResultList();
		log.info("By book name= {} has been found books. Count books= {}", name, list.size());
		return list;
	}

	@Override
	public Book findBookByISNBN(String isbn) {
		Query query = entityManager.createNamedQuery(Book.FIND_BOOK_BY_ISNBN);
		query.setParameter("isb", isbn);
		Book object = (Book) query.getSingleResult();
		log.info("Method findBookByISNBN: By findBookByISNBN: isbn book number= {} has been found Book= {}", isbn, object);
		return object;
	}

	@Override
	public List<Book> findBooksByPublisher(String publisher) {
		Query query = entityManager.createNamedQuery(Book.FIND_BOOKS_BY_PUBLISHER);
		query.setParameter("pub", publisher);
		List<Book> list = (List<Book>) query.getResultList();
		log.info("Method findBooksByPublisher: By book publisher name= {} has been found books."
				+ " Count books={} ", publisher, list.size());
		return list;
	}

	@Override
	public List<Book> findBooksByAuthor(Author author) {
		Query query = entityManager.createNamedQuery(Book.FIND_BOOKS_BY_AUTHOR);
		query.setParameter("auth", author);
		List<Book> list = (List<Book>) query.getResultList();
		log.info("Method findBooksByAuthor: By author={} has been found {} books", author, list.size());
		return list;
	}
	
	@Override
	public Book findById(String id) {
		return bookHomeLocal.findByID(id);
	}
	
	@Override
	public List<Book> findAll() {
		return bookHomeLocal.findAll();
	}
}
