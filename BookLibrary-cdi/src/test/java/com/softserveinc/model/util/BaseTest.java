package com.softserveinc.model.util;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.softserveinc.model.persist.facade.AuthorFacade;
import com.softserveinc.model.persist.facade.AuthorFacadeRemote;
import com.softserveinc.model.persist.facade.BookFacade;
import com.softserveinc.model.persist.facade.BookFacadeRemote;
import com.softserveinc.model.persist.facade.ReviewFacade;
import com.softserveinc.model.persist.facade.ReviewFacadeRemote;
import com.softserveinc.model.persist.home.AuthorHome;
import com.softserveinc.model.persist.home.AuthorHomeRemote;
import com.softserveinc.model.persist.home.BookHome;
import com.softserveinc.model.persist.home.BookHomeRemote;
import com.softserveinc.model.persist.home.ReviewHome;
import com.softserveinc.model.persist.home.ReviewHomeRemote;
import com.softserveinc.rest.client.AuthorClient;
import com.softserveinc.rest.client.AuthorClientRemote;

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
	protected static AuthorClientRemote authorClientRemote;
	protected static TestHelper testHelper;
	protected static DBUnitHelper dbUnitHelper = DBUnitHelper.getInstance();

	/**
	 * This method runs before all tests. Method gets TestHelper instance,
	 * creates instances of remote beans.
	 */
	@BeforeSuite
	public void getRemoteBeans() {
		testHelper = TestHelper.getInstance();
		authorHomeRemote = (AuthorHomeRemote) testHelper.getRemoteBean(AuthorHome.class);
		authorFacadeRemote = (AuthorFacadeRemote) testHelper.getRemoteBean(AuthorFacade.class);
		bookHomeRemote = (BookHomeRemote) testHelper.getRemoteBean(BookHome.class);
		bookFacadeRemote = (BookFacadeRemote) testHelper.getRemoteBean(BookFacade.class);
		reviewHomeRemote = (ReviewHomeRemote) testHelper.getRemoteBean(ReviewHome.class);
		reviewFacadeRemote = (ReviewFacadeRemote) testHelper.getRemoteBean(ReviewFacade.class);
		authorClientRemote = (AuthorClientRemote) testHelper.getRemoteBean(AuthorClient.class);
	}

	/**
	 * This method runs after all tests. Method close javax.naming.Context
	 * session.
	 */
	@AfterSuite
	public void closeEJBSession() {
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
