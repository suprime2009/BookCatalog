package com.softserveinc.model.persist.facade;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.testng.annotations.Test;

import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.model.util.BaseTest;

public class BookFacadeTest extends BaseTest {

	@Test
	public void testFindBooksByAuthor() {
		Author author = authorFacadeRemote.findById("a1");
		List<Book> list = bookFacadeRemote.findBooksByAuthor(author);

		Book book1 = bookFacadeRemote.findById("b1");
		Book book2 = bookFacadeRemote.findById("b2");
		Book book3 = bookFacadeRemote.findById("b3");
		Book book4 = bookFacadeRemote.findById("b5");
		Book book5 = bookFacadeRemote.findById("b8");
		Book book6 = bookFacadeRemote.findById("b21");
		Book book7 = bookFacadeRemote.findById("b43");
		Book book8 = bookFacadeRemote.findById("b45");

		assertNotNull(list);
		assertEquals(8, list.size());
		assertTrue(list.contains(book1));
		assertTrue(list.contains(book2));
		assertTrue(list.contains(book3));
		assertTrue(list.contains(book4));
		assertTrue(list.contains(book5));
		assertTrue(list.contains(book6));
		assertTrue(list.contains(book7));
		assertTrue(list.contains(book8));

		Author authorWithNoBooks = authorFacadeRemote.findById("a10");
		list = bookFacadeRemote.findBooksByAuthor(authorWithNoBooks);

		assertNotNull(list);
		assertTrue(list.isEmpty());
	}

	@Test
	public void testFindBookByISNBN() {
		Book book = bookFacadeRemote.findBookByISNBN("111-11-111-1");
		assertNotNull(book);
		assertEquals("111-11-111-1", book.getIsbn());
		assertEquals("Java Core", book.getBookName());
		assertEquals(new Integer(2010), book.getYearPublished());
		assertEquals("England", book.getPublisher());
		assertEquals("b1", book.getIdBook());
	}
	
	@Test
	public void testFindBookByName() {
		List<Book> list = bookFacadeRemote.findBookByName("Java Core");
		
		assertNotNull(list);
		assertEquals(list.size(), 2);
	}
	
	@Test
	public void testFindCountBooks() {
		int count = bookFacadeRemote.findCountAllBooks();
		assertEquals(56, count);
		
	}
	
	
	

}
