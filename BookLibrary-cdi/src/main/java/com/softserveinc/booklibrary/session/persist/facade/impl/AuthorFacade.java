package com.softserveinc.booklibrary.session.persist.facade.impl;

import java.util.List;

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
import com.softserveinc.booklibrary.session.persist.facade.AuthorFacadeLocal;
import com.softserveinc.booklibrary.session.persist.facade.AuthorFacadeRemote;
import com.softserveinc.booklibrary.session.persist.home.AuthorHomeLocal;
import com.softserveinc.booklibrary.session.util.holders.AuthorFieldHolder;
import com.softserveinc.booklibrary.session.util.holders.BookFieldHolder;
import com.softserveinc.booklibrary.session.util.holders.ReviewFieldHolder;

/**
 * The {@code AuthorFacade} class is an implementation facade (read) operations
 * for {@link Author} entity. This class is @Stateless.
 *
 */
@Stateless
public class AuthorFacade implements AuthorFacadeLocal, AuthorFacadeRemote {

	private static Logger log = LoggerFactory.getLogger(AuthorFacade.class);

	@PersistenceContext
	private EntityManager entityManager;

	@EJB
	private AuthorHomeLocal authorHomeLocal;

	public AuthorFacade() {

	}

	@Override
	public List<Author> findAllAuthorsByBook(Book book) {
		long methodStart = System.currentTimeMillis();
		TypedQuery<Author> query = entityManager.createNamedQuery(Author.FIND_ALL_AUTHORS_BY_BOOK, Author.class);
		query.setParameter("bk", book);
		List<Author> list = query.getResultList();
		log.info("The method done, that took {} milliseconds. Has been found {} authors.",
				(System.currentTimeMillis() - methodStart), list.size());
		return list;
	}

	@Override
	public Author findAuthorByFullName(String firstName, String secondName) {
		Author object = null;
		TypedQuery<Author> query = entityManager.createNamedQuery(Author.FIND_AUTHOR_BY_FULL_NAME, Author.class);
		query.setParameter("fn", firstName);
		query.setParameter("sn", secondName);
		try {
			object = (Author) query.getSingleResult();
		} catch (NoResultException e) {
			log.error("By firstName= {}, secondName= {} no one author has been found.", firstName, secondName);
			return null;
		}
		log.info("Method findAuthorByFullName finished. By firstName={} and lastName={} " + "has been found author={}",
				firstName, secondName, object);
		return object;
	}

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
		List<String> list = query.getResultList();
		log.info("The method done. By prefix= {} has been found {} authors", prefix, list.size());
		return list;
	}

	@Override
	public Double findAuthorAvegareRating(Author author) {
		Query query = entityManager.createNamedQuery(Author.FIND_AUTHOR_RATING);
		query.setParameter("author", author);
		Double result;
		try {
			result = (Double) query.getSingleResult();
			log.info("The method done. For author {} average rating equals {}", author, result);
		} catch (NoResultException e) {
			log.info("The method done. For author {} average rating = not graded", author);
			return null;
		}
		return result;
	}

	@Override
	public List<Author> findAuthorsByListId(List<String> list) {
		TypedQuery<Author> query = entityManager.createNamedQuery(Author.FIND_AUTHORS_BY_LIST_ID, Author.class);
		query.setParameter("list", list);
		List<Author> authors = query.getResultList();
		log.info("The method finished. Has been found {} authors.", authors.size());
		return authors;
	}

	@Override
	public List<Object[]> findAuthorsForDataTable(DataTableSearchHolder dataTableSearchHolder) {
		long start = System.currentTimeMillis();
		log.info("The method starts. Passed value to method={}", dataTableSearchHolder);
		Query query = entityManager
				.createQuery(new AuthorQueryBuilderForDataTable().getObjectsQuery(dataTableSearchHolder));
		query.setFirstResult(dataTableSearchHolder.getFirstRow());
		query.setMaxResults(dataTableSearchHolder.getRowsPerPage());
		List<Object[]> result = query.getResultList();
		log.info("The method done, that took {} milliseconds. Has been found {} authors.",
				(System.currentTimeMillis() - start), result.size());
		return result;
	}

	@Override
	public int findCountAuthorsForDataTable(DataTableSearchHolder dataTableSearchHolder) {
		Query query = entityManager
				.createQuery(new AuthorQueryBuilderForDataTable().getCountObjectsQuery(dataTableSearchHolder));

		long result;
		try {
			result = (long) query.getSingleResult();
			log.info("The method done. For current dataTable requirements = {}, has been found {} authors.",
					dataTableSearchHolder, result);

		} catch (NoResultException e) {
			log.info("The method done. For current dataTable requirements = {}, no authors found",
					dataTableSearchHolder);
			return 0;
		}
		return (int) result;
	}

	@Override
	public List<Author> findLatestAddedAuthors(int limit) {
		TypedQuery<Author> query = entityManager.createNamedQuery(Author.FIND_ALL_AUTHORS_SORTED_BY_DATE, Author.class);
		query.setMaxResults(limit);
		List<Author> authors = query.getResultList();
		log.info("The method done. Has been found {} authors.", authors.size());
		return authors;
	}
	


	class AuthorQueryBuilderForDataTable extends QueryBuilder {

		@Override
		protected void appendQueryPartSelectObjects() {
			sbForDataTable.append(SqlConstantsHolder.SELECT).append(SqlConstantsHolder.A)
					.append(SqlConstantsHolder.COMMA)
					.append(String.format(SqlConstantsHolder.AGREGATE_FUNC_DISTINCT_TEMPLATE, SqlConstantsHolder.COUNT,
							SqlConstantsHolder.B))
					.append(SqlConstantsHolder.COMMA)
					.append(String.format(SqlConstantsHolder.AGREGATE_FUNC_DISTINCT_TEMPLATE, SqlConstantsHolder.COUNT,
							SqlConstantsHolder.R))
					.append(SqlConstantsHolder.COMMA)
					.append(String.format(SqlConstantsHolder.ROUND_AVG_TEMPLATE_TO_TWO_DIGITS, SqlConstantsHolder.R,
							ReviewFieldHolder.RATING.getBusinessView()))
					.append(SqlConstantsHolder.AS).append(SqlConstantsHolder.RAT);
		}

		@Override
		protected void appendQueryPartSelectCountObjects() {
			sbForDataTable.append(SqlConstantsHolder.SELECT)
					.append(String.format(SqlConstantsHolder.AGREGATE_FUNC_TEMPLATE, SqlConstantsHolder.COUNT,
							SqlConstantsHolder.A, AuthorFieldHolder.ID.getBusinessView()))
					.append(SqlConstantsHolder.FROM).append(Author.class.getName()).append(SqlConstantsHolder.A)
					.append(SqlConstantsHolder.WHERE)
					.append(String.format(SqlConstantsHolder.FIELD_TEMPLATE, SqlConstantsHolder.A,
							AuthorFieldHolder.ID.getBusinessView()))
					.append(SqlConstantsHolder.IN).append('(').append(SqlConstantsHolder.SELECT)
					.append(String.format(SqlConstantsHolder.FIELD_TEMPLATE, SqlConstantsHolder.A,
							AuthorFieldHolder.ID.getBusinessView()));
		}

		@Override
		protected void appendQueryPartFrom() {
			sbForDataTable.append(SqlConstantsHolder.FROM).append(Author.class.getName()).append(SqlConstantsHolder.A)
					.append(SqlConstantsHolder.LEFT_JOIN)
					.append(String.format(SqlConstantsHolder.FIELD_TEMPLATE, SqlConstantsHolder.A,
							AuthorFieldHolder.BOOKS.getBusinessView()))
					.append(SqlConstantsHolder.B)
					.append(SqlConstantsHolder.LEFT_JOIN).append(String.format(SqlConstantsHolder.FIELD_TEMPLATE,
							SqlConstantsHolder.B, BookFieldHolder.REVIEWS.getBusinessView()))
					.append(SqlConstantsHolder.R);
		}

		@Override
		protected void appendQueryPartWhere() {
			if (dataTableSearchHolder.getFilterValues().containsKey(AuthorFieldHolder.FULL_NAME)) {
				String filterValue = dataTableSearchHolder.getFilterValues().get(AuthorFieldHolder.FULL_NAME);
				sbForDataTable.append(SqlConstantsHolder.WHERE)
						.append(String.format(SqlConstantsHolder.FIELD_TEMPLATE, SqlConstantsHolder.A,
								AuthorFieldHolder.FIRST_NAME.getBusinessView()))
						.append(String.format(SqlConstantsHolder.LIKE_TEMPLATE, filterValue))
						.append(SqlConstantsHolder.OR)
						.append(String.format(SqlConstantsHolder.FIELD_TEMPLATE, SqlConstantsHolder.A,
								AuthorFieldHolder.SECOND_NAME.getBusinessView()))
						.append(String.format(SqlConstantsHolder.LIKE_TEMPLATE, filterValue));
			}
		}

		@Override
		protected void appendQueryPartGroupBy() {
			sbForDataTable.append(SqlConstantsHolder.GROUP_BY).append(SqlConstantsHolder.A);
		}

		@Override
		protected void appendQueryPartHaving() {
			if (dataTableSearchHolder.getFilterValues().containsKey(AuthorFieldHolder.AVE_RATING)) {
				sbForDataTable.append(SqlConstantsHolder.HAVING)
						.append(String.format(SqlConstantsHolder.FLOOR_TEMPLATE, SqlConstantsHolder.R,
								ReviewFieldHolder.RATING.getBusinessView()))
						.append(SqlConstantsHolder.EQUAL).append(Integer
								.valueOf(dataTableSearchHolder.getFilterValues().get(AuthorFieldHolder.AVE_RATING)));
			}
		}

		@Override
		protected void appendQueryPartOrderBy() {
			sbForDataTable.append(SqlConstantsHolder.ORDER_BY);

			if (dataTableSearchHolder.getSortColumn() == null) {
				sbForDataTable.append(SqlConstantsHolder.RAT).append(SqlConstantsHolder.DESC)
						.append(SqlConstantsHolder.COMMA);
			} else {
				switch ((AuthorFieldHolder) dataTableSearchHolder.getSortColumn()) {
				case AVE_RATING:
					sbForDataTable.append(SqlConstantsHolder.RAT).append(dataTableSearchHolder.getSortOrder())
							.append(SqlConstantsHolder.COMMA);
					break;
				case FULL_NAME:
					sbForDataTable
							.append(String.format(SqlConstantsHolder.FIELD_TEMPLATE, SqlConstantsHolder.A,
									AuthorFieldHolder.SECOND_NAME.getBusinessView()))
							.append(dataTableSearchHolder.getSortOrder()).append(SqlConstantsHolder.COMMA);
					sbForDataTable
							.append(String.format(SqlConstantsHolder.FIELD_TEMPLATE, SqlConstantsHolder.A,
									AuthorFieldHolder.FIRST_NAME.getBusinessView()))
							.append(dataTableSearchHolder.getSortOrder()).append(SqlConstantsHolder.COMMA);
					break;
				case COUNT_BOOKS:
					sbForDataTable
							.append(String.format(SqlConstantsHolder.AGREGATE_FUNC_DISTINCT_TEMPLATE,
									SqlConstantsHolder.COUNT, SqlConstantsHolder.B))
							.append(dataTableSearchHolder.getSortOrder()).append(SqlConstantsHolder.COMMA);
					break;
				case COUNT_REVIEWS:
					sbForDataTable
							.append(String.format(SqlConstantsHolder.AGREGATE_FUNC_DISTINCT_TEMPLATE,
									SqlConstantsHolder.COUNT, SqlConstantsHolder.R))
							.append(dataTableSearchHolder.getSortOrder()).append(SqlConstantsHolder.COMMA);
					break;
				default:
					break;
				}
			}
			sbForDataTable.append(String.format(SqlConstantsHolder.FIELD_TEMPLATE, SqlConstantsHolder.B,
					AuthorFieldHolder.CREATED_DATE.getBusinessView())).append(SqlConstantsHolder.DESC);
		}

	}



}
