package com.softserveinc.booklibrary.session.persist.facade.impl;

import java.util.ArrayList;
import java.util.Date;
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

import com.softserveinc.booklibrary.action.helper.DataTableSearchHolder;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.model.entity.Review;
import com.softserveinc.booklibrary.session.persist.facade.BookFacadeLocal;
import com.softserveinc.booklibrary.session.persist.facade.BookFacadeRemote;
import com.softserveinc.booklibrary.session.persist.facade.IBookFacade;
import com.softserveinc.booklibrary.session.persist.home.BookHomeLocal;
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
	public Book findMostPopularBookForAuthor(Author author) {
		TypedQuery<Book> query = entityManager.createNamedQuery(Book.FIND_MOST_POPULAR_BOOK_BY_AUTHOR, Book.class);
		query.setParameter("author", author);
		Book book = null;
		try {
			book = query.getSingleResult();
			log.info("The method done. For author {} most popular book is {}.", author, book);
		} catch (NoResultException e) {
			log.error("The method done. Author doesn't have any books.");

		}
		return book;
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

	@Override
	public List<Book> findMostPopularBooks(Integer limit) {
		TypedQuery<Book> query = entityManager.createNamedQuery(Book.FIND_TOP_RATED_BOOKS, Book.class);
		query.setParameter("date", new Date((System.currentTimeMillis() - 10 * 24 * 60 * 60 * 1000)));
		if (limit != null) {
			query.setMaxResults(limit);
		}
		List<Book> books = query.getResultList();
		log.info("The method done. Has been found {} books.", books.size());
		return books;
	}

	@Override
	public List<Book> findMostPopularLatelyAddedBooks(Integer limit) {
		TypedQuery<Book> query = entityManager.createNamedQuery(Book.FIND_TOP_RATED_BOOKS_LATELY_ADDED, Book.class);

		query.setParameter("date", new Date((System.currentTimeMillis() - 10 * 24 * 60 * 60 * 1000)));

		if (limit != null) {
			query.setMaxResults(limit);
		}
		List<Book> books = query.getResultList();
		log.info("The method done. Has been found {} books.", books.size());
		return books;
	}

	@Override
	public List<Book> findLatestAddedBooks(int limit) {
		TypedQuery<Book> query = entityManager.createNamedQuery(Book.FIND_ALL_BOOKS_SORTED_BY_DATE, Book.class);
		query.setMaxResults(limit);
		List<Book> books = query.getResultList();
		log.info("The method done. Has been found {} books.", books.size());
		return books;
	}

	/**
	 * This class is a implementation of {@link QueryBuilder} class and class is
	 * a query builder for {@code ManageBooks} DataTables (table for managing
	 * {@link Book} instances).
	 */
	class BookQueryBuilderForDataTable extends QueryBuilder {

		@Override
		protected void appendQueryPartSelectCountObjects() {
			sbForDataTable.append(SqlConstantsHolder.SELECT)
					.append(String.format(SqlConstantsHolder.AGREGATE_FUNC_TEMPLATE, SqlConstantsHolder.COUNT,
							SqlConstantsHolder.B, BookFieldHolder.ID.getBusinessView()))
					.append(SqlConstantsHolder.FROM).append(Book.class.getName()).append(SqlConstantsHolder.B)
					.append(SqlConstantsHolder.WHERE)
					.append(String.format(SqlConstantsHolder.FIELD_TEMPLATE, SqlConstantsHolder.B,
							BookFieldHolder.ID.getBusinessView()))
					.append(SqlConstantsHolder.IN).append('(').append(SqlConstantsHolder.SELECT)
					.append(String.format(SqlConstantsHolder.FIELD_TEMPLATE, SqlConstantsHolder.B,
							BookFieldHolder.ID.getBusinessView()));
		}

		@Override
		protected void appendQueryPartSelectObjects() {
			sbForDataTable.append(SqlConstantsHolder.SELECT).append(SqlConstantsHolder.B)
					.append(SqlConstantsHolder.COMMA)
					.append(String.format(SqlConstantsHolder.ROUND_AVG_TEMPLATE_TO_TWO_DIGITS, SqlConstantsHolder.R,
							BookFieldHolder.RATING.getBusinessView()))
					.append(SqlConstantsHolder.AS).append(SqlConstantsHolder.RAT);
		}

		@Override
		protected void appendQueryPartFrom() {
			sbForDataTable.append(SqlConstantsHolder.FROM).append(Review.class.getName()).append(SqlConstantsHolder.R)
					.append(SqlConstantsHolder.RIGHT_JOIN).append((String.format(SqlConstantsHolder.FIELD_TEMPLATE,
							SqlConstantsHolder.R, ReviewFieldHolder.BOOK.getBusinessView())))
					.append(SqlConstantsHolder.B);
			if ((dataTableSearchHolder.getSortColumn() != null)
					&& (dataTableSearchHolder.getSortColumn().equals(BookFieldHolder.AUTHORS))
					|| (dataTableSearchHolder.getFilterValues().containsKey(BookFieldHolder.AUTHORS))) {
				sbForDataTable
						.append(SqlConstantsHolder.LEFT_JOIN).append(String.format(SqlConstantsHolder.FIELD_TEMPLATE,
								SqlConstantsHolder.B, BookFieldHolder.AUTHORS.getBusinessView()))
						.append(SqlConstantsHolder.A);
			}
		}

		@Override
		protected void appendQueryPartWhere() {
			Map<EntityFieldHolder, String> map = dataTableSearchHolder.getFilterValues();
			boolean isRating = false;
			String ratingValue = null;
			if (dataTableSearchHolder.getFilterValues().containsKey(BookFieldHolder.RATING)) {
				isRating = true;
				ratingValue = dataTableSearchHolder.getFilterValues().get(BookFieldHolder.RATING);
				dataTableSearchHolder.getFilterValues().remove(BookFieldHolder.RATING);
			}
			if (!dataTableSearchHolder.getFilterValues().isEmpty()) {
				sbForDataTable.append(SqlConstantsHolder.WHERE);
			}
			int count = 1;
			for (Map.Entry<EntityFieldHolder, String> pair : map.entrySet()) {
				if (count > 1) {
					sbForDataTable.append(SqlConstantsHolder.AND);
				}
				++count;
				switch ((BookFieldHolder) pair.getKey()) {
				case AUTHORS:
					sbForDataTable.append('(')
							.append(String.format(SqlConstantsHolder.FIELD_TEMPLATE, SqlConstantsHolder.A,
									AuthorFieldHolder.SECOND_NAME.getBusinessView()))
							.append(String.format(SqlConstantsHolder.LIKE_TEMPLATE, pair.getValue()))
							.append(SqlConstantsHolder.OR)
							.append(String.format(SqlConstantsHolder.FIELD_TEMPLATE, SqlConstantsHolder.A,
									AuthorFieldHolder.FIRST_NAME.getBusinessView()))
							.append(String.format(SqlConstantsHolder.LIKE_TEMPLATE, pair.getValue())).append(')');
					break;
				default:
					sbForDataTable
							.append(String.format(SqlConstantsHolder.FIELD_TEMPLATE, SqlConstantsHolder.B,
									pair.getKey().getBusinessView()))
							.append(String.format(SqlConstantsHolder.LIKE_TEMPLATE, pair.getValue()));
				}
			}
			if (isRating) {
				dataTableSearchHolder.getFilterValues().put(BookFieldHolder.RATING, ratingValue);
			}
		}

		@Override
		protected void appendQueryPartGroupBy() {
			sbForDataTable.append(SqlConstantsHolder.GROUP_BY).append(SqlConstantsHolder.B);
		}

		@Override
		protected void appendQueryPartHaving() {
			if (dataTableSearchHolder.getFilterValues().containsKey(BookFieldHolder.RATING)) {
				int value = Integer.valueOf(dataTableSearchHolder.getFilterValues().get(BookFieldHolder.RATING));
				sbForDataTable.append(SqlConstantsHolder.HAVING)
						.append(String.format(SqlConstantsHolder.FLOOR_TEMPLATE, SqlConstantsHolder.R,
								BookFieldHolder.RATING.getBusinessView()))
						.append(SqlConstantsHolder.EQUAL).append(value);
			}
		}

		@Override
		protected void appendQueryPartOrderBy() {
			sbForDataTable.append(SqlConstantsHolder.ORDER_BY);

			if (dataTableSearchHolder.getSortColumn() == null) {
				sbForDataTable.append(SqlConstantsHolder.RAT).append(SqlConstantsHolder.DESC)
						.append(SqlConstantsHolder.COMMA);
			} else {
				switch ((BookFieldHolder) dataTableSearchHolder.getSortColumn()) {
				case RATING:
					sbForDataTable.append(SqlConstantsHolder.RAT).append(dataTableSearchHolder.getSortOrder())
							.append(SqlConstantsHolder.COMMA);
					break;
				case AUTHORS:
					sbForDataTable
							.append(String.format(SqlConstantsHolder.FIELD_TEMPLATE, SqlConstantsHolder.A,
									AuthorFieldHolder.SECOND_NAME.getBusinessView()))
							.append(dataTableSearchHolder.getSortOrder()).append(SqlConstantsHolder.COMMA);
					sbForDataTable
							.append(String.format(SqlConstantsHolder.FIELD_TEMPLATE, SqlConstantsHolder.A,
									AuthorFieldHolder.FIRST_NAME.getBusinessView()))
							.append(dataTableSearchHolder.getSortOrder()).append(SqlConstantsHolder.COMMA);
					break;
				default:
					sbForDataTable
							.append(String.format(SqlConstantsHolder.FIELD_TEMPLATE, SqlConstantsHolder.B,
									dataTableSearchHolder.getSortColumn().getBusinessView()))
							.append(dataTableSearchHolder.getSortOrder()).append(SqlConstantsHolder.COMMA);
				}
			}
			sbForDataTable.append(String.format(SqlConstantsHolder.FIELD_TEMPLATE, SqlConstantsHolder.B,
					BookFieldHolder.CREATED_DATE.getBusinessView())).append(SqlConstantsHolder.DESC);
		}
	}

}
