package com.softserveinc.model.persist.facade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.testng.annotations.Test;

import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.model.entity.Review;
import com.softserveinc.model.util.BaseTest;

public class ReviewFacadeTest extends BaseTest {
	
	@Test
	public void testFindAllReviewsByCommenter() {
		List<Review> list = reviewFacadeRemote.findAllReviewsByCommenter("Sasha");
		
		assertNotNull(list);
		assertEquals(32, list.size());
	}
	
	@Test
	public void testFindAverageRatingForBook() {
		Book book = bookFacadeRemote.findById("b1");
		double averageRating = reviewFacadeRemote.findAverageRatingForBook(book);
	
		assertEquals(3.67, averageRating, 0);
	}

}
