package com.softserveinc.model.persist.facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.AuthorFieldHolder;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.BookFieldHolder;
import com.softserveinc.model.persist.entity.ReviewFieldHolder;
import com.softserveinc.model.persist.home.AuthorHomeLocal;
import com.softserveinc.model.session.util.DataTableSearchHolder;
import com.softserveinc.model.session.util.SQLCommandConstants;
/**
 * AuthorFacade class is an implementation facade operations for Author entity.
 * This class is @Stateless.
 *
 */
@Stateless
public class AuthorFacade implements AuthorFacadeLocal, AuthorFacadeRemote, SQLCommandConstants{
	
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
	public Author findAuthorByFullName(String secondName, String firstName) {
		Author object = null;
		Query query = entityManager.createNamedQuery(Author.FIND_AUTHOR_BY_FULL_NAME);
		query.setParameter("fn", firstName);
		query.setParameter("sn", secondName);
		try {
		object = (Author) query.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
		log.info("Method findAuthorByFullName finished. By firstName={} and lastName={} "
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



	@Override
	public List<String> findAuthorsFullNamesForAutocomplete(String prefix) {
		Query query = entityManager.createNamedQuery(Author.FIND_AUTHORS_NAMES_FOR_AUTOCOMPLETE);
		query.setParameter("sn", prefix + '%');
		query.setMaxResults(5);
		List<String> list =  query.getResultList();

		log.info("Method done.");
		return list;
	}
	
	private void appendSelectQueryPart(StringBuilder sbForDataTable) {
		sbForDataTable.append(String.format("%s %s, ", SELECT, A));
		sbForDataTable.append(String.format("%s(%s %s), ", COUNT, DISTINCT, B));
		sbForDataTable.append(String.format("%s(%s %s), ", COUNT, DISTINCT, R));
		sbForDataTable.append(String.format("%s(%s.%s) ", AVG,  R, "rating"));
		sbForDataTable.append(String.format("%s %s %S", FROM, Author.class.getName(), A));
		sbForDataTable.append(String.format("%s %s.%s %S", LEFT_JOIN, A, AuthorFieldHolder.BOOKS.getBusinessView(), B));
		sbForDataTable.append(String.format("%s %s.%s %S", LEFT_JOIN, B, BookFieldHolder.REVIEWS.getBusinessView(), R));
		
	}
	
	private void appendWhereQueryPart(StringBuilder sbForDataTable, DataTableSearchHolder dataTableSearchHolder) {
		if (dataTableSearchHolder.getFilterValues().containsKey(AuthorFieldHolder.FULL_NAME)) {
			String filterValue = (String) dataTableSearchHolder.getFilterValues().get(AuthorFieldHolder.FULL_NAME);
			sbForDataTable.append(WHERE).append(A + '.').append(AuthorFieldHolder.FIRST_NAME.getBusinessView()).append(' ').append(LIKE)
			.append("'").append(filterValue).append("%'");
			sbForDataTable.append(OR).append(A + '.').append(AuthorFieldHolder.SECOND_NAME.getBusinessView()).append(' ').append(LIKE)
			.append("'").append(filterValue).append("%'");
		}
	}
	


	@Override
	public List<Object[]> findAuthorsForDataTable(DataTableSearchHolder dataTableSearchHolder) {
		long start = System.currentTimeMillis();
		log.info("The method starts. Passed value to method={}", dataTableSearchHolder);
	
		StringBuilder sbForDataTable = new StringBuilder();
		appendSelectQueryPart(sbForDataTable);
		appendWhereQueryPart(sbForDataTable, dataTableSearchHolder);
		sbForDataTable.append(" GROUP BY a");
	
		
		Query query = entityManager.createQuery(sbForDataTable.toString());
		query.setFirstResult(dataTableSearchHolder.getFirstRow());
		query.setMaxResults(dataTableSearchHolder.getRowsPerPage());
		List<Object[]> result = query.getResultList();
		
		return result;
	}

	@Override
	public int findCountAuthorsForDataTable(DataTableSearchHolder dataTableSearchHolder) {
		StringBuilder sbForDataTable = new StringBuilder();
		sbForDataTable.append("SELECT COUNT(DISTINCT a) ");
		

		sbForDataTable.append(String.format("%s %s %S", FROM, Author.class.getName(), A));
		sbForDataTable.append(String.format("%s %s.%s %S", LEFT_JOIN, A, AuthorFieldHolder.BOOKS.getBusinessView(), B));
		sbForDataTable.append(String.format("%s %s.%s %S", LEFT_JOIN, B, BookFieldHolder.REVIEWS.getBusinessView(), R));
		appendWhereQueryPart(sbForDataTable, dataTableSearchHolder);
		sbForDataTable.append(" GROUP BY a");

		Query query = entityManager.createQuery(sbForDataTable.toString());

		List<Long> findedValues = query.getResultList();
		int count = findedValues.size();


		
		return  count;
	}

	@Override
	public Double findAuthorAvegareRating(Author author) {
		Query query = entityManager.createNamedQuery(Author.FIND_AUTHOR_RATING);
		query.setParameter("author", author);
		Double result = (Double) query.getSingleResult();
		return result;
	}

}

