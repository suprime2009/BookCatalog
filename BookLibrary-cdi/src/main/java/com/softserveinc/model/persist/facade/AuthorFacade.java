package com.softserveinc.model.persist.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.home.AuthorHomeLocal;
/**
 * AuthorFacade class is an implementation facade operations for Author entity.
 * This class is @Stateless.
 *
 */
@Stateless
public class AuthorFacade implements AuthorFacadeLocal, AuthorFacadeRemote{
	
	private static Logger log = LoggerFactory.getLogger(AuthorFacade.class);
	
	@PersistenceContext(unitName = "primary")
	EntityManager entityManager;
	
	@EJB
	AuthorHomeLocal authorHomeLocal;
	
	public AuthorFacade() {
	
	}

	@Override
	public List<Author> findAllAuthorsByBook(Book book) {
		long methodStart = System.currentTimeMillis();
		Query query = entityManager.createNamedQuery(Author.FIND_ALL_AUTHORS_BY_BOOK);
		query.setParameter("bk", book);
		List<Author> list = (List<Author>) query.getResultList();
		long methodEnd = System.currentTimeMillis();
		long duration = methodEnd - methodStart;
		log.info("Method findAllAuthorsByBook for book={}, finished for {} millis. "
				+ "Successfully returns List<Author> size={}", book, duration, list.size());
		return list;
	}

	@Override
	public Author findAuthorByFullName(String firstName, String secondName) {
		Query query = entityManager.createNamedQuery(Author.FIND_AUTHOR_BY_FULL_NAME);
		query.setParameter("fn", firstName);
		query.setParameter("sn", secondName);
		Author object = (Author) query.getSingleResult();
		log.info("Method findAuthorByFullName finished. By firstName={} and lastName={}"
				+ "has been found author={}",firstName, secondName, object);
		return object;
	}

//	@Override
//	public List<Author> findAuthorsByAverageRating(String rating) {
//		return null;
//	}

	@Override
	public Author findById(String id) {
		return authorHomeLocal.findByID(id);
	}

	@Override
	public List<Author> findAll() {
		return authorHomeLocal.findAll();
	}

}

