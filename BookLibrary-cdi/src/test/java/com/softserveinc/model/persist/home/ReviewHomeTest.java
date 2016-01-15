package com.softserveinc.model.persist.home;

import static org.junit.Assert.*;


import java.util.List;

import org.hibernate.jpa.boot.archive.spi.ArchiveException;
import org.testng.annotations.Test;

import com.softserveinc.exception.BookCatalogException;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.Review;
import com.softserveinc.model.util.BaseTest;
import com.softserveinc.model.util.DataBaseConstants;

public class ReviewHomeTest extends BaseTest implements DataBaseConstants{
	
	public static final String CREATE_REVIEW = "testCreateReview";
	
	@Test
	public void testCreateReview() {
		List<Review> listBefore = reviewHomeRemote.findAll();
		Book book = bookHomeRemote.findByID("b4");
		Review review = new Review("This book wonderfull!", "Fred12", 5, book);
		review = reviewHomeRemote.create(review);
		List<Review> listAfter = reviewHomeRemote.findAll();

		assertNotNull(review);
		assertEquals(book, review.getBook());
		assertEquals("This book wonderfull!", review.getComment());
		assertEquals("Fred12", review.getCommenterName());
		assertEquals(Integer.valueOf(5), review.getRating());
		assertEquals(listBefore.size() + 1, listAfter.size());

		
		review = new Review("I am agree with Fred!!", "Sara", 4, book);
		review = reviewHomeRemote.create(review);
		listAfter = reviewHomeRemote.findAll();
		assertNotNull(review);
		assertEquals(book, review.getBook());
		assertEquals("I am agree with Fred!!", review.getComment());
		assertEquals("Sara", review.getCommenterName());
		assertEquals(Integer.valueOf(4), review.getRating());
		assertEquals(listBefore.size() + 2, listAfter.size());
	}
	
//	@Test(expectedExceptions=BookCatalogException.class)
//	public void negativeCreateCommenterName() {
//		List<Review> listBefore = reviewHomeRemote.findAll();
//		Book book = bookHomeRemote.findByID("b4");
//		Review review = new Review("This book wonderfull!", null, 5, book);
//		review = reviewHomeRemote.create(review);
//	}
	
	@Test
	public void testUpdateReview() {
		
//		Review reviewBefore = reviewHomeRemote.findByID("r7");
//		Book otherBook = bookHomeRemote.findByID("b1");
//		reviewBefore.setBook(otherBook);
//		reviewBefore.setComment("Other Comment");
//		reviewBefore.setCommenterName("Unknown");
//		reviewBefore.setRating(1);
//		
//		reviewHomeRemote.update(reviewBefore);
//		Review reviewAfter = reviewHomeRemote.findByID("r7");
//		
//		assertNotNull(reviewBefore);
//		assertNotNull(reviewAfter);
//		assertEquals("Other Comment", reviewAfter.getComment());
//		assertEquals("Unknown", reviewAfter.getCommenterName());
//		assertEquals(1, reviewAfter.getRating());
//		assertEquals(otherBook, reviewAfter.getBook());
//		assertEquals(reviewBefore.getCreatedDate(), reviewAfter.getCreatedDate());
//		assertEquals(reviewBefore.getIdreview(), reviewAfter.getIdreview());
	}

	@Test
	public void testFindByIdreview() {
		Review review = reviewHomeRemote.findByID("r4");
		Book book = bookHomeRemote.findByID("b3");
		
		assertEquals("Lesia", review.getCommenterName());
		assertEquals("JPA", review.getComment());
		assertEquals(Integer.valueOf(4), review.getRating());
		assertEquals(book, review.getBook());

		
		review = reviewHomeRemote.findByID("r10");
		review.getComment();
	}
	
	@Test(expectedExceptions=IllegalArgumentException.class)
	public void testNegativeFindByIdReview() {
		Review review = reviewHomeRemote.findByID("2");
	}
	
	@Test
	public void testDeleteReview() {
		List<Review> listBefore = reviewHomeRemote.findAll();
		Review review = reviewHomeRemote.findByID("r2");
		reviewHomeRemote.delete(review);
		List<Review> listAfter = reviewHomeRemote.findAll();
		assertEquals(listBefore.size(), listAfter.size() + 1);
	}
	
	@Test(dependsOnMethods={CREATE_REVIEW})
	public void testFindAllReviews() {
//		List<Review> listReview = reviewHomeRemote.findAll();
//		System.out.println(listReview.size());
//		System.out.println(listReview.size());
//		System.out.println(listReview.size());
//		System.out.println(listReview.size());
//		assertNotNull(listReview);
//		assertEquals(11, listReview.size());
	}

}
