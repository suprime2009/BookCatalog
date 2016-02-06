package com.softserveinc.booklibrary.arquillian;

import java.io.File;
import java.io.IOException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.testng.annotations.Test;

import com.softserveinc.booklibrary.action.util.DataTableSearchHolder;
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
import com.softserveinc.booklibrary.rest.util.AuthorRestDTOConverter;
import com.softserveinc.booklibrary.rest.util.JsonFieldsHolder;
import com.softserveinc.booklibrary.session.manager.AuthorManagerLocal;
import com.softserveinc.booklibrary.session.manager.BookManagerLocal;
import com.softserveinc.booklibrary.session.manager.ReviewManagerLocal;
import com.softserveinc.booklibrary.session.manager.impl.AuthorManager;
import com.softserveinc.booklibrary.session.manager.impl.BookManager;
import com.softserveinc.booklibrary.session.manager.impl.ReviewManager;
import com.softserveinc.booklibrary.session.persist.facade.BookFacadeLocal;
import com.softserveinc.booklibrary.session.persist.facade.IBookFacade;
import com.softserveinc.booklibrary.session.persist.facade.impl.BookFacade;
import com.softserveinc.booklibrary.session.persist.home.ReviewHomeLocal;
import com.softserveinc.booklibrary.session.persist.home.impl.AuthorHome;
import com.softserveinc.booklibrary.session.persist.home.impl.BookHome;
import com.softserveinc.booklibrary.session.persist.home.impl.ReviewHome;
import com.softserveinc.booklibrary.session.util.QueryBuilderForDataTable;
import com.softserveinc.booklibrary.session.util.SQLCommandConstants;
import com.softserveinc.booklibrary.test.manager.ManagerTestUlil;
import com.softserveinc.booklibrary.test.rest.AuthorRestTest;
import com.softserveinc.model.util.DBUnitHelper;
import com.softserveinc.model.util.DataBaseConstants;


public class ArquillianDeployerHelper extends Arquillian {

	
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
		war.addPackages(true, ArquillianDeployerHelper.class.getPackage());
		war.addPackages(true, AuthorManager.class.getPackage());
		war.addPackages(true, AuthorManagerLocal.class.getPackage());
		war.addPackages(true, AuthorManagerException.class.getPackage());

		war.addPackages(true, ReviewManager.class.getPackage());
		war.addPackages(true, ReviewManagerLocal.class.getPackage());
		war.addPackages(true, ReviewManagerException.class.getPackage());
		war.addPackages(true, BookManager.class.getPackage());
		war.addPackages(true, BookManagerLocal.class.getPackage());
		war.addPackages(true, BookManagerException.class.getPackage());
		
		war.addPackages(true, ManagerTestUlil.class.getPackage());
		
		war.addPackages(true, AuthorDTO.class.getPackage());
		war.addPackages(true, AuthorRestDTOConverter.class.getPackage());
		war.addPackages(true, AuthorClientImpl.class.getPackage());
		war.addPackages(true, AuthorClient.class.getPackage());
		war.addPackages(true, AuthorService.class.getPackage());
		war.addPackages(true, AuthorServiceImpl.class.getPackage());
		war.addPackages(true, BookCatalogException.class.getPackage());
		war.addPackages(true, JsonFieldsHolder.class.getPackage());
		war.addPackages(true, AuthorRestTest.class.getPackage());

		war.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
		war.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

		System.out.println("I am deployed");
		for (int i = 0; i < 20; i++) {
			System.out.println("I am deployed");
		}

		return war;
	}
	


}
