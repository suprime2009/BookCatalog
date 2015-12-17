package com.softserveinc.model.persist.facade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.testng.annotations.Test;

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.util.BaseTest;

public class AuthorFacadeTest extends BaseTest{
	
	@Test
	public void testFindAllAuthorsByBook() {
		Book book = bookFacadeRemote.findById("b1");
		List<Author> list = authorFacadeRemote.findAllAuthorsByBook(book);

		assertNotNull(list);
		assertEquals(list.size(), 3);

		for (Author a : list) {
			if (a.getIdauthor().equals("a1")) {
				assertEquals("Raul", a.getFirstName());
				assertEquals("Garsia", a.getSecondName());
			} else if (a.getIdauthor().equals("a2")) {
				assertEquals("Cristian", a.getFirstName());
				assertEquals("Ronaldo", a.getSecondName());
			} else if (a.getIdauthor().equals("a4")) {
				assertEquals("Greg", a.getFirstName());
				assertEquals("Blou", a.getSecondName());
			}
		}
	}
	
	@Test
	public void testFindAuthorByFullName() {
		Author author = authorFacadeRemote.findAuthorByFullName("Raul", "Garsia");
		
		assertNotNull(author);
		assertEquals("Raul", author.getFirstName());
		assertEquals("Raul", author.getFirstName());
		assertEquals("Garsia", author.getSecondName());
		assertEquals("a1", author.getIdauthor());
	}
	
	

}
