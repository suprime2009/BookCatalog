package com.softserveinc.booklibrary.session.persist.facade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.booklibrary.action.util.DataTableSearchHolder;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.model.entity.Review;
import com.softserveinc.booklibrary.session.persist.facade.BookFacadeLocal;
import com.softserveinc.booklibrary.session.persist.facade.BookFacadeRemote;
import com.softserveinc.booklibrary.session.persist.facade.IBookFacade;
import com.softserveinc.booklibrary.session.persist.home.BookHomeLocal;
import com.softserveinc.booklibrary.session.util.QueryBuilderForDataTable;
import com.softserveinc.booklibrary.session.util.SQLCommandConstants;
import com.softserveinc.booklibrary.session.util.holders.AuthorFieldHolder;
import com.softserveinc.booklibrary.session.util.holders.BookFieldHolder;
import com.softserveinc.booklibrary.session.util.holders.EntityFieldHolder;
import com.softserveinc.booklibrary.session.util.holders.ReviewFieldHolder;



/**
 * The {@code BookFacade} class is an implementation facade (read) operations
 * for {@link Book} entity. This class is @Stateless.
 *
 */
@Stateless
public class BookFacade implements BookFacadeLocal, BookFacadeRemote {

	private static Logger log = LoggerFactory.getLogger(BookFacade.class);

	@PersistenceContext
	private EntityManager entityManager;

	@EJB
	private BookHomeLocal bookHomeLocal;

	public BookFacade() {
	}

	@Override
	public List<Book> findBookByName(String name) {
		TypedQuery<Book> query = entityManager.createNamedQuery(Book.FIND_BOOK_BY_NAME, Book.class);
		query.setParameter("nam", name);
		List<Book> list = (List<Book>) query.getResultList();
		log.info("By book name= {} has been found books. Count books= {}", name, list.size());
		return list;
	}

	@Override
	public Book findBookByISNBN(String isbn) {
		TypedQuery<Book> query = entityManager.createNamedQuery(Book.FIND_BOOK_BY_ISNBN, Book.class);
		query.setParameter("isb", isbn);
		Book object = null;
		try {
			object = (Book) query.getSingleResult();
		} catch (NoResultException e) {
			log.error("By {} no one book finded.", isbn);
			return null;
		}
		log.info("The method done. By {} has been found Book= {}", isbn, object);
		return object;
	}

	@Override
	public List<Book> findBooksByAuthor(Author author) {
		TypedQuery<Book> query = entityManager.createNamedQuery(Book.FIND_BOOKS_BY_AUTHOR, Book.class);
		query.setParameter("auth", author);
		List<Book> list = query.getResultList();
		log.info("By author={} has been found {} books", author, list.size());
		return list;
	}

	@Override
	public Book findById(String id) {
		Book book = bookHomeLocal.findByID(id);
		if (book == null) {
			log.error("By id={} nothing finded.", id);
		} else {
			log.info("By id={} has been found Book=={}", id, book);
		}
		return book;
	}

	@Override
	public List<Book> findAll() {
		List<Book> books = bookHomeLocal.findAll();
		log.info("The method done. Has been found {} books", books.size());
		return books;
	}

	@Override
	public int findCountAllBooks() {
		Query query = entityManager.createNamedQuery(Book.FIND_COUNT_BOOKS);
		long count;
		try {
			count = (long) query.getSingleResult();
		} catch (NoResultException e) {
			log.info("Book table in database is empty.");
			return 0;
		}
		log.info("The method done. Has been found {} books", count);
		return (int) count;
	}

	/**
	 * This method is a implementation of {@link IBookFacade} method
	 * {@linkplain findBooksByBookNameForAutocomplete}. This implementation is
	 * specific, books returns are limited. Current limits set at 10.
	 *
	 */
	@Override
	public List<Book> findBooksByBookNameForAutocomplete(String value) {
		TypedQuery<Book> query = entityManager.createNamedQuery(Book.FIND_BOOK_BY_NAME, Book.class);
		if (value == null || value.equals("")) {
			return new ArrayList<Book>();
		}
		query.setParameter("nam", value + '%');
		query.setMaxResults(10);
		List<Book> books = query.getResultList();
		log.info("The method done. Has been found {} books.", books.size());
		return books;
	}

	@Override
	public List<Book> findBooksByListId(List<String> idForBooks) {
		TypedQuery<Book> query = entityManager.createNamedQuery(Book.FIND_BOOKS_BY_LIST_ID, Book.class);
		query.setParameter("list", idForBooks);
		List<Book> books = query.getResultList();
		log.info("The method finished. Has been found {} books.", books.size());
		return books;
	}

	@Override
	public List<Book> findBooksByRating(Integer rating) {
		if (rating != null && rating > 5 || rating < 1) {
			String errorMessage = "Rating must be in range between 1 and 5.";
			log.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);
		}
		TypedQuery<Book> query = entityManager.createNamedQuery(Book.FIND_BOOKS_BY_RATING, Book.class);
		query.setParameter("rat", rating);
		List<Book> books = query.getResultList();
		log.info("The method finished. By rating = {} has been found {} books.", rating, books.size());
		return books;
	}

	@Override
	public Integer findCountBooksByRating(Integer rating) {
		if (rating == null) {
			System.out.println("NULL");
			return null;

		}
		if (rating > 5 || rating < 1) {
			System.out.println("rating > 5 || rating < 1");
			String errorMessage = "Rating must be in range between 1 and 5.";
			log.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);
		}
		System.out.println("before execu");
		System.out.println(rating);
		Query query = entityManager.createQuery(
				"SELECT COUNT(b) FROM Book b WHERE b.idBook IN (SELECT b.idBook FROM Review r RIGHT JOIN r.book b GROUP BY b HAVING FLOOR(AVG( r.rating)) = :rat)");
		query.setParameter("rat", rating);
		long result;
		try {
			result = (long) query.getSingleResult();
			log.info("The method done. Has been found {} books.", result);
		} catch (NoResultException e) {
			log.info("The method done. For rating {} no books found", rating);
			return 0;
		}
		return (int) result;
	}

	@Override
	public int findCountBooksForDataTable(DataTableSearchHolder dataTableSearchHolder) {
		long start = System.currentTimeMillis();

		Query query = entityManager
				.createQuery(new BookQueryBuilderForDataTable().getCountObjectsQuery(dataTableSearchHolder));
		long result;
		try {
			result = (long) query.getSingleResult();
			log.info("The method done, that took {} milliseconds. Has been found {} books.",
					(System.currentTimeMillis() - start), result);

		} catch (NoResultException e) {
			log.info("The method done. For current dataTable requirements = {}, no books found", dataTableSearchHolder);
			return 0;
		}
		return (int) result;
	}

	@Override

	public List<Book> findBooksForDataTable(DataTableSearchHolder dataTableSearchHolder) {
		long start = System.currentTimeMillis();
		log.debug("The method starts. Passed value for method = {}", dataTableSearchHolder);

		String createdQuery = new BookQueryBuilderForDataTable().getObjectsQuery(dataTableSearchHolder);

		Query query = entityManager.createQuery(createdQuery);
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
		log.info("The method done, that took {} milliseconds. Has been found {} books.",
				(System.currentTimeMillis() - start), books.size());

		return books;
	}

	/**
	 * This class is a implementation of {@link QueryBuilderForDataTable} class
	 * and class is a query builder for {@code ManageBooks} DataTables (table
	 * for managing {@link Book} instances).
	 */
	class BookQueryBuilderForDataTable extends QueryBuilderForDataTable implements SQLCommandConstants {

		@Override
		protected void appendQueryPartSelectCountObjects() {
			sbForDataTable.append(SELECT)
					.append(String.format(AGREGATE_FUNC_TEMPLATE, COUNT, B, BookFieldHolder.ID.getBusinessView()))
					.append(FROM).append(Book.class.getName()).append(B).append(WHERE)
					.append(String.format(FIELD_TEMPLATE, B, BookFieldHolder.ID.getBusinessView())).append(IN)
					.append('(').append(SELECT)
					.append(String.format(FIELD_TEMPLATE, B, BookFieldHolder.ID.getBusinessView()));
		}

		@Override
		protected void appendQueryPartSelectObjects() {
			sbForDataTable.append(SELECT).append(B).append(COMMA).append(
					String.format(ROUND_AVG_TEMPLATE_TO_TWO_DIGITS, R, BookFieldHolder.RATING.getBusinessView()))
					.append(AS).append(RAT);
		}

		@Override
		protected void appendQueryPartFrom() {
			sbForDataTable.append(FROM).append(Review.class.getName()).append(R).append(RIGHT_JOIN)
					.append((String.format(FIELD_TEMPLATE, R, ReviewFieldHolder.BOOK.getBusinessView()))).append(B);
			if ((dataTableSearchHolder.getSortColumn() != null)
					&& (dataTableSearchHolder.getSortColumn().equals(BookFieldHolder.AUTHORS))
					|| (dataTableSearchHolder.getFilterValues().containsKey(BookFieldHolder.AUTHORS))) {
				sbForDataTable.append(LEFT_JOIN)
						.append(String.format(FIELD_TEMPLATE, B, BookFieldHolder.AUTHORS.getBusinessView())).append(A);
			}
		}

		@Override
		protected void appendQueryPartWhere() {
			Map<EntityFieldHolder, String> map = dataTableSearchHolder.getFilterValues();
			if (!dataTableSearchHolder.getFilterValues().containsKey(BookFieldHolder.RATING)
					|| dataTableSearchHolder.getFilterValues().size() > 1) {
				sbForDataTable.append(WHERE);
			}
			int count = 1;
			boolean isRating = false;
			for (Map.Entry<EntityFieldHolder, String> pair : map.entrySet()) {
				if (count > 1 && isRating == false) {
					if (!pair.getKey().equals(BookFieldHolder.RATING)) {
						sbForDataTable.append(AND);
					}
				}
				++count;
				isRating = false;
				switch ((BookFieldHolder) pair.getKey()) {
				case AUTHORS:
					sbForDataTable.append('(')
							.append(String.format(FIELD_TEMPLATE, A, AuthorFieldHolder.SECOND_NAME.getBusinessView()))
							.append(String.format(LIKE_TEMPLATE, pair.getValue())).append(OR)
							.append(String.format(FIELD_TEMPLATE, A, AuthorFieldHolder.FIRST_NAME.getBusinessView()))
							.append(String.format(LIKE_TEMPLATE, pair.getValue())).append(')');
					break;
				case RATING:
					isRating = true;
					break;

				default:
					sbForDataTable.append(String.format(FIELD_TEMPLATE, B, pair.getKey().getBusinessView()))
							.append(String.format(LIKE_TEMPLATE, pair.getValue()));
				}
			}
		}

		@Override
		protected void appendQueryPartGroupBy() {
			sbForDataTable.append(GROUP_BY).append(B);
		}

		@Override
		protected void appendQueryPartHaving() {
			if (dataTableSearchHolder.getFilterValues().containsKey(BookFieldHolder.RATING)) {
				int value = Integer.valueOf(dataTableSearchHolder.getFilterValues().get(BookFieldHolder.RATING));
				sbForDataTable.append(HAVING)
						.append(String.format(FLOOR_TEMPLATE, R, BookFieldHolder.RATING.getBusinessView()))
						.append(EQUAL).append(value);
			}
		}

		@Override
		protected void appendQueryPartOrderBy() {
			sbForDataTable.append(ORDER_BY);

			if (dataTableSearchHolder.getSortColumn() == null) {
				sbForDataTable.append(RAT).append(DESC).append(COMMA);
			} else {
				switch ((BookFieldHolder) dataTableSearchHolder.getSortColumn()) {
				case RATING:
					sbForDataTable.append(RAT).append(dataTableSearchHolder.getSortOrder()).append(COMMA);
					break;
				case AUTHORS:
					sbForDataTable
							.append(String.format(FIELD_TEMPLATE, A, AuthorFieldHolder.SECOND_NAME.getBusinessView()))
							.append(dataTableSearchHolder.getSortOrder()).append(COMMA);
					sbForDataTable
							.append(String.format(FIELD_TEMPLATE, A, AuthorFieldHolder.FIRST_NAME.getBusinessView()))
							.append(dataTableSearchHolder.getSortOrder()).append(COMMA);
					break;
				default:
					sbForDataTable
							.append(String.format(FIELD_TEMPLATE, B,
									dataTableSearchHolder.getSortColumn().getBusinessView()))
							.append(dataTableSearchHolder.getSortOrder()).append(COMMA);
				}
			}
			sbForDataTable.append(String.format(FIELD_TEMPLATE, B, BookFieldHolder.CREATED_DATE.getBusinessView()))
					.append(DESC);
		}
	}

}
