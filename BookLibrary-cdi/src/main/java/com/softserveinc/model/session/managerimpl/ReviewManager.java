package com.softserveinc.model.session.managerimpl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.exception.BookCatalogException;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.Review;
import com.softserveinc.model.persist.facade.ReviewFacadeLocal;
import com.softserveinc.model.persist.home.BookHome;
import com.softserveinc.model.persist.home.ReviewHomeLocal;
import com.softserveinc.model.session.manager.ReviewManagerLocal;
import com.softserveinc.model.session.manager.ReviewManagerRemote;

@Stateless
public class ReviewManager implements ReviewManagerLocal, ReviewManagerRemote{
	
	private static Logger log = LoggerFactory.getLogger(ReviewManager.class);


	@EJB
	private ReviewFacadeLocal reviewFacade;
	
	@EJB
	private ReviewHomeLocal reviewHome;
	
	@Override
	public double getAverageRating(Book book) {
		double result = reviewFacade.findAverageRatingForBook(book);
		return result;
	}

	@Override
	public boolean createReview(Review review) {
		log.debug("Method createReview starts. Review to create ={}", review.toString());
		String errorMessage = "";
		try {
		if (review.getCommenterName() == null) {
			errorMessage = "Commenter name can't be null.";
			throw new BookCatalogException(errorMessage);
		}
		if (review.getComment() == null) {
			errorMessage = "Comment can't be null.";
			throw new BookCatalogException(errorMessage);
		}
		if (review.getRating() != null) {
			if (review.getRating() < 1 || review.getRating() > 5) {
				errorMessage = "Rating should be in diapason between 1 and 5.";
				throw new BookCatalogException(errorMessage);
			}
		}
		reviewHome.create(review);
		if (review.getIdreview() != null) {
			return true;
		}
		} catch (BookCatalogException e) {
			log.error("Review has not been created. {}", e.getMessage());
		}
		return false;
		
	}

	@Override
	public boolean deleteReview(String idReview) {
		Review review = reviewFacade.findById(idReview);
		reviewHome.delete(review);
		review = null;
		review = reviewFacade.findById(idReview);
		if (review == null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateReview(Review review) {
		log.debug("Method updateReview starts. Review to update ={}", review.toString());
		String errorMessage = "";
		try {
		if (review.getCommenterName() == null) {
			errorMessage = "Commenter name can't be null.";
			throw new BookCatalogException(errorMessage);
		}
		if (review.getComment() == null) {
			errorMessage = "Comment can't be null.";
			throw new BookCatalogException(errorMessage);
		}
		if (review.getRating() != null) {
			if (review.getRating() < 1 || review.getRating() > 5) {
				errorMessage = "Rating should be in diapason between 1 and 5.";
				throw new BookCatalogException(errorMessage);
			}
		}
		reviewHome.update(review);
		
		}catch(BookCatalogException e){
			log.error("Can't update this review. {}", e.getMessage());
		}
		return false;
	}

}
