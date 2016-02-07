package com.softserveinc.model.persist.home;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.model.entity.Review;
import com.softserveinc.model.util.BaseTest;
import com.softserveinc.model.util.DataBaseConstants;

public class BookHomeTest extends BaseTest implements DataBaseConstants{ 
	
	public static final String CREATE_BOOK = "testCreateBook";
	public static final String CREATE_BOOK_WITH_AUTHORS = "testCreateBookWithAuthors";
	public static final String DELETE_BOOK = "testDeleteBook";
	

//	@Test
//	public void testCreateBook() {
//		Book book = new Book(WINDOWS, ISBN, JAPAN, 2015, null);
//		assertNull(book.getIdBook());
//		bookHomeRemote.create(book);
//		System.out.println(book);
//		System.out.println(book);
//		System.out.println(book);
//		System.out.println(book);
//		System.out.println(book);
//		String id = book.getIdBook();
//		assertNotNull(id);
//		Book bookExpected = bookHomeRemote.findByID(id);
//		assertNotNull(bookExpected);
//		assertEquals(bookExpected.getIdBook(), id);
//		assertEquals(bookExpected.getBookName(), book.getBookName());
//		assertEquals(bookExpected.getIsbn(), book.getIsbn());
//		assertEquals(bookExpected.getPublisher(), book.getPublisher());
//		assertEquals(bookExpected.getYearPublished(), book.getYearPublished());
//	}
//	
//	@Test(expectedExceptions=Exception.class)
//	public void testCreateBookWithAlreadyExistISBN() {
//		Book book = new Book(WINDOWS, EXISTING_ISBN, JAPAN, 2015, null);
//		bookHomeRemote.create(book);
//		
//		assertEquals(book.getIdBook(), null);
//	}
//	
//	@Test
//	public void testCreateBookWithAuthors() {
//		Author author1 = authorHomeRemote.findByID(A1);
//		Author author2 = authorHomeRemote.findByID(A9);
//		Set<Author> set = new HashSet<Author>();
//		set.add(author1);
//		set.add(author2);
//		
//		Book book = new Book("Windows 8.1 New", "111-122-32-2", JAPAN, 2015, set);
//		bookHomeRemote.create(book);
//		String id = book.getIdBook();
//		Book bookExpected = bookHomeRemote.findByID(id);
//		
//		assertNotNull(bookExpected);
//		assertEquals(set.size(), 2);
//		assertEquals(bookExpected.getIdBook(), id);
//		assertEquals(bookExpected.getBookName(), book.getBookName());
//		assertEquals(bookExpected.getIsbn(), book.getIsbn());
//		assertEquals(bookExpected.getPublisher(), book.getPublisher());
//		assertEquals(bookExpected.getYearPublished(), book.getYearPublished());
//		assertEquals(bookExpected.getAuthors(), book.getAuthors());
//	}
//	
//	@Test
//	public void testUpdateBook() {
//		Author author1 = authorHomeRemote.findByID(A1);
//		Author author2 = authorHomeRemote.findByID(A4);
//		Set<Author> setBefore = new HashSet<Author>();
//		setBefore.add(author1);
//		setBefore.add(author2);
//		
//		Book bookBefore = bookHomeRemote.findByID(B5);
//		Set<Author> setAfter = new HashSet<Author>();
//		Author author3 = authorHomeRemote.findByID(A5);
//		Author author4 = authorHomeRemote.findByID(A6);
//		Author author5 = authorHomeRemote.findByID(A7);
//		Author author6 = authorHomeRemote.findByID(A2);
//		setAfter.add(author3);
//		setAfter.add(author4);
//		setAfter.add(author5);
//		setAfter.add(author6);
//		bookBefore.setBookName(NEW_NEW);
//		bookBefore.setIsbn(SEVEN);
//		bookBefore.setPublisher(PUBLISHER);
//		bookBefore.setYearPublished(1990);
//		bookBefore.setAuthors(setAfter);
//
//		bookHomeRemote.update(bookBefore);
//		Book bookAfter = bookHomeRemote.findByID(B5);
//		assertNotNull(bookAfter);
//		assertNotNull(bookBefore);
//		assertEquals(4, bookAfter.getAuthors().size());
//		assertTrue(bookAfter.getAuthors().contains(author3));
//		assertTrue(bookAfter.getAuthors().contains(author4));
//		assertTrue(bookAfter.getAuthors().contains(author5));
//		assertTrue(bookAfter.getAuthors().contains(author6));
//		assertFalse(bookAfter.getAuthors().contains(author1));
//		assertFalse(bookAfter.getAuthors().contains(author2));
//		assertEquals(NEW_NEW, bookAfter.getBookName());
//		assertEquals(SEVEN, bookAfter.getIsbn());
//		assertEquals(PUBLISHER, bookAfter.getPublisher());
//
//		assertEquals(new Integer(1990), bookAfter.getYearPublished());
//		assertEquals(bookBefore.getCreatedDate(), bookAfter.getCreatedDate());
//		assertEquals(bookBefore.getIdBook(), bookAfter.getIdBook());
//
//	}
//
//
//	@Test(expectedExceptions=NullPointerException.class, 
//			dependsOnMethods= {CREATE_BOOK_WITH_AUTHORS, CREATE_BOOK, DELETE_BOOK})
//	public void testFindByIdBook() {
//		Author author1 = authorHomeRemote.findByID(A1);
//		Author author2 = authorHomeRemote.findByID(A4);
//		Set<Author> set = new HashSet<Author>();
//		set.add(author1);
//		set.add(author2);
//		
//		Book book = bookHomeRemote.findByID(B2);
//		assertEquals("Hibernate", book.getBookName());
//		assertEquals("111-11-111-2", book.getIsbn());
//		assertEquals("USA", book.getPublisher());
//		assertEquals(set, book.getAuthors());
//		assertEquals(new Integer(2009), book.getYearPublished());
//		
//		book = bookHomeRemote.findByID("b900");
//		book.getBookName();
//	}
//	
//	@Test(dependsOnMethods= {CREATE_BOOK_WITH_AUTHORS, CREATE_BOOK})
//	public void testDeleteBook() {
//		List<Book> listBefore = bookHomeRemote.findAll();
//		List<Review> listReviewBefore = reviewHomeRemote.findAll();
//		List<Author> listAuthorBefore = authorFacadeRemote.findAll();
//		Book book = bookHomeRemote.findByID(B36);
//		bookHomeRemote.delete(book);
//		List<Author> listAuthorAfter = authorFacadeRemote.findAll();
//		List<Book> listAfter = bookHomeRemote.findAll();
//		List<Review> listReviewAfter = reviewHomeRemote.findAll();
//		assertEquals(listAuthorAfter.size(), listAuthorBefore.size());
//		assertEquals(144, listReviewBefore.size());
//		assertNotNull(book.getAuthors());
//		assertEquals(137, listReviewAfter.size());
//		assertEquals(listBefore.size(), listAfter.size() + 1);
//	}
//	
//	@Test(dependsOnMethods= {CREATE_BOOK_WITH_AUTHORS, CREATE_BOOK, DELETE_BOOK})
//	public void testFindAllBooks() {
//		List<Book> listBook = bookHomeRemote.findAll();
//		assertNotNull(listBook);
//		assertEquals(57, listBook.size());
//	}

}
