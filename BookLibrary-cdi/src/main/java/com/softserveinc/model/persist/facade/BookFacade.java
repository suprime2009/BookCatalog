package com.softserveinc.model.persist.facade;

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

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.AuthorFieldHolder;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.BookFieldHolder;
import com.softserveinc.model.persist.entity.EntityFieldHolder;
import com.softserveinc.model.persist.entity.Review;
import com.softserveinc.model.persist.home.BookHomeLocal;
import com.softserveinc.model.session.util.DataTableSearchHolder;
import com.softserveinc.model.session.util.SQLCommandConstants;
import com.softserveinc.model.session.util.QueryBuilderForDataTable;

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

	@Override
	public List<Book> findBooksByBookNameForAutocomplete(String value) {
		TypedQuery<Book> query = (TypedQuery<Book>) entityManager.createNamedQuery(Book.FIND_BOOK_BY_NAME);
		if (value == null || value.equals("")) {
			return new ArrayList<Book>();
		}
		query.setParameter("nam", value + '%');
		query.setMaxResults(10);
		List<Book> list = query.getResultList();
		log.info("The metjod done. Has been found {} books.", list.size());
		return list;
	}

	@Override
	public List<Book> findBooksByListId(List<String> idForBooks) {
		TypedQuery<Book> query = (TypedQuery<Book>) entityManager.createNamedQuery(Book.FIND_BOOKS_BY_LIST_ID);
		query.setParameter("list", idForBooks);
		List<Book> books = query.getResultList();
		return books;
	}

	@Override
	public List<Book> findBooksByRating(Integer rating) {
		TypedQuery<Book> query = (TypedQuery<Book>) entityManager.createNamedQuery(Book.FIND_BOOKS_BY_RATING);
		query.setParameter("rat", rating);
		List<Book> books = query.getResultList();
		return books;
	}

	@Override
	public int findCountBooksForDataTable(DataTableSearchHolder dataTableSearchHolder) {
		long start = System.currentTimeMillis();

		String createdQuery = new BookQueryBuilderForDataTable().getCountObjectsQuery(dataTableSearchHolder);

		Query query = entityManager.createQuery(createdQuery);
		long count;
		// if
		// (dataTableSearchHolder.getFilterValues().containsKey(BookFieldHolder.RATING))
		// {
		List<Long> findedValues = query.getResultList();
		count = findedValues.size();
		// } else {
		// count = (long) query.getSingleResult();
		// }
		log.info("Method done, that took {} milliseconds. Has been found {} books.",
				(System.currentTimeMillis() - start), count);
		return (int) count;
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
		log.info("Method done, that took {} milliseconds. Has been found {} books.",
				(System.currentTimeMillis() - start), books.size());

		return books;
	}

	/**
	 * This class is a implementation of {@link QueryBuilderForDataTable} class
	 * and class is a query builder for {@code ManageBooks} DataTables (table
	 * for managing {@link Book} instances).
	 */
	class BookQueryBuilderForDataTable extends QueryBuilderForDataTable {

		@Override
		protected void appendQueryPartSelectCountObjects() {
			sbForDataTable.append(SELECT).append(String.format(AGREGATE_FUNC_DISTINCT_TEMPLATE, COUNT, B));
		}

		@Override
		protected void appendQueryPartSelectObjects() {
			sbForDataTable.append(SELECT).append(B).append(COMMA)
					.append(String.format(AGREGATE_FUNC_TEMPLATE, AVG, R, BookFieldHolder.RATING.getBusinessView()))
					.append(AS).append(RAT);
		}

		@Override
		protected void appendQueryPartFrom() {
			sbForDataTable.append(FROM).append(Review.class.getName()).append(R).append(RIGHT_JOIN)
					.append((String.format(FIELD_TEMPLATE, R, "book"))).append(B);
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
					sbForDataTable.append(AND);
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
				sbForDataTable.append(HAVING).append(FLOOR + '(')
						.append(String.format(AGREGATE_FUNC_TEMPLATE, AVG, R, BookFieldHolder.RATING.getBusinessView()))
						.append(") = ").append(value);
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
