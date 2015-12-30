package com.softserveinc.model.session.managerImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.activation.DataHandler;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.richfaces.component.SortOrder;
import org.richfaces.model.ArrangeableState;
import org.richfaces.model.SortField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.OrderBy;
import com.softserveinc.model.persist.entity.Review;
import com.softserveinc.model.persist.facade.BookFacadeLocal;
import com.softserveinc.model.persist.facade.ReviewFacadeLocal;
import com.softserveinc.model.persist.home.BookHomeLocal;
import com.softserveinc.model.session.manager.BookManagerLocal;
import com.softserveinc.model.session.manager.BookManagerRemote;
import com.softserveinc.model.session.util.ConstantsUtil;
import com.softserveinc.model.session.util.DataTableSearchHolder;

/**
 * BookManager class is an implementation business operations for Book entity.
 * This class is @Stateless.
 */
@Stateless
public class BookManager implements BookManagerLocal, BookManagerRemote, ConstantsUtil {

	private static Logger log = LoggerFactory.getLogger(BookManager.class);
	public static final String RATING = "rating";

	private StringBuilder sbForDataTable;
	private DataTableSearchHolder dataTableHelper;

	@EJB
	private BookHomeLocal bookHomeLocal;

	@EJB
	private BookFacadeLocal bookFacadeLocal;

	@EJB
	private ReviewFacadeLocal reviewFacadeLocal;

	public BookManager() {
	}

	@PostConstruct
	private void postConstruct() {
		log.info("Bean BookManager has been created.");
	}

	@PreDestroy
	private void preDestroy() {
		log.info("Bean BookManager has been destroyed.");
	}

	public List<Book> getAllBooks() {
		List<Book> list = bookFacadeLocal.findAll();
		log.info("Method has been successfully finished.");
		return list;
	}

	@Override
	public Book getBookByID(String id) {
		Book book = bookHomeLocal.findByID(id);
		log.info("Method has been successfully finished.");
		return book;
	}

	@Transactional
	@Override
	public void setRatingForBooks(List<Book> list) {
		for (Book b : list) {
			b.setRating(Double.valueOf(reviewFacadeLocal.findAverageRatingForBook(b)));
		}
		log.info("Method has been successfully finished.");
	}

	@Override
	public void sortByBookName(List<Book> list, final OrderBy order) {
		Collections.sort(list, new Comparator<Book>() {
			public int compare(Book b1, Book b2) {
				if (order.equals(OrderBy.ASC)) {
					return b1.getBookName().compareToIgnoreCase(b2.getBookName());
				} else {
					return b2.getBookName().compareToIgnoreCase(b1.getBookName());
				}
			}
		});
		log.info("Books has been successfully sorted by bookName");
	}

	@Override
	public void sortByYearPublished(List<Book> list, final OrderBy order) {
		Collections.sort(list, new Comparator<Book>() {
			public int compare(Book b1, Book b2) {
				if (order.equals(OrderBy.ASC)) {
					return b1.getYearPublished().compareTo(b2.getYearPublished());
				} else {
					return b2.getYearPublished().compareTo(b1.getYearPublished());
				}
			}
		});
		log.info("Books has been successfully sorted by yearPublished");
	}

	@Override
	public void sortByPublisher(List<Book> list, final OrderBy order) {
		Collections.sort(list, new Comparator<Book>() {
			public int compare(Book b1, Book b2) {
				if (order.equals(OrderBy.ASC)) {
					return b1.getPublisher().compareTo(b2.getPublisher());
				} else {
					return b2.getPublisher().compareTo(b1.getPublisher());
				}
			}
		});
		log.info("Books has been successfully sorted by Publisher");
	}

	@Override
	public void sortByCreatedDate(List<Book> list) {
		Collections.sort(list, new Comparator<Book>() {
			public int compare(Book b1, Book b2) {
				return b1.getCreatedDate().compareTo(b2.getCreatedDate());
			}
		});
		log.info("Books has been successfully sorted by createdDate");
	}

	@Override
	public void sortByRating(List<Book> list, final OrderBy order) {
		Collections.sort(list, new Comparator<Book>() {
			public int compare(Book b1, Book b2) {
				if (order.equals(OrderBy.ASC)) {
					return b1.getRating().compareTo(b2.getRating());
				} else {
					return b2.getRating().compareTo(b1.getRating());
				}
			}
		});
		log.info("Books has been successfully sorted by Rating");
	}

	private List<Order> checkSortOrder(CriteriaBuilder criteriaBuilder, Root<Book> root,
			DataTableSearchHolder dataTableHelper) {
		System.out.println("checkSortOrder start");
		List<Order> list = new ArrayList<Order>();
		Path<Object> expression = null;
		if (dataTableHelper.getSortColumn() != null) {

			expression = root.get(dataTableHelper.getSortColumn());
			if (dataTableHelper.getSortOrder() == SortOrder.ascending) {
				System.out.println("ASKENDING");
				list.add(criteriaBuilder.asc(expression));
			} else if (dataTableHelper.getSortOrder() == SortOrder.descending) {
				System.out.println("DESCENDING");
				list.add(criteriaBuilder.desc(expression));
			}
		}

		System.out.println("checkSortOrder done");
		return list;
	}

	@Override
	public List<Book> getBooksForDataTable(SequenceRange sequenceRange, ArrangeableState arrangeableState,
			Map<String, SortOrder> sortOrders, Map<String, String> filterValues) {
		// System.out.println("getBooksForDataTable start");
		//
		//
		// CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		// CriteriaQuery<Book> criteriaQuery =
		// criteriaBuilder.createQuery(Book.class);
		// Root<Book> root = criteriaQuery.from(Book.class);
		// List<Order> orders = checkSortOrder(criteriaBuilder, root,
		// sortOrders);
		// if (!orders.isEmpty()) {
		// criteriaQuery.orderBy(orders);
		// }
		//
		// TypedQuery<Book> query = entityManager.createQuery(criteriaQuery);
		//
		// if (sequenceRange.getFirstRow() >= 0 && sequenceRange.getRows() > 0)
		// {
		//
		// query.setFirstResult(sequenceRange.getFirstRow());
		// query.setMaxResults(sequenceRange.getRows());
		// }
		//
		// List<Book> books = query.getResultList();

		return null;
	}







	@Transactional
	@Override
	public List<Object> getBookForDataTable(DataTableSearchHolder dataTableHelper) {
		
	
		return null;
	}
	
	
	
}
