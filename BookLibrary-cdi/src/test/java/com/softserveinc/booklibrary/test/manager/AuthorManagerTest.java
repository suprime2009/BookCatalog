package com.softserveinc.booklibrary.test.manager;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.transaction.Transactional;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.TestExecutionPhase;

import com.softserveinc.booklibrary.action.helper.DataTableSearchHolder;
import com.softserveinc.booklibrary.exception.AuthorManagerException;
import com.softserveinc.booklibrary.exception.BookCatalogException;
import com.softserveinc.booklibrary.exception.BookManagerException;
import com.softserveinc.booklibrary.exception.ReviewManagerException;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.model.entity.Review;
import com.softserveinc.booklibrary.rest.client.AuthorClient;
import com.softserveinc.booklibrary.rest.client.AuthorClientImpl;
import com.softserveinc.booklibrary.rest.dto.AuthorDTO;
import com.softserveinc.booklibrary.rest.service.AuthorService;
import com.softserveinc.booklibrary.rest.service.AuthorServiceImpl;
import com.softserveinc.booklibrary.rest.util.JsonFieldsHolder;
import com.softserveinc.booklibrary.session.manager.AuthorManagerLocal;
import com.softserveinc.booklibrary.session.manager.BookManagerLocal;
import com.softserveinc.booklibrary.session.manager.ReviewManagerLocal;
import com.softserveinc.booklibrary.session.manager.impl.AuthorManager;
import com.softserveinc.booklibrary.session.manager.impl.BookManager;
import com.softserveinc.booklibrary.session.manager.impl.ReviewManager;
import com.softserveinc.booklibrary.session.persist.facade.AuthorFacadeLocal;
import com.softserveinc.booklibrary.session.persist.facade.BookFacadeLocal;
import com.softserveinc.booklibrary.session.persist.facade.IBookFacade;
import com.softserveinc.booklibrary.session.persist.facade.impl.BookFacade;
import com.softserveinc.booklibrary.session.persist.home.AuthorHomeLocal;
import com.softserveinc.booklibrary.session.persist.home.ReviewHomeLocal;
import com.softserveinc.booklibrary.session.persist.home.impl.AuthorHome;
import com.softserveinc.booklibrary.session.persist.home.impl.BookHome;
import com.softserveinc.booklibrary.session.persist.home.impl.ReviewHome;
import com.softserveinc.booklibrary.session.util.QueryBuilderForDataTable;
import com.softserveinc.booklibrary.session.util.SQLCommandConstants;
import com.softserveinc.booklibrary.test.rest.AuthorRestTest;
import com.softserveinc.model.util.DBUnitHelper;
import com.softserveinc.model.util.DataBaseConstants;

public class AuthorManagerTest extends Arquillian {
	
	private static final String CREATE_GROUP = "createAuthor";
	private static final String DELETE_GROUP = "updateAuthor";
	private static final String DELETE_AUTHOR = "testDeleteAuthor";

	@EJB
	private AuthorManagerLocal authorManager;

	@EJB
	private AuthorHomeLocal authorHome;

	@EJB
	private AuthorFacadeLocal authorFacade;

	@EJB
	private BookFacadeLocal bookFacade;
	
	@Deployment
	public static Archive<?> createTestArchive() throws IOException {
		File[] files = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve()
				.withTransitivity().asFile();
		WebArchive war = ShrinkWrap.create(WebArchive.class, "authorManager.war");

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
		war.addPackages(true, AuthorManager.class.getPackage());
		war.addPackages(true, AuthorManagerLocal.class.getPackage());
		war.addPackages(true, AuthorManagerException.class.getPackage());
		war.addPackages(true, ManagerTestUlil.class.getPackage());

		war.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
		war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

		return war;
	}

	@Test(groups = CREATE_GROUP)
	@Cleanup(phase = TestExecutionPhase.AFTER, strategy = CleanupStrategy.STRICT)
	public void testCreateAuthor() throws AuthorManagerException {

		Author author = new Author("Hose", "Marin");
		authorManager.createAuthor(author);
		assertNotNull(author.getIdAuthor());
		Author after = authorFacade.findById(author.getIdAuthor());
		assertNotNull(after.getCreatedDate());
		assertTrue(author.equalsNotCreatedBook(after));

	}

	@Transactional
	@Test(expectedExceptions = AuthorManagerException.class, dataProvider = "negativeCreateAuthorDataProvider", dataProviderClass = ManagerTestUlil.class, groups = {
			CREATE_GROUP }, dependsOnMethods = { "testCreateAuthor" })
	@Cleanup(phase = TestExecutionPhase.NONE)
	public void testNegativeCreateAuthor(Author object) throws AuthorManagerException {
		authorManager.createAuthor(object);
	}

	@Transactional
	@Test(dependsOnGroups = { DELETE_GROUP })
	@UsingDataSet("dataset/datasetForAuthorManager.xml")
	@ShouldMatchDataSet(value = "dataset/expectingDataSetForUpdateAuthor.xml")
	public void testUpdateAuthor() throws AuthorManagerException {

		Author author = authorFacade.findById("a42");
		author.setFirstName("Stepan");
		author.setSecondName("Novikov");
		authorManager.updateAuthor(author);

		author = authorFacade.findById("a40");
		author.setBooks(new ArrayList<Book>());
		authorManager.updateAuthor(author);

		author = authorFacade.findById("a41");
		List<Book> books = bookFacade.findBooksByAuthor(author);
		books.add(bookFacade.findById("b95"));
		books.add(bookFacade.findById("b94"));
		author.setBooks(books);
		authorManager.updateAuthor(author);

	}

	@Transactional
	@Test(expectedExceptions = AuthorManagerException.class, dataProvider = "negativeUpdateAuthorDataProvider", dataProviderClass = ManagerTestUlil.class, dependsOnMethods = {
			"testUpdateAuthor" })
	@Cleanup(phase = TestExecutionPhase.AFTER, strategy = CleanupStrategy.USED_ROWS_ONLY)
	public void testNegativeUpdateAuthor(Author object) throws AuthorManagerException {
		authorManager.updateAuthor(object);
	}

	@Transactional
	@Test(groups = DELETE_GROUP, dependsOnGroups = { CREATE_GROUP })
	@UsingDataSet("dataset/datasetForAuthorManager.xml")
	@Cleanup(phase = TestExecutionPhase.NONE)
	public void testDeleteAuthor() throws AuthorManagerException {
		String authorId = "a40";
		Author author = authorFacade.findById(authorId);
		assertNotNull(author);

		List<Book> books = bookFacade.findBooksByAuthor(author);
		assertEquals(books.size(), 3);

		authorManager.deleteAuthor(authorId);
		author = authorFacade.findById(authorId);
		assertNull(author);

		Book book = bookFacade.findById(books.get(0).getIdBook());
		assertFalse(book.getAuthors().contains(author));

		/* delete author with no books positive */
		authorId = "a42";
		author = authorFacade.findById(authorId);
		assertNotNull(author);
		authorManager.deleteAuthorWithNoBooks(authorId);
		author = authorFacade.findById(authorId);
		assertNull(author);
	}

	@Transactional
	@Test(expectedExceptions = AuthorManagerException.class, dataProvider = "negativeDeleteAuthorDataProvider", dataProviderClass = ManagerTestUlil.class, groups = DELETE_GROUP, dependsOnMethods = {
			DELETE_AUTHOR })
	@Cleanup(phase = TestExecutionPhase.NONE)
	public void testNegativeDeleteAuthor(String idAuthor) throws AuthorManagerException {
		authorManager.deleteAuthor(idAuthor);
	}

	@Test(dependsOnMethods = {
			DELETE_AUTHOR }, groups = "testNegativeDeleteAuthorWithNoBooks", expectedExceptions = AuthorManagerException.class)
	@Cleanup(phase = TestExecutionPhase.NONE)
	public void testNegativeDeleteAuthorWithNoBooks() throws AuthorManagerException {
		Author author = authorFacade.findById("a41");
		assertNotNull(author);
		authorManager.deleteAuthorWithNoBooks(author.getIdAuthor());
	}

	@Transactional
	@Test(groups = DELETE_GROUP, dependsOnMethods = { "testNegativeDeleteAuthorWithNoBooks" })
	@Cleanup(phase = TestExecutionPhase.NONE)
	public void testBulkDeleteAuthor() throws AuthorManagerException {
		Author author = authorFacade.findById("a43");
		List<Author> authors = new ArrayList<Author>();
		authors.add(author);
		authorManager.bulkDeleteAuthorsWithNoBooks(authors);
		author = null;
		author = authorFacade.findById("a43");

	}

	@Transactional
	@Test(expectedExceptions = AuthorManagerException.class, dependsOnMethods = { "testBulkDeleteAuthor" })
	@ShouldMatchDataSet(value = "dataset/expectingDataSetForDeleteAuthor.xml")
	public void testNegativeBulkDeleteAuthors() throws AuthorManagerException {
		List<Author> authors = new ArrayList<Author>();
		authors.add(authorFacade.findById("a41"));
		authors.add(authorFacade.findById("a44"));
		authorManager.bulkDeleteAuthorsWithNoBooks(authors);
	}

}
