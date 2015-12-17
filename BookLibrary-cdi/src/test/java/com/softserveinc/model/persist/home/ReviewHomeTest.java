package com.softserveinc.model.persist.home;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.testng.annotations.Test;

import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.Review;
import com.softserveinc.model.util.BaseTest;

public class ReviewHomeTest extends BaseTest{
	
	@Test(groups= "ReviewHome")
	public void testCreateBook() {
		Book book = bookHomeRemote.findByID("b4");
		Review review = new Review("This book wonderfull!", "Fred12", 5, book);
		review = reviewHomeRemote.create(review);

		assertNotNull(review);
		assertEquals(book, review.getBook());
		assertEquals("This book wonderfull!", review.getComment());
		assertEquals("Fred12", review.getCommenterName());
		assertEquals(5, review.getRating());
		
		review = new Review("I am agree with Fred!!", "Sara", 4, book);
		review = reviewHomeRemote.create(review);
		assertNotNull(review);
		assertEquals(book, review.getBook());
		assertEquals("I am agree with Fred!!", review.getComment());
		assertEquals("Sara", review.getCommenterName());
		assertEquals(4, review.getRating());
	}
	
	@Test(groups= "ReviewHome")
	public void testUpdateReview() {
		
		Review reviewBefore = reviewHomeRemote.findByID("r7");
		Book otherBook = bookHomeRemote.findByID("b1");
		reviewBefore.setBook(otherBook);
		reviewBefore.setComment("Other Comment");
		reviewBefore.setCommenterName("Unknown");
		reviewBefore.setRating(1);
		
		reviewHomeRemote.update(reviewBefore);
		Review reviewAfter = reviewHomeRemote.findByID("r7");
		
		assertNotNull(reviewBefore);
		assertNotNull(reviewAfter);
		assertEquals("Other Comment", reviewAfter.getComment());
		assertEquals("Unknown", reviewAfter.getCommenterName());
		assertEquals(1, reviewAfter.getRating());
		assertEquals(otherBook, reviewAfter.getBook());
		assertEquals(reviewBefore.getCreatedDate(), reviewAfter.getCreatedDate());
		assertEquals(reviewBefore.getIdreview(), reviewAfter.getIdreview());
	}

	@Test(expectedExceptions=NullPointerException.class, dependsOnGroups = "ReviewHome")
	public void testFindByIdBook() {
		Review review = reviewHomeRemote.findByID("r4");
		Book book = bookHomeRemote.findByID("b3");
		
		assertEquals("Lesia", review.getCommenterName());
		assertEquals("JPA", review.getComment());
		assertEquals(4, review.getRating());
		assertEquals(book, review.getBook());
		
		review = reviewHomeRemote.findByID("r10");
		review.getComment();
	}
	
//	@Test(groups= "ReviewHome")
//	public void testDeleteReview() {
//		List<Review> listBefore = reviewHomeRemote.findAll();
//		Review review = reviewHomeRemote.findByID("r2");
//		reviewHomeRemote.delete(review);
//		List<Review> listAfter = reviewHomeRemote.findAll();
//		assertEquals(listBefore.size(), listAfter.size() + 1);
//	}
	
	@Test(dependsOnGroups = "ReviewHome")
	public void testFindAllReviews() {
		List<Review> listReview = reviewHomeRemote.findAll();
		assertNotNull(listReview);
		assertEquals(8, listReview.size());
	}

}
