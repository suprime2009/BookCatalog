package com.softserveinc.model.util;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.softserveinc.booklibrary.session.persist.facade.AuthorFacadeRemote;
import com.softserveinc.booklibrary.session.persist.facade.BookFacadeRemote;
import com.softserveinc.booklibrary.session.persist.facade.ReviewFacadeRemote;
import com.softserveinc.booklibrary.session.persist.facade.impl.AuthorFacade;
import com.softserveinc.booklibrary.session.persist.facade.impl.BookFacade;
import com.softserveinc.booklibrary.session.persist.facade.impl.ReviewFacade;
import com.softserveinc.booklibrary.session.persist.home.AuthorHomeRemote;
import com.softserveinc.booklibrary.session.persist.home.BookHomeRemote;
import com.softserveinc.booklibrary.session.persist.home.ReviewHomeRemote;
import com.softserveinc.booklibrary.session.persist.home.impl.AuthorHome;
import com.softserveinc.booklibrary.session.persist.home.impl.BookHome;
import com.softserveinc.booklibrary.session.persist.home.impl.ReviewHome;

/**
 * This abstract class extends for test class. Class has two method which runs
 * before and after test class appropriately. Class gets remote beans instances,
 * insert test data into database and clears database after tests.
 *
 */
public abstract class BaseTest {

	protected static AuthorHomeRemote authorHomeRemote;
	protected static AuthorFacadeRemote authorFacadeRemote;
	protected static BookHomeRemote bookHomeRemote;
	protected static BookFacadeRemote bookFacadeRemote;
	protected static ReviewHomeRemote reviewHomeRemote;
	protected static ReviewFacadeRemote reviewFacadeRemote;
	protected static TestHelper testHelper;
	protected static DBUnitHelper dbUnitHelper = DBUnitHelper.getInstance();

	/**
	 * This method runs before all tests. Method gets TestHelper instance,
	 * creates instances of remote beans.
	 */
	@BeforeSuite
	public void getRemoteBeans() {
		testHelper = TestHelper.getInstance();
		authorHomeRemote = (AuthorHomeRemote) testHelper.getRemoteBean(AuthorHome.class, AuthorHomeRemote.class);
		authorFacadeRemote = (AuthorFacadeRemote) testHelper.getRemoteBean(AuthorFacade.class,
				AuthorFacadeRemote.class);
		bookHomeRemote = (BookHomeRemote) testHelper.getRemoteBean(BookHome.class, BookHomeRemote.class);
		bookFacadeRemote = (BookFacadeRemote) testHelper.getRemoteBean(BookFacade.class, BookFacadeRemote.class);
		reviewHomeRemote = (ReviewHomeRemote) testHelper.getRemoteBean(ReviewHome.class, ReviewHomeRemote.class);
		reviewFacadeRemote = (ReviewFacadeRemote) testHelper.getRemoteBean(ReviewFacade.class,
				ReviewFacadeRemote.class);

	}

	/**
	 * This method runs after all tests. Method close javax.naming.Context
	 * session.
	 */
	@AfterSuite
	public synchronized void closeEJBSession() {
		testHelper.closeSession();

	}

	/**
	 * This method runs before test class. and inserts testing data to database.
	 */
	@BeforeClass
	public static void setUp() {
		dbUnitHelper.insertTestingData();
	}

	/**
	 * This method runs after test class. Method deletes all records from
	 * testing database.
	 */
	@AfterClass
	public static void tearDown() {
		dbUnitHelper.clearTestingDatabase();
	}

}
