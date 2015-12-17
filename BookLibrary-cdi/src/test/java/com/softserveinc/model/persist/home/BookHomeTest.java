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

public class BookHomeTest extends BaseTest{
	
	@Test(groups= "bookHome")
	public void testCreateBook() {
		Book book = new Book("Windows", "111-111-112-1", "Japan", 2015, null);
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
	
	@Test(groups= "bookHome")
	public void testCreateBookWithAuthors() {
		Author author1 = authorHomeRemote.findByID("a1");
		Author author2 = authorHomeRemote.findByID("a4");
		Set<Author> set = new HashSet<Author>();
		set.add(author1);
		set.add(author2);
		
		Book book = new Book("Windows 8.1 New", "111-111-122-2", "Japan", 2015, set);
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
	
	@Test(groups= "bookHome")
	public void testUpdateBook() {
		Author author1 = authorHomeRemote.findByID("a1");
		Author author2 = authorHomeRemote.findByID("a4");
		Set<Author> set = new HashSet<Author>();
		set.add(author1);
		set.add(author2);
		
		Book bookBefore = bookHomeRemote.findByID("b5");
		bookBefore.setBookName("New Name");
		bookBefore.setIsbn("777-777-777-7");
		bookBefore.setPublisher("publisher");
		bookBefore.setYearPublished(1990);
		bookBefore.setBookAuthors(set);

		bookHomeRemote.update(bookBefore);
		Book bookAfter = bookHomeRemote.findByID("b5");
		assertNotNull(bookAfter);
		assertNotNull(bookBefore);
		assertEquals("New Name", bookAfter.getBookName());
		assertEquals("777-777-777-7", bookAfter.getIsbn());
		assertEquals("publisher", bookAfter.getPublisher());
		assertEquals(set, bookAfter.getBookAuthors());
		assertEquals(new Integer(1990), bookAfter.getYearPublished());
		assertEquals(bookBefore.getCreatedDate(), bookAfter.getCreatedDate());
		assertEquals(bookBefore.getIdbook(), bookAfter.getIdbook());

	}

	@Test(expectedExceptions=NullPointerException.class, dependsOnGroups = "bookHome")
	public void testFindByIdBook() {
		Author author1 = authorHomeRemote.findByID("a1");
		Author author2 = authorHomeRemote.findByID("a4");
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
	
	@Test(dependsOnGroups = "bookHome")
	public void testFindAllBooks() {
		List<Book> listBook = bookHomeRemote.findAll();
		assertNotNull(listBook);
		assertEquals(8, listBook.size());
	}

}
