package com.softserveinc.model.manager;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.OrderBy;
import com.softserveinc.model.persist.facade.BookFacadeLocal;
import com.softserveinc.model.persist.facade.ReviewFacadeLocal;
import com.softserveinc.model.persist.home.BookHomeLocal;
import com.softserveinc.model.util.SortableDataModel;

@Stateless
public class BookManager implements BookManagerLocal, BookManagerRemote {

	private static Logger log = LoggerFactory.getLogger(BookManager.class);

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
		// list.sortBy(new Comparator<Book>() {
		// public int compare(Book b1, Book b2) {
		// if (order.equals(OrderBy.ASC)) {
		// System.out.println("ask");
		// return b1.getBookName().compareToIgnoreCase(b2.getBookName());
		// } else {
		// System.out.println("desc");
		// return b2.getBookName().compareToIgnoreCase(b1.getBookName());
		// }
		// }
		// });
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
}
