package com.softserveinc.model.persist.home;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.testng.annotations.Test;

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.util.BaseTest;
import com.softserveinc.model.util.DataBaseConstants;

public class AuthorHomeTest extends BaseTest implements DataBaseConstants{
	
	public static final String CREATE_AUTHOR = "testCreateAuthor";
	public static final String DELETE_AUTHOR = "testDeleteAuthor";
	
	@Test
	public void testCreateAuthor() {
		Author author = new Author(BLACK, JACK);
		author = authorHomeRemote.create(author);
		String id = author.getIdauthor();
		Author authorExpected = authorHomeRemote.findByID(id);
		assertNotNull(author);
		assertEquals(authorExpected.getIdauthor(), id);
		assertEquals(authorExpected.getFirstName(), author.getFirstName());
		assertEquals(authorExpected.getSecondName(), author.getSecondName());
	}

	@Test
	public void testUpdateAuthor() {
		Author authorBefore = authorHomeRemote.findByID(A2);
		authorBefore.setFirstName(JOHN);
		authorBefore.setSecondName(SMITH);
		authorHomeRemote.update(authorBefore);
		Author authorAfter = authorHomeRemote.findByID(A2);
		assertNotNull(authorBefore);
		assertNotNull(authorAfter);
		assertEquals(JOHN, authorAfter.getFirstName());
		assertEquals(SMITH, authorAfter.getSecondName());
		assertEquals(authorBefore.getCreatedDate(), authorAfter.getCreatedDate());
		assertEquals(authorBefore.getIdauthor(), authorAfter.getIdauthor());
	}
	
//	 @Test(dependsOnMethods = {CREATE_AUTHOR})
//	 public void testDeleteAuthor() {
//	 List<Author> listBefore = authorHomeRemote.findAll();
//	 Author author = authorHomeRemote.findByID("a3");
//	 authorHomeRemote.delete(author);
//	 List<Author> listAfter = authorHomeRemote.findAll();
//	 assertEquals(listBefore.size(), listAfter.size() + 1);
//	 }

	@Test(dependsOnMethods={CREATE_AUTHOR})
	public void testFindByIdAuthor() {
		Author author = authorHomeRemote.findByID(A4);
		assertEquals(A4, author.getIdauthor());
		assertEquals("Greg", author.getFirstName());
		assertEquals("Blou", author.getSecondName());
	}

	@Test(dependsOnMethods={CREATE_AUTHOR})
	public void testFindAllAuthors() {
		List<Author> listAuthors = authorHomeRemote.findAll();
		assertNotNull(listAuthors);
		assertEquals(5, listAuthors.size());
	}

}
