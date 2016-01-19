package com.softserveinc.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.softserveinc.exception.BookCatalogException;
import com.softserveinc.exception.ReviewManagerException;
import com.softserveinc.model.persist.entity.Review;
import com.softserveinc.model.persist.facade.ReviewFacadeLocal;
import com.softserveinc.model.session.manager.ReviewManagerLocal;

@Stateless
public class ReviewSeviceImpl implements ReviewService {

	@EJB
	private ReviewFacadeLocal reviewFacade;

	@EJB
	ReviewManagerLocal reviewManager;

	@Override
	public Response findById(String id) {
		Review review = reviewFacade.findById(id);
		if (review == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(review).build();
	}

	@Override
	public Response create(Review review) {
		Response.ResponseBuilder builder = null;

		try {
			validateReview(review);
			reviewManager.createReview(review);

			
			builder = Response.status(Status.CREATED);
		} catch (NullPointerException e) {
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);

		} catch (BookCatalogException e) {
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
		} catch (Exception e) {
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.NOT_IMPLEMENTED).entity(responseObj);
		}

		return builder.build();
	}
	
	@Override
	public Response findAll() {
		List<Review> list = reviewFacade.findAll();
		return Response.ok(list).build();
	}
	
	@Override
	public Response update(Review review) {
		Response.ResponseBuilder builder = null;

		try {
			validateReview(review);
			 reviewManager.updateReview(review);
			builder = Response.status(Status.OK);
		} catch (NullPointerException e) {
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);

		} catch (BookCatalogException e) {
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
		} catch (Exception e) {
			Map<String, String> responseObj = new HashMap<>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.NOT_IMPLEMENTED).entity(responseObj);
		}

		return builder.build();
	}

	@Override
	public Response deleteById(String id) {
		Response r = null;
		try {
			reviewManager.deleteReview(id);
		} catch (ReviewManagerException e) {
			return r = Response.ok("error").build();
		} 

			return r.ok("OK").build();
	}
	


	public void validateReview(Review review) throws BookCatalogException {
		if (review == null) {
			throw new NullPointerException();
		}
		if (review.getComment() == null || review.getCommenterName() == null) {
			throw new BookCatalogException("Commenter name or comment can't be empty.");
		}
		if (review.getRating() != null) {
			if (review.getRating() < 1 || review.getRating() > 5) {
				throw new BookCatalogException("Rating should be between 1 and 5.");
			}

		}
	}

	



}
