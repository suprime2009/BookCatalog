package com.softserveinc.model.persist.facade;

import static org.junit.Assert.*;
import java.util.List;

import org.testng.annotations.Test;

import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.Review;
import com.softserveinc.model.util.BaseTest;

public class ReviewFacadeTest extends BaseTest {
	
	@Test
	public void testFindAllReviewsByCommenter() {
		List<Review> list = reviewFacadeRemote.findAllReviewsByCommenter("Sasha");
		
		assertNotNull(list);
		assertEquals(2, list.size());
	}
	
	@Test
	public void testFindAverageRatingForBook() {
		Book book = bookFacadeRemote.findById("b1");
		double averageRating = reviewFacadeRemote.findAverageRatingForBook(book);
	
		assertEquals(4.0, averageRating, 0);
	}

}
