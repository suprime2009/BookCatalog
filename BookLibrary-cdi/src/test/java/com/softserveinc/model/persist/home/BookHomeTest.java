package com.softserveinc.model.persist.home;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.util.BaseTest;
import com.softserveinc.model.util.DataBaseConstants;

public class BookHomeTest extends BaseTest implements DataBaseConstants{ 
	
	public static final String CREATE_BOOK = "testCreateBook";
	public static final String CREATE_BOOK_WITH_AUTHORS = "testCreateBookWithAuthors";
	

	@Test
	public void testCreateBook() {
		Book book = new Book(WINDOWS, ISBN, JAPAN, 2015, null);
		book = bookHomeRemote.create(book);
		String id = book.getIdbook();
		Book bookExpected = bookHomeRemote.findByID(id);
		assertNotNull(bookExpected);
		assertEquals(bookExpected.getIdbook(), id);
		assertEquals(bookExpected.getBookName(), book.getBookName());
		assertEquals(bookExpected.getIsbn(), book.getIsbn());
		assertEquals(bookExpected.getPublisher(), book.getPublisher());
		assertEquals(bookExpected.getYearPublished(), book.getYearPublished());
	}
	
	@Test
	public void testCreateBookWithAuthors() {
		Author author1 = authorHomeRemote.findByID(A1);
		Author author2 = authorHomeRemote.findByID(A4);
		Set<Author> set = new HashSet<Author>();
		set.add(author1);
		set.add(author2);
		
		Book book = new Book("Windows 8.1 New", "111-111-122-2", JAPAN, 2015, set);
		book = bookHomeRemote.create(book);
		String id = book.getIdbook();
		Book bookExpected = bookHomeRemote.findByID(id);
		
		assertNotNull(bookExpected);
		assertEquals(bookExpected.getIdbook(), id);
		assertEquals(bookExpected.getBookName(), book.getBookName());
		assertEquals(bookExpected.getIsbn(), book.getIsbn());
		assertEquals(bookExpected.getPublisher(), book.getPublisher());
		assertEquals(bookExpected.getYearPublished(), book.getYearPublished());
		assertEquals(bookExpected.getBookAuthors(), book.getBookAuthors());
	}
	
//	@Test(expected=Exception.class)(groups= "bookHome")
//	public void testNegativeCreateBook() {
//		Author author1 = authorHomeRemote.findByID("a1");
//		Author author2 = authorHomeRemote.findByID("a4");
//		Set<Author> set = new HashSet<Author>();
//		set.add(author1);
//		set.add(author2);
//		
//		Book book = new Book("Windows 8.1 New", "111-111-111-2", "Japan", 2015, set);
//		book = bookHomeRemote.create(book);
//	}
	
	@Test
	public void testUpdateBook() {
		Author author1 = authorHomeRemote.findByID(A1);
		Author author2 = authorHomeRemote.findByID(A4);
		Set<Author> set = new HashSet<Author>();
		set.add(author1);
		set.add(author2);
		
		Book bookBefore = bookHomeRemote.findByID(B5);
		bookBefore.setBookName(NEW_NEW);
		bookBefore.setIsbn(SEVEN);
		bookBefore.setPublisher(PUBLISHER);
		bookBefore.setYearPublished(1990);
		bookBefore.setBookAuthors(set);

		bookHomeRemote.update(bookBefore);
		Book bookAfter = bookHomeRemote.findByID(B5);
		assertNotNull(bookAfter);
		assertNotNull(bookBefore);
		assertEquals(NEW_NEW, bookAfter.getBookName());
		assertEquals(SEVEN, bookAfter.getIsbn());
		assertEquals(PUBLISHER, bookAfter.getPublisher());
		assertEquals(set, bookAfter.getBookAuthors());
		assertEquals(new Integer(1990), bookAfter.getYearPublished());
		assertEquals(bookBefore.getCreatedDate(), bookAfter.getCreatedDate());
		assertEquals(bookBefore.getIdbook(), bookAfter.getIdbook());

	}

	@Test(expectedExceptions=NullPointerException.class, 
			dependsOnMethods= {CREATE_BOOK_WITH_AUTHORS, CREATE_BOOK})
	public void testFindByIdBook() {
		Author author1 = authorHomeRemote.findByID(A1);
		Author author2 = authorHomeRemote.findByID(A4);
		Set<Author> set = new HashSet<Author>();
		set.add(author1);
		set.add(author2);
		
		Book book = bookHomeRemote.findByID("b2");
		assertEquals("Hibernate", book.getBookName());
		assertEquals("111-111-111-2", book.getIsbn());
		assertEquals("USA", book.getPublisher());
		assertEquals(set, book.getBookAuthors());
		assertEquals(new Integer(2009), book.getYearPublished());
		
		book = bookHomeRemote.findByID("b9");
		book.getBookName();
	}
	
//	@Test(groups= "bookHome")
//	public void testDeleteAuthor() {
//		List<Book> listBefore = bookHomeRemote.findAll();
//		Book book = bookHomeRemote.findByID("b6");
//		bookHomeRemote.delete(book);
//		List<Book> listAfter = bookHomeRemote.findAll();
//		assertEquals(listBefore.size(), listAfter.size() + 1);
//	}
	
	@Test(dependsOnMethods= {CREATE_BOOK_WITH_AUTHORS, CREATE_BOOK})
	public void testFindAllBooks() {
		List<Book> listBook = bookHomeRemote.findAll();
		assertNotNull(listBook);
		assertEquals(9, listBook.size());
	}

}
