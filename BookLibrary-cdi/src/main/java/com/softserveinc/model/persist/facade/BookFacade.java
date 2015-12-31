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
import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.BookWrapper;
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
	private static BookWrapper bookWrapper = BookWrapper.BOOK_BUSINESS_WRAPPER;
	private static BookWrapper bookWrapperUI = BookWrapper.BOOK_UI_WRAPPER;
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
		Book object = (Book) query.getSingleResult();
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
		Preconditions.checkArgument(book == null, "Passed id is not present in database.");
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

		if (!dataTableSearchHolder.getSortColumn().equals(bookWrapper.createdDate)) {
			sbForDataTable.append(", ");
		}
	}

	private void appendQueryPartFrom(StringBuilder sbForDataTable, DataTableSearchHolder dataTableSearchHolder) {

		sbForDataTable.append(FROM).append(Review.class.getName()).append(R + ' ').append(RIGHT_JOIN).append(R + '.')
				.append("book ").append(B + ' ');
		if ((dataTableSearchHolder.getSortColumn() != null)
				&& (dataTableSearchHolder.getSortColumn().equals(bookWrapperUI.authors))) {
			sbForDataTable.append(LEFT_JOIN).append(B + '.').append(bookWrapper.authors).append(A);
		}
		if (dataTableSearchHolder.getFilterValues().containsKey(bookWrapperUI.authors)) {
			sbForDataTable.append(LEFT_JOIN).append(B + '.').append(bookWrapper.authors).append(A);
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
			sbForDataTable.append(RAT).append(DESC).append(", ");
		} else {
			switch (dataTableSearchHolder.getSortColumn()) {
			case "Average rating":
				sbForDataTable.append(RAT);
				appendSortOrder(sbForDataTable, dataTableSearchHolder);
				break;
			case "Authors":
				sbForDataTable.append(A + '.').append(authorWrapper.secondName);
				appendSortOrder(sbForDataTable, dataTableSearchHolder);
				sbForDataTable.append(A + '.').append(authorWrapper.firstName + ' ');
				appendSortOrder(sbForDataTable, dataTableSearchHolder);
				break;
			default:
				sbForDataTable.append(B + '.')
						.append(BookWrapper.convertFromWrapperUIToWrapper(dataTableSearchHolder.getSortColumn()));
				appendSortOrder(sbForDataTable, dataTableSearchHolder);
			}
		}
		sbForDataTable.append(B + '.').append(bookWrapper.createdDate + ' ').append(DESC + ' ');

	}

	/**
	 * Method appends for query filtering attribute WHERE with fields and values
	 * to filtering. Method does validation DataTableHelper arguments
	 * Map<String, String> filterValues.
	 */
	private void appendQueryPartWhere(StringBuilder sbForDataTable, DataTableSearchHolder dataTableSearchHolder) {

		Map<String, String> map = dataTableSearchHolder.getFilterValues();
		if (dataTableSearchHolder.getFilterValues().size() > 1 || !dataTableSearchHolder.getFilterValues().containsKey(bookWrapperUI.rating)) {
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
			case "Authors":
				sbForDataTable.append(" (" + A + '.').append(authorWrapper.secondName + ' ').append(LIKE + "'")
						.append(pair.getValue()).append("%' ").append(OR).append(A + '.')
						.append(authorWrapper.firstName + ' ').append(LIKE + "'").append(pair.getValue())
						.append("%') ");
				break;
			case "Average rating":
				isRating = true;
				break;

			default:
				sbForDataTable.append(B + '.').append(BookWrapper.convertFromWrapperUIToWrapper(pair.getKey()))
						.append(LIKE + "'").append(pair.getValue()).append("%' ");
			}
			++count;
		}
	}

	public void appendQueryPartHaving(StringBuilder sbForDataTable, DataTableSearchHolder dataTableSearchHolder) {

		if (dataTableSearchHolder.getFilterValues().containsKey(bookWrapperUI.rating)) {
			int value = Integer.valueOf(dataTableSearchHolder.getFilterValues().get(bookWrapperUI.rating));
			sbForDataTable.append(HAVING).append(FLOOR).append("(AVG(r.rating)) = ").append(value)
					.append(" ");
		}
	}

	@Override
	public List<Book> findBooksForDataTable(DataTableSearchHolder dataTableSearchHolder) {
		long start = System.currentTimeMillis();

		StringBuilder sbForDataTable = new StringBuilder();
		sbForDataTable.append(SELECT).append(B + ", ").append(AVG).append("(" + R + '.')
				.append(bookWrapper.rating + ") ").append(AS).append(RAT);

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
		StringBuilder sbForDataTable = new StringBuilder();
		sbForDataTable.append(SELECT).append(COUNT + '(').append(DISTINCT).append(B + '.').append("isbn) ");
		appendQueryPartFrom(sbForDataTable, dataTableSearchHolder);
		if (!dataTableSearchHolder.getFilterValues().isEmpty()) {
			appendQueryPartWhere(sbForDataTable, dataTableSearchHolder);
		}
		log.info("Query for getting count Books for dataTable created {}", sbForDataTable.toString());

		Query query = entityManager.createQuery(sbForDataTable.toString());
		long count = (long) query.getSingleResult();
		long end = System.currentTimeMillis();
		log.info("Method done, that took {} milliseconds. Has been found {} books.", (end - start), count);
		return (int) count;
	}
}
