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

@Stateless
public class BookFacade implements BookFacadeLocal, BookFacadeRemote {

	private static Logger log = LoggerFactory.getLogger(BookFacade.class);
	
	@PersistenceContext(unitName = "primary")
	EntityManager entityManager;
	
	@EJB
	BookHomeLocal bookHomeLocal;
	
	public BookFacade() {}

	public List<Book> findBooksWithRating(String rating) {
		Query query = entityManager.createNamedQuery(Book.FIND_BOOKS_WITH_RATING);
		query.setParameter("rat", rating);
		List<Book> list = (List<Book>) query.getResultList();
		log.info("By rating= " + rating + " were found List<Book>. Count books= " + list.size());
		return list;
	}

	public List<Book> findBookByName(String name) {
		Query query = entityManager.createNamedQuery(Book.FIND_BOOK_BY_NAME);
		query.setParameter("nam", name);
		List<Book> list = (List<Book>) query.getResultList();
		log.info("By book name= {} has been found Book= {}", name, list);
		return list;
	}

	public Book findBookByISNBN(String isbn) {
		Query query = entityManager.createNamedQuery(Book.FIND_BOOK_BY_ISNBN);
		query.setParameter("isb", isbn);
		Book object = (Book) query.getSingleResult();
		log.info("By isbn book number= " + isbn + "was found Book= " + object);
		return object;
	}

	public List<Book> findBooksByPublisher(String publisher) {
		Query query = entityManager.createNamedQuery(Book.FIND_BOOKS_BY_PUBLISHER);
		query.setParameter("pub", publisher);
		List<Book> list = (List<Book>) query.getResultList();
		log.info("By book publisher name= " + publisher + "were found List<Book>. Count books= " + list.size());
		return list;
	}

	public List<Book> findBooksByAuthor(Author author) {
		Query query = entityManager.createNamedQuery(Book.FIND_BOOKS_BY_AUTHOR);
		query.setParameter("auth", author);
		List<Book> list = (List<Book>) query.getResultList();

		return list;
	}
	
	public Book findById(String id) {
		return bookHomeLocal.findByID(id);
	}
	
	public List<Book> findAll() {
		System.out.println("Book FACADE");
		System.out.println("Book FACADE");
		System.out.println("Book FACADE");
		System.out.println("Book FACADE");
		System.out.println("Book FACADE");
		System.out.println("Book FACADE");
		System.out.println("Book FACADE");
		return bookHomeLocal.findAll();
	}
}
