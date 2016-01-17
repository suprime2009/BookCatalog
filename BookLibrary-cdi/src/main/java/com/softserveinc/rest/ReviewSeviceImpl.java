package com.softserveinc.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.softserveinc.exception.BookCatalogException;
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
			boolean status = reviewManager.createReview(review);
			if (status == false) {
				throw new Exception("Unexpected internal error.");
			}
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

	@Override
	public Response update(Review student) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response deleteById(String id) {
		Response r = null;
		boolean result = reviewManager.deleteReview(id);
		if (result) {
			return r.ok("OK").build();
		} else {
			return r = Response.ok("error").build();
		}
	}

}
