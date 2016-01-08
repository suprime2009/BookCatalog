package com.softserveinc.model.persist.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.ColumnResult;
import javax.persistence.EntityManager;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;

import org.apache.commons.collections.MultiMap;
import org.richfaces.component.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.softserveinc.action.managebook.BookUIWrapper;
import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.BookColumnsEnum;
import com.softserveinc.model.persist.entity.AuthorWrapper;
import com.softserveinc.model.persist.entity.Review;
import com.softserveinc.model.persist.home.BookHome;
import com.softserveinc.model.persist.home.BookHomeLocal;
import com.softserveinc.model.session.util.ConstantsUtil;
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
	private static BookColumnsEnum bookEnum = BookColumnsEnum.BOOK_BUSINESS_VIEW;
	private static AuthorWrapper authorWrapper = AuthorWrapper.AUTHOR_BUSINESS_WRAPPER;

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

		if (!dataTableSearchHolder.getSortColumn().equals(bookEnum.createdDate)) {
			sbForDataTable.append(COMMA);
		}
	}

	private void appendQueryPartFrom(StringBuilder sbForDataTable, DataTableSearchHolder dataTableSearchHolder) {

		sbForDataTable.append(String.format("%s %s %s", FROM, Review.class.getName(), R));
		sbForDataTable.append(String.format("%s %s.%s %s", RIGHT_JOIN, R, "book", B));
		if ((dataTableSearchHolder.getSortColumn() != null)
				&& (dataTableSearchHolder.getSortColumn().equals(bookEnum.authors))
				|| (dataTableSearchHolder.getFilterValues().containsKey(bookEnum.authors)) ) {
			sbForDataTable.append(String.format("%s %s.%s %s", LEFT_JOIN, B, bookEnum.authors, A));
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
			switch (dataTableSearchHolder.getSortColumn()) {
			case "rating":
				sbForDataTable.append(RAT);
				appendSortOrder(sbForDataTable, dataTableSearchHolder);
				break;
			case "authors":
				sbForDataTable.append(String.format("%s.%s ", A, authorWrapper.secondName));
				appendSortOrder(sbForDataTable, dataTableSearchHolder);
				sbForDataTable.append(String.format("%s.%s ", A, authorWrapper.firstName));
				appendSortOrder(sbForDataTable, dataTableSearchHolder);
				break;
			default:
				sbForDataTable.append(String.format("%s.%s ", B, dataTableSearchHolder.getSortColumn()));
				appendSortOrder(sbForDataTable, dataTableSearchHolder);
			}
		}
		sbForDataTable.append(String.format("%s.%s %s ", B, bookEnum.createdDate, DESC));

	}

	/**
	 * Method appends for query filtering attribute WHERE with fields and values
	 * to filtering. Method does validation DataTableHelper arguments
	 * Map<String, String> filterValues.
	 */
	private void appendQueryPartWhere(StringBuilder sbForDataTable, DataTableSearchHolder dataTableSearchHolder) {

		Map<String, String> map = dataTableSearchHolder.getFilterValues();
		if (dataTableSearchHolder.getFilterValues().size() > 1
				|| !dataTableSearchHolder.getFilterValues().containsKey(bookEnum.rating)) {
			sbForDataTable.append(WHERE);
		}
		int count = 0;
		boolean isRating = false;
		for (Map.Entry<String, String> pair : map.entrySet()) {
			if (count > 0 && isRating == false) {
				sbForDataTable.append(AND);
			}
			isRating = false;
			switch (pair.getKey()) {
			case "authors":
				sbForDataTable.append(String.format("(%s.%s %s '%s%%' ", A, authorWrapper.secondName, LIKE, pair.getValue()));
				sbForDataTable.append(OR);
				sbForDataTable.append(String.format(" %s.%s %s '%s%%') ", A, authorWrapper.getFirstName(), LIKE, pair.getValue()));
				break;
			case "rating":
				isRating = true;
				break;

			default:
				sbForDataTable.append(String.format("%s.%s %s '%s%%' ", B, pair.getKey(), LIKE, pair.getValue()));
			}
			++count;
		}
	}

	public void appendQueryPartHaving(StringBuilder sbForDataTable, DataTableSearchHolder dataTableSearchHolder) {

		if (dataTableSearchHolder.getFilterValues().containsKey(bookEnum.rating)) {
			int value = Integer.valueOf(dataTableSearchHolder.getFilterValues().get(bookEnum.rating));
			sbForDataTable.append(String.format("%s %s(%s(%s.%s)) = %s ", HAVING, FLOOR, AVG, R, bookEnum.rating, value));
		}
	}

	@Override
	public List<Book> findBooksForDataTable(DataTableSearchHolder dataTableSearchHolder) {
		long start = System.currentTimeMillis();

		StringBuilder sbForDataTable = new StringBuilder();
		sbForDataTable.append(SELECT).append(B).append(COMMA);
		sbForDataTable.append(String.format("%s(%s.%s) %s %s", AVG, R, bookEnum.rating, AS, RAT));

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
		containsRating = dataTableSearchHolder.getFilterValues().containsKey(bookEnum.rating);
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
}
