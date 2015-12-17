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
import com.softserveinc.model.persist.home.AuthorHome;
import com.softserveinc.model.persist.home.AuthorHomeLocal;
/**
 * JAVADOC!!!!!!!!!!!!!!!!!!!!
 * for everybody
 * @author Pavel
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
		Query query = entityManager.createNamedQuery(Author.FIND_ALL_AUTHORS_BY_BOOK);
		query.setParameter("bk", book);
		List<Author> list = (List<Author>) query.getResultList();
		log.info("Method findAllAuthorsByBook(Book book) finished. Successfully returns List<Author>");
		return list;
	}

	@Override
	public Author findAuthorByFullName(String firstName, String secondName) {
		Query query = entityManager.createNamedQuery(Author.FIND_AUTHOR_BY_FULL_NAME);
		query.setParameter("fn", firstName);
		query.setParameter("sn", secondName);
		Author object = (Author) query.getSingleResult();
		log.info("By firstName= " + firstName + ", secondName= " + secondName + "found Author=" + object);
		return object;
	}

	@Override
	public List<Author> findAuthorsByAverageRating(String rating) {
		return null;
	}

	@Override
	public Author findById(String id) {
		return authorHomeLocal.findByID(id);
	}

	@Override
	public List<Author> findAll() {
		return authorHomeLocal.findAll();
	}

}

