package com.softserveinc.booklibrary.session.manager.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.booklibrary.exception.ReviewManagerException;
import com.softserveinc.booklibrary.model.entity.Review;
import com.softserveinc.booklibrary.session.manager.ReviewManagerLocal;
import com.softserveinc.booklibrary.session.manager.ReviewManagerRemote;
import com.softserveinc.booklibrary.session.persist.facade.ReviewFacadeLocal;
import com.softserveinc.booklibrary.session.persist.home.ReviewHomeLocal;

/**
 * The {@code ReviewManager} class is an implementation of business logic for
 * write operations for {@link Review} entity
 */
@Stateless
public class ReviewManager implements ReviewManagerLocal, ReviewManagerRemote {

	private static Logger log = LoggerFactory.getLogger(ReviewManager.class);

	@EJB
	private ReviewFacadeLocal reviewFacade;

	@EJB
	private ReviewHomeLocal reviewHome;

	@PostConstruct
	private void postConstruct() {
		log.debug("Bean has been created.");
	}

	@PreDestroy
	private void preDestroy() {
		log.debug("Bean has been destroyed.");
	}

	@Override
	public void createReview(Review review) throws ReviewManagerException {

		long startMethodTime = System.currentTimeMillis();
		log.debug("Method starts. Review to create ={}", review);
		String errorMessage = "";
		if (review == null) {
			errorMessage = "Passed review to create is null.";
			log.error(errorMessage);
			throw new ReviewManagerException(errorMessage);
		}
		if (review.getIdreview() != null) {
			errorMessage = "Passed review to create has id number and can't be created.";
			log.error(errorMessage);
			throw new ReviewManagerException(errorMessage);
		}
		validateReviewFields(review);
		reviewHome.create(review);
		if (review.getIdreview() == null) {
			errorMessage = "Creation of Review is not successful. Unexpected exception.";
			log.error(errorMessage);
			throw new ReviewManagerException(errorMessage);
		}
		log.info("Method successfully finished. That took {} milliseconds. Review {} has been created.",
				(System.currentTimeMillis() - startMethodTime), review);
	}

	@Override
	public void deleteReview(String idReview) throws ReviewManagerException {

		log.debug("Method starts. The review id ={}", idReview);
		String errorMessage = "";
		if (idReview == null) {
			errorMessage = "Passed id cannot be null.";
			log.error(errorMessage);
			throw new ReviewManagerException(errorMessage);
		}
		Review review = reviewFacade.findById(idReview);
		if (review == null) {
			errorMessage = String.format("The Review by id= %s hasn't found.", idReview);
			log.error(errorMessage);
			throw new ReviewManagerException(errorMessage);
		}
		reviewHome.delete(review);
		log.info("Method finished. The Review {} has been deleted.", review);

	}

	@Override
	public void updateReview(Review review) throws ReviewManagerException {

		log.debug("The method starts. Review to update ={}", review);
		validateReviewFields(review);
		String errorMessage = "";
		if (review.getBook() == null || review.getIdreview() == null) {
			errorMessage = String.format("The book must be present for review.", review.getBook());
			log.error(errorMessage);
			throw new ReviewManagerException(errorMessage);
		}
		Review reviewCheck = reviewFacade.findById(review.getIdreview());
		if (!reviewCheck.getBook().getIdBook().equals(review.getBook().getIdBook())) {
			errorMessage = String.format(
					"The review has been created for book %s, book can not be changed for current review",
					review.getBook());
			log.error(errorMessage);
			throw new ReviewManagerException(errorMessage);
		}
		review.setCreatedDate(reviewCheck.getCreatedDate());
		reviewHome.update(review);
		log.info("The method finished. Review {} has been successfully updated.", review);

	}

	/**
	 * This method provide checking <code>Review</code> instance variables in
	 * accordance with the business requirements. <code>Review</code> instance
	 * passed from UI component and should be created in system or updated. If
	 * any conditions do not meet the requirements for business logic for Review
	 * instance, method throw {@link ReviewManagerException}.
	 * 
	 * @param review
	 *            {@link Review} instance that should be checked.
	 * @throws ReviewManagerException
	 *             exception
	 */
	private void validateReviewFields(Review review) throws ReviewManagerException {

		String errorMessage = "";
		if (review.getBook() == null) {
			errorMessage = "For review book must be present.";
			log.error(errorMessage);
			throw new ReviewManagerException(errorMessage);
		}
		if (review.getCommenterName() == null || review.getCommenterName().length() > 50) {
			errorMessage = "Commenter name can't be null. Name of commentator cannot be more than 50 characters.";
			log.error(errorMessage);
			throw new ReviewManagerException(errorMessage);
		}
		if (review.getComment() == null) {
			errorMessage = "Comment cannot be null.";
			log.error(errorMessage);
			throw new ReviewManagerException(errorMessage);
		}
		if (review.getRating() != null) {
			if (review.getRating() < 1 || review.getRating() > 5) {
				errorMessage = "The rating should be in diapason between 1 and 5.";
				log.error(errorMessage);
				throw new ReviewManagerException(errorMessage);
			}
		}
	}

}
