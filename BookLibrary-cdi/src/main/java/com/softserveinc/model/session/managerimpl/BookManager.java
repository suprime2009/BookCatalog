package com.softserveinc.model.session.managerimpl;

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


	@Transactional
	@Override
	public void deleteBook(Book book) {
		bookHomeLocal.delete(book);
		log.info("Book {} has been deleted from database", book);
	}

	@Override
	public List<Object> getBookForDataTable(DataTableSearchHolder dataTableHelper) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public void deleteListBooks(List<Book> list) {
		for (Book book : list) {
			bookHomeLocal.delete(book);
		}
		
	}

	@Transactional
	@Override
	public Book createBook(Book book) {
		System.out.println("createBook");
		Book bookCreated = bookHomeLocal.create(book);
		return bookCreated;
		
	}

	@Override
	public void updateBook(Book book) {
		bookHomeLocal.update(book);
		
	}
	
	
	
}
