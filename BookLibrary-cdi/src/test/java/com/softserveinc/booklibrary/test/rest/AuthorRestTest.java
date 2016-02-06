package com.softserveinc.booklibrary.test.rest;

import static org.junit.Assert.*;

import javax.ejb.EJB;

import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupStrategy;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.testng.annotations.Test;

import com.softserveinc.booklibrary.arquillian.ArquillianDeployerHelper;
import com.softserveinc.booklibrary.exception.BookCatalogException;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.rest.client.AuthorClientImpl;
import com.softserveinc.booklibrary.rest.dto.AuthorDTO;
import com.softserveinc.booklibrary.session.persist.facade.AuthorFacadeLocal;

public class AuthorRestTest extends ArquillianDeployerHelper {

	@EJB
	private AuthorClientImpl authorClient;

	@EJB
	private AuthorFacadeLocal authorFacade;

	@Test
	@UsingDataSet("dataset/datasetForAuthorManager.xml")
	@Cleanup(phase = TestExecutionPhase.AFTER, strategy = CleanupStrategy.USED_ROWS_ONLY)
	public void testFindById() throws BookCatalogException {

		Author author = authorFacade.findById("a3");

		AuthorDTO authorDTO = authorClient.findById("a3");
		assertEquals(author.getIdAuthor(), authorDTO.getIdAuthor());
		assertEquals(author.getFirstName(), authorDTO.getFirstName());
		assertEquals(author.getSecondName(), authorDTO.getSecondName());

	}

}
