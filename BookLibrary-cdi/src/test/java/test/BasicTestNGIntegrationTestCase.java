package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.FormatStage;
import org.jboss.shrinkwrap.resolver.api.ResolveStage;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenFormatStage;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolvedArtifact;
import org.jboss.shrinkwrap.resolver.api.maven.MavenStrategyStage;
import org.jboss.shrinkwrap.resolver.api.maven.PomlessResolveStage;
import org.jboss.shrinkwrap.resolver.api.maven.coordinate.MavenDependency;
import org.jboss.shrinkwrap.resolver.api.maven.filter.MavenResolutionFilter;
import org.jboss.shrinkwrap.resolver.api.maven.strategy.MavenResolutionStrategy;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.base.MoreObjects;
import com.softserveinc.booklibrary.action.util.DataTableSearchHolder;
import com.softserveinc.booklibrary.arquillian.ArquillianDeployerHelper;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.model.entity.Review;
import com.softserveinc.booklibrary.session.persist.facade.BookFacadeLocal;
import com.softserveinc.booklibrary.session.persist.facade.IBookFacade;
import com.softserveinc.booklibrary.session.persist.facade.impl.BookFacade;
import com.softserveinc.booklibrary.session.persist.home.AuthorHomeLocal;
import com.softserveinc.booklibrary.session.persist.home.AuthorHomeRemote;
import com.softserveinc.booklibrary.session.persist.home.BookHomeLocal;
import com.softserveinc.booklibrary.session.persist.home.BookHomeRemote;
import com.softserveinc.booklibrary.session.persist.home.IHome;
import com.softserveinc.booklibrary.session.persist.home.ReviewHomeLocal;
import com.softserveinc.booklibrary.session.persist.home.ReviewHomeRemote;
import com.softserveinc.booklibrary.session.persist.home.impl.AuthorHome;
import com.softserveinc.booklibrary.session.persist.home.impl.BookHome;
import com.softserveinc.booklibrary.session.persist.home.impl.ReviewHome;
import com.softserveinc.booklibrary.session.util.ConnectionFilter;
import com.softserveinc.booklibrary.session.util.QueryBuilderForDataTable;
import com.softserveinc.booklibrary.session.util.SQLCommandConstants;
import com.softserveinc.model.util.BaseTest;
import com.softserveinc.model.util.DBUnitHelper;
import com.softserveinc.model.util.DataBaseConstants;

import org.dbunit.ext.mysql.MySqlDataTypeFactory;

/**
 * Ensures that the basic startup/deployment etc facilities of the Arquillian
 * container are working with TestNG w/ AS7. AS7-1303
 * 
 * @author <a href="mailto:alr@jboss.org">Andrew Lee Rubinger</a>
 */
public class BasicTestNGIntegrationTestCase extends ArquillianDeployerHelper {

	private static final String SOURCES_DIR = "src/main/java";



	@Inject
	private BookFacadeLocal bookFacadeLocal;

	@Inject
	private static BaseTest dbUnitHelper;

	@Test
	@UsingDataSet("testingData.xml")
	public void testFindBookByISNBN() {
		Book book = bookFacadeLocal.findBookByISNBN("111-111-111-1");
		assertNotNull(book);
		assertEquals("111-111-111-1", book.getIsbn());
		assertEquals("Java Core", book.getBookName());
		assertEquals(new Integer(2010), book.getYearPublished());
		assertEquals("England", book.getPublisher());
		assertEquals("b1", book.getIdBook());
	}

	@Test
	@UsingDataSet("testingData.xml")
	public void testFindBookByName() {
		List<Book> list = bookFacadeLocal.findBookByName("Java Core");

		assertNotNull(list);
		assertEquals(list.size(), 2);
	}

	@Test
	@UsingDataSet("testingData.xml")
	public void testFindCountBooks() {
		int count = bookFacadeLocal.findCountAllBooks();
		assertEquals(7, count);
	}

}
