package com.softserveinc.model.persist.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.AuthorFieldHolder;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.BookFieldHolder;
import com.softserveinc.model.persist.entity.ReviewFieldHolder;
import com.softserveinc.model.persist.home.AuthorHomeLocal;
import com.softserveinc.model.session.util.DataTableSearchHolder;
import com.softserveinc.model.session.util.QueryBuilderForDataTable;
import com.softserveinc.model.session.util.SQLCommandConstants;

/**
 * The {@code AuthorFacade} class is an implementation facade (read) operations
 * for {@link Author} entity. This class is @Stateless.
 *
 */
@Stateless
public class AuthorFacade implements AuthorFacadeLocal, AuthorFacadeRemote {

	private static Logger log = LoggerFactory.getLogger(AuthorFacade.class);

	@PersistenceContext(unitName = PERSISTANCE_UNIT_PRIMARY)
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
	public Author findAuthorByFullName(String secondName, String firstName) {
		Author object = null;
		Query query = entityManager.createNamedQuery(Author.FIND_AUTHOR_BY_FULL_NAME);
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

	class AuthorQueryBuilderForDataTable extends QueryBuilderForDataTable implements SQLCommandConstants {

		@Override
		protected void appendQueryPartSelectObjects() {
			sbForDataTable.append(SELECT).append(A).append(COMMA)
					.append(String.format(AGREGATE_FUNC_DISTINCT_TEMPLATE, COUNT, B)).append(COMMA)
					.append(String.format(AGREGATE_FUNC_DISTINCT_TEMPLATE, COUNT, R)).append(COMMA).append(String
							.format(ROUND_AVG_TEMPLATE_TO_TWO_DIGITS, R, ReviewFieldHolder.RATING.getBusinessView()))
					.append(AS).append(RAT);
		}

		@Override
		protected void appendQueryPartSelectCountObjects() {
			sbForDataTable.append(SELECT)
					.append(String.format(AGREGATE_FUNC_TEMPLATE, COUNT, A, AuthorFieldHolder.ID.getBusinessView()))
					.append(FROM).append(Author.class.getName()).append(A).append(WHERE)
					.append(String.format(FIELD_TEMPLATE, A, AuthorFieldHolder.ID.getBusinessView())).append(IN)
					.append('(').append(SELECT)
					.append(String.format(FIELD_TEMPLATE, A, AuthorFieldHolder.ID.getBusinessView()));
		}

		@Override
		protected void appendQueryPartFrom() {
			sbForDataTable.append(FROM).append(Author.class.getName()).append(A).append(LEFT_JOIN)
					.append(String.format(FIELD_TEMPLATE, A, AuthorFieldHolder.BOOKS.getBusinessView())).append(B)
					.append(LEFT_JOIN)
					.append(String.format(FIELD_TEMPLATE, B, BookFieldHolder.REVIEWS.getBusinessView())).append(R);
		}

		@Override
		protected void appendQueryPartWhere() {
			if (dataTableSearchHolder.getFilterValues().containsKey(AuthorFieldHolder.FULL_NAME)) {
				String filterValue = dataTableSearchHolder.getFilterValues().get(AuthorFieldHolder.FULL_NAME);
				sbForDataTable.append(WHERE)
						.append(String.format(FIELD_TEMPLATE, A, AuthorFieldHolder.FIRST_NAME.getBusinessView()))
						.append(String.format(LIKE_TEMPLATE, filterValue)).append(OR)
						.append(String.format(FIELD_TEMPLATE, A, AuthorFieldHolder.SECOND_NAME.getBusinessView()))
						.append(String.format(LIKE_TEMPLATE, filterValue));
			}
		}

		@Override
		protected void appendQueryPartGroupBy() {
			sbForDataTable.append(GROUP_BY).append(A);
		}

		@Override
		protected void appendQueryPartHaving() {
			if (dataTableSearchHolder.getFilterValues().containsKey(AuthorFieldHolder.AVE_RATING)) {
				sbForDataTable.append(HAVING)
						.append(String.format(FLOOR_TEMPLATE, R, ReviewFieldHolder.RATING.getBusinessView()))
						.append(EQUAL).append(Integer
								.valueOf(dataTableSearchHolder.getFilterValues().get(AuthorFieldHolder.AVE_RATING)));
			}
		}

		@Override
		protected void appendQueryPartOrderBy() {
			sbForDataTable.append(ORDER_BY);

			if (dataTableSearchHolder.getSortColumn() == null) {
				sbForDataTable.append(RAT).append(DESC).append(COMMA);
			} else {
				switch ((AuthorFieldHolder) dataTableSearchHolder.getSortColumn()) {
				case AVE_RATING:
					sbForDataTable.append(RAT).append(dataTableSearchHolder.getSortOrder()).append(COMMA);
					break;
				case FULL_NAME:
					sbForDataTable
							.append(String.format(FIELD_TEMPLATE, A, AuthorFieldHolder.SECOND_NAME.getBusinessView()))
							.append(dataTableSearchHolder.getSortOrder()).append(COMMA);
					sbForDataTable
							.append(String.format(FIELD_TEMPLATE, A, AuthorFieldHolder.FIRST_NAME.getBusinessView()))
							.append(dataTableSearchHolder.getSortOrder()).append(COMMA);
					break;
				case COUNT_BOOKS:
					sbForDataTable.append(String.format(AGREGATE_FUNC_DISTINCT_TEMPLATE, COUNT, B))
							.append(dataTableSearchHolder.getSortOrder()).append(COMMA);
					break;
				case COUNT_REVIEWS:
					sbForDataTable.append(String.format(AGREGATE_FUNC_DISTINCT_TEMPLATE, COUNT, R))
							.append(dataTableSearchHolder.getSortOrder()).append(COMMA);
					break;
				default:
					break;
				}
			}
			sbForDataTable.append(String.format(FIELD_TEMPLATE, B, AuthorFieldHolder.CREATED_DATE.getBusinessView()))
					.append(DESC);
		}

	}

}
