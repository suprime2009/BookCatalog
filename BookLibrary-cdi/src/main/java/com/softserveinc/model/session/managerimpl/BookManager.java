package com.softserveinc.model.session.managerimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.softserveinc.exception.AuthorManagerException;
import com.softserveinc.exception.BookCatalogException;
import com.softserveinc.exception.BookManagerException;
import com.softserveinc.exception.ReviewManagerException;
import com.softserveinc.model.persist.entity.Author;
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

	@EJB
	private BookHomeLocal bookHome;

	@EJB
	private BookFacadeLocal bookFacade;

	@EJB
	private ReviewFacadeLocal reviewFacade;

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

	@Override
	public void createBook(Book book) throws BookManagerException, BookCatalogException {

		long startMethodTime = System.currentTimeMillis();
		log.debug("Method starts. Book to create ={}", book);
		String errorMessage = "";
		if (book == null) {
			errorMessage = "Passed book to create is null.";
			log.error(errorMessage);
			throw new BookManagerException(errorMessage);
		}
		validateBookFields(book);
		Book checkISBN = bookFacade.findBookByISNBN(book.getIsbn());
		if (checkISBN != null) {
			errorMessage = String.format("Passed ISBN number %s already in use by Book=%s.", book.getIsbn(), checkISBN);
			log.error(errorMessage);
			throw new BookManagerException(errorMessage);
		}
		bookHome.create(book);
		if (book.getIdBook() == null) {
			errorMessage = "Creation of Book is not successful. Unexpected exception.";
			log.error(errorMessage);
			throw new BookCatalogException(errorMessage);
		}
		long endMethodTime = System.currentTimeMillis();
		log.info("Method successfully finished. That took {} milliseconds. Book {} has been created.",
				(endMethodTime - startMethodTime), book);
	}

	@Override
	public void updateBook(Book book) throws BookManagerException {
		
		log.debug("The method starts. Book to update ={}", book);
		String errorMessage = "";
		validateBookFields(book);
		Book checkISBN = bookFacade.findBookByISNBN(book.getIsbn());
		if (checkISBN != null && !checkISBN.getIdBook().equals(book.getIdBook())) {
			errorMessage = String.format("Passed ISBN number %s already in use.", book.getIsbn());
			log.error(errorMessage);
			throw new BookManagerException(errorMessage);
		}
		bookHome.update(book);
		log.info("The method finished. Book {} has been successfully updated.", book);

	}

	@Override
	public void deleteBook(String idBook) throws BookManagerException {
		log.debug("Method starts. The book id ={}", idBook);
		String errorMessage = "";
		if (idBook == null) {
			errorMessage = "Passed id cannot be null.";
			log.error(errorMessage);
			throw new BookManagerException(errorMessage);
		}
		Book book = bookFacade.findById(idBook);
		if (book == null) {
			errorMessage = String.format("The Book by id= %s hasn't found.", idBook);
			log.error(errorMessage);
			throw new BookManagerException(errorMessage);
		}
		bookHome.delete(book);
		log.info("Method finished. The Book {} has been deleted.", book);
	}

	@Override
	public void bulkDelete(List<Book> list) throws BookManagerException {
		String errorMessage = "";
		if (list == null || list.isEmpty()) {
			errorMessage = "List Books to delete is empty.";
			log.error(errorMessage);
			throw new BookManagerException(errorMessage);
		}
		
		bookHome.bulkRemove(list);

	}

	@Override
	public void setRatingForBooks(List<Book> list) {
		for (Book b : list) {
			b.setRating(Double.valueOf(reviewFacade.findAverageRatingForBook(b)));
		}
		log.info("Method has been successfully finished.");
	}

	private void validateBookFields(Book book) throws BookManagerException {
		String errorMessage = "";
		if (book.getBookName() == null || book.getIsbn() == null || book.getPublisher() == null) {
			errorMessage = "Book name, ISBN number and publisher are required for book.";
			log.error(errorMessage);
			throw new BookManagerException(errorMessage);
		}
		if (book.getBookName().length() < 3 || book.getBookName().length() > 80) {
			errorMessage = "Book name lenght must be between 3 and 80 characters.";
			log.error(errorMessage);
			throw new BookManagerException(errorMessage);
		}
		if (book.getPublisher().length() < 3 || book.getPublisher().length() > 80) {
			errorMessage = "Publisher lenght must be between 3 and 80 characters.";
			log.error(errorMessage);
			throw new BookManagerException(errorMessage);
		}
		if (book.getYearPublished() != null) {
			if (String.valueOf(book.getYearPublished()).length() != 4) {
				errorMessage = "Year published must containt 4 numbers.";
				log.error(errorMessage);
				throw new BookManagerException(errorMessage);
			}
		}
		String regex = "^(ISBN(-1[03]): )(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(book.getIsbn());
		if (!matcher.matches()) {
			errorMessage = "ISBN is not valid.";
			log.error(errorMessage);
			throw new BookManagerException(errorMessage);
		}
	}

}
