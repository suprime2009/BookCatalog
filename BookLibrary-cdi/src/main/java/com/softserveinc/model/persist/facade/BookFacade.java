package com.softserveinc.model.persist.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.text.html.HTMLDocument.HTMLReader.PreAction;

import org.richfaces.component.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.AuthorFieldHolder;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.BookFieldHolder;
import com.softserveinc.model.persist.entity.EntityFieldHolder;
import com.softserveinc.model.persist.entity.Review;
import com.softserveinc.model.persist.home.BookHomeLocal;
import com.softserveinc.model.session.util.DataTableSearchHolder;
import com.softserveinc.model.session.util.SQLCommandConstants;

/**
 * BookFacade class is an implementation facade operations for Book entity. This
 * class is @Stateless.
 *
 */
@Stateless
public class BookFacade implements BookFacadeLocal, BookFacadeRemote, SQLCommandConstants {

	private static Logger log = LoggerFactory.getLogger(BookFacade.class);

	@PersistenceContext(unitName = "primary")
	public EntityManager entityManager;

	@EJB
	BookHomeLocal bookHomeLocal;

	public BookFacade() {
	}

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
		Book object = null;
		try {
			object = (Book) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		log.info("By findBookByISNBN: isbn book number= {} has been found Book= {}", isbn, object);
		return object;
	}

	@Override
	public List<Book> findBooksByPublisher(String publisher) {
		Query query = entityManager.createNamedQuery(Book.FIND_BOOKS_BY_PUBLISHER);
		query.setParameter("pub", publisher);
		List<Book> list = (List<Book>) query.getResultList();
		log.info("By book publisher name= {} has been found books." + " Count books={} ", publisher, list.size());
		return list;
	}

	@Override
	public List<Book> findBooksByAuthor(Author author) {
		Query query = entityManager.createNamedQuery(Book.FIND_BOOKS_BY_AUTHOR);
		query.setParameter("auth", author);
		List<Book> list = (List<Book>) query.getResultList();
		log.info("By author={} has been found {} books", author, list.size());
		return list;
	}

	@Override
	public Book findById(String id) {
		Book book = bookHomeLocal.findByID(id);
		// Preconditions.checkArgument(book == null, "Passed id is not present
		// in database.");
		log.info("By id={} has been found Book=={}", id, book);
		return book;
	}

	@Override
	public List<Book> findAll() {
		List<Book> books = bookHomeLocal.findAll();
		log.info("Has been found {} books", books.size());
		return books;
	}

	@Override
	public int findCountAllBooks() {
		Query query = entityManager.createNamedQuery(Book.FIND_COUNT_BOOKS);
		long count = (long) query.getSingleResult();
		log.info("Has been found {} books", count);
		return (int) count;
	}

	/**
	 * Method is converter org.richfaces.component.SortOrder attributes
	 * ascending and descending to String id SQL format.
	 * 
	 * @param SortOrder
	 *            order
	 * @return String
	 */
	private void appendSortOrder(StringBuilder sbForDataTable, DataTableSearchHolder dataTableSearchHolder) {
		if (dataTableSearchHolder.getSortOrder().equals(SortOrder.ascending)) {
			sbForDataTable.append(ASC);
		} else if (dataTableSearchHolder.getSortOrder().equals(SortOrder.descending)) {
			sbForDataTable.append(DESC);
		} else {
			throw new IllegalArgumentException();
		}

		if (!dataTableSearchHolder.getSortColumn().equals(BookFieldHolder.CREATED_DATE)) {
			sbForDataTable.append(COMMA);
		}
	}

	private void appendQueryPartFrom(StringBuilder sbForDataTable, DataTableSearchHolder dataTableSearchHolder) {

		sbForDataTable.append(String.format("%s %s %s", FROM, Review.class.getName(), R));
		sbForDataTable.append(String.format("%s %s.%s %s", RIGHT_JOIN, R, "book", B));
		if ((dataTableSearchHolder.getSortColumn() != null)
				&& (dataTableSearchHolder.getSortColumn().equals(BookFieldHolder.AUTHORS))
				|| (dataTableSearchHolder.getFilterValues().containsKey(BookFieldHolder.AUTHORS)) ) {
			sbForDataTable.append(String.format("%s %s.%s %s", LEFT_JOIN, B, BookFieldHolder.AUTHORS.getBusinessView(), A));
		}
	}

	/**
	 * Method appends for query sorting attribute ORDER BY with fields and
	 * Orders to sorting. Method does validation DataTableHelper arguments
	 * sortColumn and sortOrder.
	 */
	private void appendQueryPartOrderBy(StringBuilder sbForDataTable, DataTableSearchHolder dataTableSearchHolder) {

		sbForDataTable.append(ORDER_BY);

		if (dataTableSearchHolder.getSortColumn() == null) {
			sbForDataTable.append(String.format("%s %s, ", RAT, DESC));
		} else {
			switch ((BookFieldHolder) dataTableSearchHolder.getSortColumn()) {
			case RATING:
				sbForDataTable.append(RAT);
				appendSortOrder(sbForDataTable, dataTableSearchHolder);
				break;
			case AUTHORS:
				sbForDataTable.append(String.format("%s.%s ", A, AuthorFieldHolder.SECOND_NAME.getBusinessView()));
				appendSortOrder(sbForDataTable, dataTableSearchHolder);
				sbForDataTable.append(String.format("%s.%s ", A, AuthorFieldHolder.FIRST_NAME.getBusinessView()));
				appendSortOrder(sbForDataTable, dataTableSearchHolder);
				break;
			default:
				sbForDataTable.append(String.format("%s.%s ", B, dataTableSearchHolder.getSortColumn().getBusinessView()));
				appendSortOrder(sbForDataTable, dataTableSearchHolder);
			}
		}
		sbForDataTable.append(String.format("%s.%s %s ", B, BookFieldHolder.CREATED_DATE.getBusinessView(), DESC));

	}

	/**
	 * Method appends for query filtering attribute WHERE with fields and values
	 * to filtering. Method does validation DataTableHelper arguments
	 * Map<String, String> filterValues.
	 */
	private void appendQueryPartWhere(StringBuilder sbForDataTable, DataTableSearchHolder dataTableSearchHolder) {

		Map<EntityFieldHolder, String> map = dataTableSearchHolder.getFilterValues();
		if (dataTableSearchHolder.getFilterValues().size() > 1
				|| !dataTableSearchHolder.getFilterValues().containsKey(BookFieldHolder.RATING)) {
			sbForDataTable.append(WHERE);
		}
		int count = 0;
		boolean isRating = false;
		for (Map.Entry<EntityFieldHolder, String> pair : map.entrySet()) {
			if (count > 0 && isRating == false) {
				sbForDataTable.append(AND);
			}
			isRating = false;
			switch ((BookFieldHolder) pair.getKey()) {
			case AUTHORS:
				sbForDataTable.append(String.format("(%s.%s %s '%s%%' ", A, AuthorFieldHolder.SECOND_NAME.getBusinessView(), LIKE, pair.getValue()));
				sbForDataTable.append(OR);
				sbForDataTable.append(String.format(" %s.%s %s '%s%%') ", A, AuthorFieldHolder.FIRST_NAME.getBusinessView(), LIKE, pair.getValue()));
				break;
			case RATING:
				isRating = true;
				break;

			default:
				sbForDataTable.append(String.format("%s.%s %s '%s%%' ", B, pair.getKey().getBusinessView(), LIKE, pair.getValue()));
			}
			++count;
		}
	}

	/**
	 * Method appends for query filtering attribute HAVING with fields and values
	 * to filtering. Method does validation DataTableHelper arguments
	 * @param sbForDataTable
	 * @param dataTableSearchHolder
	 */
	public void appendQueryPartHaving(StringBuilder sbForDataTable, DataTableSearchHolder dataTableSearchHolder) {

		if (dataTableSearchHolder.getFilterValues().containsKey(BookFieldHolder.RATING)) {
			int value = Integer.valueOf(dataTableSearchHolder.getFilterValues().get(BookFieldHolder.RATING));
			sbForDataTable.append(String.format("%s %s(%s(%s.%s)) = %s ", HAVING, FLOOR, AVG, R, BookFieldHolder.RATING.getBusinessView(), value));
		}
	}

	@Override
	public List<Book> findBooksForDataTable(DataTableSearchHolder dataTableSearchHolder) {
		long start = System.currentTimeMillis();

		StringBuilder sbForDataTable = new StringBuilder();
		sbForDataTable.append(SELECT).append(B).append(COMMA);
		sbForDataTable.append(String.format("%s(%s.%s) %s %s", AVG, R, BookFieldHolder.RATING.getBusinessView(), AS, RAT));

		appendQueryPartFrom(sbForDataTable, dataTableSearchHolder);

		if (!dataTableSearchHolder.getFilterValues().isEmpty()) {
			appendQueryPartWhere(sbForDataTable, dataTableSearchHolder);
		}

		sbForDataTable.append(GROUP_BY).append(B);
		appendQueryPartHaving(sbForDataTable, dataTableSearchHolder);

		appendQueryPartOrderBy(sbForDataTable, dataTableSearchHolder);

		log.info("Query for getting Books for dataTable created == {}", sbForDataTable.toString());

		Query query = entityManager.createQuery(sbForDataTable.toString());

		query.setFirstResult(dataTableSearchHolder.getFirstRow());
		query.setMaxResults(dataTableSearchHolder.getRowsPerPage());

		List<Object[]> result = query.getResultList();
		List<Book> books = new ArrayList<Book>();
		for (Object[] o : result) {
			Book book = (Book) o[0];
			Double rating = (Double) o[1];
			if (rating == null) {
				rating = 0.0;
			}
			book.setRating(rating);
			books.add(book);
		}
		long end = System.currentTimeMillis();
		log.info("Method done, that took {} milliseconds. Has been found {} books.", (end - start), books.size());

		return books;
	}

	@Override
	public int findCountBooksForDataTable(DataTableSearchHolder dataTableSearchHolder) {
		long start = System.currentTimeMillis();

		boolean containsRating = false;

		StringBuilder sbForDataTable = new StringBuilder();
		sbForDataTable.append(String.format("%s %s(%s %s)", SELECT, COUNT, DISTINCT, B));
		appendQueryPartFrom(sbForDataTable, dataTableSearchHolder);
		if (!dataTableSearchHolder.getFilterValues().isEmpty()) {
			appendQueryPartWhere(sbForDataTable, dataTableSearchHolder);
		}
		containsRating = dataTableSearchHolder.getFilterValues().containsKey(BookFieldHolder.RATING);
		if (containsRating) {
			sbForDataTable.append(GROUP_BY).append(B);
			appendQueryPartHaving(sbForDataTable, dataTableSearchHolder);
		}

		log.info("Query for getting count Books for dataTable created {}", sbForDataTable.toString());
		Query query = entityManager.createQuery(sbForDataTable.toString());
		long count;
		if (containsRating) {
			List<Long> findedValues = query.getResultList();
			count = findedValues.size();
		} else {
			count = (long) query.getSingleResult();
		}
		long end = System.currentTimeMillis();
		log.info("Method done, that took {} milliseconds. Has been found {} books.", (end - start), count);
		return (int) count;
	}

	@Override
	public List<Book> findBooksByBookNameForAutocomplete(String value) {
		TypedQuery<Book> query = (TypedQuery<Book>) entityManager.createNamedQuery(Book.FIND_BOOK_BY_NAME);
		if (value == null || value.equals("")) {
			return new ArrayList<Book>();
		}
		query.setParameter("nam", value + '%');
		query.setMaxResults(10);
		List<Book> list =  query.getResultList();
		log.info("The metjod done. Has been found {} books.", list.size());
		return list;
	}
}
