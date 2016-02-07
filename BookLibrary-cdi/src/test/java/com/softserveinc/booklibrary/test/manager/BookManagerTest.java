package com.softserveinc.booklibrary.test.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.transaction.Transactional;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.softserveinc.booklibrary.action.helper.DataTableSearchHolder;
import com.softserveinc.booklibrary.exception.BookManagerException;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.model.entity.Review;
import com.softserveinc.booklibrary.session.manager.BookManagerLocal;
import com.softserveinc.booklibrary.session.manager.impl.BookManager;
import com.softserveinc.booklibrary.session.persist.facade.AuthorFacadeLocal;
import com.softserveinc.booklibrary.session.persist.facade.BookFacadeLocal;
import com.softserveinc.booklibrary.session.persist.facade.IBookFacade;
import com.softserveinc.booklibrary.session.persist.facade.impl.BookFacade;
import com.softserveinc.booklibrary.session.persist.home.ReviewHomeLocal;
import com.softserveinc.booklibrary.session.persist.home.impl.AuthorHome;
import com.softserveinc.booklibrary.session.persist.home.impl.BookHome;
import com.softserveinc.booklibrary.session.persist.home.impl.ReviewHome;
import com.softserveinc.booklibrary.session.util.QueryBuilderForDataTable;
import com.softserveinc.booklibrary.session.util.SQLCommandConstants;
import com.softserveinc.model.util.DBUnitHelper;
import com.softserveinc.model.util.DataBaseConstants;

public class BookManagerTest extends Arquillian  {
	

	private static Logger log = LoggerFactory.getLogger(BookManagerTest.class);

	@EJB
	private BookFacadeLocal bookFacade;

	@EJB
	private BookManagerLocal bookManager;

	@EJB
	private AuthorFacadeLocal authorFacade;
	
	@Deployment
	public static Archive<?> createTestArchive() throws IOException {
		File[] files = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve()
				.withTransitivity().asFile();
		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war");

		war.addAsLibraries(files);

		war.addPackages(true, Author.class.getPackage());
		war.addPackages(true, AuthorHome.class.getPackage());
		war.addPackages(true, Book.class.getPackage());
		war.addPackages(true, BookHome.class.getPackage());
		war.addPackages(true, Review.class.getPackage());
		war.addPackages(true, ReviewHome.class.getPackage());
		war.addPackages(true, ReviewHomeLocal.class.getPackage());
		war.addPackages(true, BookFacadeLocal.class.getPackage());
		war.addPackages(true, BookFacade.class.getPackage());
		war.addPackages(true, IBookFacade.class.getPackage());
		war.addPackages(true, SQLCommandConstants.class.getPackage());
		war.addPackages(true, QueryBuilderForDataTable.class.getPackage());
		war.addPackages(true, DataTableSearchHolder.class.getPackage());
		war.addPackages(true, DBUnitHelper.class.getPackage());
		war.addPackages(true, DataBaseConstants.class.getPackage());
		war.addPackages(true, BookManager.class.getPackage());
		war.addPackages(true, BookManagerLocal.class.getPackage());
		war.addPackages(true, BookManagerException.class.getPackage());
		war.addPackages(true, ManagerTestUlil.class.getPackage());
		
		war.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
		war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

		return war;
	}

	@Transactional
	@Test
	@UsingDataSet("dataset/datasetForBookManager.xml")
	@ShouldMatchDataSet(value = "dataset/expectingDataSetForCreateBook.xml", excludeColumns = { "book_id",
			"created_date" })
	public void testCreateBook() throws BookManagerException {
		/* single book with no authors */
		Book book = new Book("Create1", "ISBN-13: 978-1-236-54125-0", "Canada", 2012, new HashSet<Author>());
		bookManager.createBook(book);

		/* single book with no authors and no required year */
		book = new Book("Create2", "ISBN-10: 1-871-11411-5", "England", null, new HashSet<Author>());
		bookManager.createBook(book);

		/* Book with authors, that present in DB (test cascade update) */
		Set<Author> authors = new HashSet<Author>();
		Author author = authorFacade.findById("a32");
		authors.add(author);
		author = authorFacade.findById("a31");
		authors.add(author);
		book = new Book("Create3", "ISBN-10: 1-873-11411-5", "Canada", 2013, authors);
		bookManager.createBook(book);
	}

	@Transactional
	@Test(dependsOnMethods = {
			"testCreateBook" }, expectedExceptions = BookManagerException.class, dataProvider = "negativeCreateBookDataProvider", dataProviderClass = ManagerTestUlil.class)
	@Cleanup(phase = TestExecutionPhase.AFTER, strategy = CleanupStrategy.USED_ROWS_ONLY)
	public void testNegativeCreateBook(Object object) throws BookManagerException {
		Book book = (Book) object;
		bookManager.createBook(book);
	}

	@Transactional
	@Test
	@UsingDataSet("dataset/datasetForBookManager.xml")
	@ShouldMatchDataSet(value = "dataset/expectingDataSetForUpdateBook.xml")
	public void testUpdateBook() throws BookManagerException {
		/* simple update book */
		Book book = bookFacade.findById("b103");
		book.setPublisher("New Publisher");
		book.setBookName("New Book name");
		book.setYearPublished(1990);
		book.setIsbn("ISBN-13: 978-1-286-74595-7");
		bookManager.updateBook(book);

		/* update authors of book */
		book = bookFacade.findById("b100");
		Author author = authorFacade.findById("a32");
		book.getAuthors().add(author);
		author = authorFacade.findById("a31");
		book.getAuthors().add(author);
		bookManager.updateBook(book);

		book = bookFacade.findById("b101");
		book.setAuthors(new HashSet<Author>());
		bookManager.updateBook(book);

	}

	// @Transactional
	// @Test(dependsOnMethods = {
	// "testUpdateBook" }, expectedExceptions = BookManagerException.class,
	// dataProvider = "negativeUpdateBookDataProvider", dataProviderClass =
	// ManagerTestUlil.class)
	// @Cleanup(phase = TestExecutionPhase.AFTER, strategy =
	// CleanupStrategy.USED_ROWS_ONLY)
	// public void testNegativeUpdateBook(Object object) throws
	// BookManagerException {
	// Book book = (Book) object;
	// bookManager.createBook(book);
	// }

	@Test
	@UsingDataSet("dataset/datasetForBookManager.xml")
	@Cleanup(phase = TestExecutionPhase.AFTER, strategy = CleanupStrategy.USED_ROWS_ONLY)
	public void testDeleteBook() throws BookManagerException {
		String bookId = "b100";
		Book book = bookFacade.findById(bookId);
		assertNotNull(book);

		List<String> authorIds = new ArrayList<String>();
		for (Author author : book.getAuthors()) {
			authorIds.add(author.getIdAuthor());
		}

		bookManager.deleteBook(bookId);
		book = bookFacade.findById(bookId);
		assertNull(book);
		List<Author> authors = authorFacade.findAuthorsByListId(authorIds);
		assertEquals(authorIds.size(), authors.size());

	}

	@Test(expectedExceptions = BookManagerException.class, dataProvider = "negativeDeleteBookDataProvider", dataProviderClass = ManagerTestUlil.class)
	@Cleanup(phase = TestExecutionPhase.AFTER, strategy = CleanupStrategy.USED_ROWS_ONLY)
	public void testNegativeDeleteBook(String bookId) throws BookManagerException {
		bookManager.deleteBook(bookId);
	}
	
	@Test
	@Transactional
	@UsingDataSet("dataset/datasetForBookManager.xml")
	@ShouldMatchDataSet(value = "dataset/expectingDataSetForBulkDeleteBook.xml")
	public void testBulkDelete() throws BookManagerException {
		
		String [] idsBook = {"b100", "b103", "b104"};
		List<Book> books = bookFacade.findBooksByListId((List<String>) Arrays.asList(idsBook));
		bookManager.bulkDelete(books);
		
	}
	
	
	public void testNegativeBulkDelete() throws BookManagerException {
		
	}

}
