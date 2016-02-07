package com.softserveinc.booklibrary.session.manager.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.booklibrary.exception.BookManagerException;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.session.manager.BookManagerLocal;
import com.softserveinc.booklibrary.session.manager.BookManagerRemote;
import com.softserveinc.booklibrary.session.persist.facade.AuthorFacadeLocal;
import com.softserveinc.booklibrary.session.persist.facade.BookFacadeLocal;
import com.softserveinc.booklibrary.session.persist.facade.ReviewFacadeLocal;
import com.softserveinc.booklibrary.session.persist.home.BookHomeLocal;
import com.softserveinc.booklibrary.session.persist.home.ReviewHomeLocal;

/**
 * The {@code BookManager} class is an implementation of business logic for
 * write operations for {@link Book} entity
 */
@Stateless
public class BookManager implements BookManagerLocal, BookManagerRemote {

	private static Logger log = LoggerFactory.getLogger(BookManager.class);

	@EJB
	private BookHomeLocal bookHome;

	@EJB
	private BookFacadeLocal bookFacade;

	@EJB
	private ReviewFacadeLocal reviewFacade;

	@EJB
	private ReviewHomeLocal reviewHome;

	@EJB
	private AuthorFacadeLocal authorFacade;

	public BookManager() {

	}

	@PostConstruct
	private void postConstruct() {
		log.debug("Bean has been created.");
	}

	@PreDestroy
	private void preDestroy() {
		log.debug("Bean has been destroyed.");
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void createBook(Book book) throws BookManagerException {

		long startMethodTime = System.currentTimeMillis();
		log.debug("Method starts. Book to create ={}", book);
		String errorMessage = "";
		if (book == null || book.getIdBook() != null) {
			errorMessage = "Passed book to create is null or has present id number.";
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
			throw new BookManagerException(errorMessage);
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
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void bulkDelete(List<Book> list) throws BookManagerException {
		String errorMessage = "";
		if (list == null || list.isEmpty()) {
			errorMessage = "List Books to delete is empty.";
			log.error(errorMessage);
			throw new BookManagerException(errorMessage);
		}
		int countReviews = reviewHome.bulkRemoveByBook(list);
		int countBooks = bookHome.bulkRemove(list);
		log.info("The method done. Has been deleted {} books and {} reviews for them.", countBooks, countReviews);

	}

	/**
	 * This method provide checking <code>Book</code> instance variables in
	 * accordance with the business requirements. <code>Book</code> instance
	 * passed from UI component and should be created in system or updated. If
	 * any conditions do not meet the requirements for business logic for Book
	 * instance, method throw {@link BookManagerException}.
	 * 
	 * @param review
	 *            {@link Book} instance that should be checked.
	 * @throws BookManagerException
	 *             exception
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	private void validateBookFields(Book book) throws BookManagerException {
		String errorMessage = "";
		if (book.getBookName() == null || book.getIsbn() == null || book.getPublisher() == null) {
			errorMessage = "Book name, ISBN number and publisher are required for book.";
			log.error(errorMessage);
			throw new BookManagerException(errorMessage);
		}
		if (book.getBookName().length() < 2 || book.getBookName().length() >= 80) {
			errorMessage = "Book name lenght must be between 3 and 80 characters.";
			log.error(errorMessage);
			throw new BookManagerException(errorMessage);
		}
		if (book.getPublisher().length() < 2 || book.getPublisher().length() >= 80) {
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
		if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
			validateAuthorsField(book);
		}

	}

	/**
	 * The method checks authors for passed by parameter {@link Book} instance.
	 * If some from authors in null or id number for this author is null method
	 * will throw {@link BookManagerException}.
	 * 
	 * @param book
	 *            Entity to validation
	 * @throws BookManagerException
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	private void validateAuthorsField(Book book) throws BookManagerException {
		String errorMessage = "";
		List<String> authorsIds = new ArrayList<String>();
		for (Author author : book.getAuthors()) {
			if (author.getIdAuthor() != null) {
				authorsIds.add(author.getIdAuthor());
			} else {
				errorMessage = String.format("Author %s has no id number.", author);
				log.error(errorMessage);
				throw new BookManagerException(errorMessage);
			}
		}

		Set<Author> presentAuthors = new HashSet<>(authorFacade.findAuthorsByListId(authorsIds));
		if (!presentAuthors.equals(book.getAuthors())) {
			errorMessage = "Some author from authors of the book has been modified and is not reflected.";
			log.error(errorMessage);
			throw new BookManagerException(errorMessage);
		}

	}

}
