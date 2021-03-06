package com.softserveinc.booklibrary.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.booklibrary.exception.ReviewManagerException;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.model.entity.Review;
import com.softserveinc.booklibrary.rest.dto.ReviewDTO;
import com.softserveinc.booklibrary.session.manager.ReviewManagerLocal;
import com.softserveinc.booklibrary.session.persist.facade.BookFacadeLocal;
import com.softserveinc.booklibrary.session.persist.facade.ReviewFacadeLocal;

/**
 * This class is a implementation methods used JAX-RS web service for Review
 * entity. These methods are available to web service clients and using them can
 * perform CRUD operations on {@code BookCatalog API}.
 *
 */
@Stateless
public class ReviewSeviceImpl implements ReviewService {

	private static Logger log = LoggerFactory.getLogger(ReviewSeviceImpl.class);

	@EJB
	private ReviewFacadeLocal reviewFacade;

	@EJB
	private ReviewManagerLocal reviewManager;

	@EJB
	private BookFacadeLocal bookFacade;

	@Override
	public Response findById(String id) {
		if (id == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Review review = reviewFacade.findById(id);
		if (review == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		ReviewDTO dto = convertToDTO(review);
		return Response.ok(dto).build();
	}

	@Override
	public Response create(ReviewDTO reviewDTO) {
		Response.ResponseBuilder builder = null;
		if (reviewDTO == null || reviewDTO.getIdReview() != null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		try {
			Review review = convertToEntity(reviewDTO);
			reviewManager.createReview(review);
			builder = Response.status(Status.CREATED);
		} catch (ReviewManagerException e) {
			builder = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage());
		}
		return builder.build();
	}

	@Override
	public Response findAll() {
		List<Review> list = reviewFacade.findAll();
		List<ReviewDTO> dto = convertToListDTO(list);
		return Response.ok(dto).build();
	}

	@Override
	public Response update(ReviewDTO reviewDTO) {

		Response.ResponseBuilder builder = null;
		if (reviewDTO == null || reviewDTO.getIdReview() == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		try {
			Review review = convertToEntity(reviewDTO);
			reviewManager.updateReview(review);
			builder = Response.status(Status.OK);
		} catch (ReviewManagerException e) {
			builder = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage());
		}
		return builder.build();
	}

	@Override
	public Response deleteById(String id) {
		try {
			reviewManager.deleteReview(id);
		} catch (ReviewManagerException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

		return Response.ok().build();
	}

	@Override
	public Response getReviewsByBook(String idBook) {
		if (idBook == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Book book = bookFacade.findById(idBook);
		if (book == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		List<Review> list = reviewFacade.findReviewsForBook(book);
		List<ReviewDTO> result = convertToListDTO(list);
		return Response.ok(result).build();
	}

	@Override
	public Review convertToEntity(ReviewDTO dto) {

		Book book = null;
		Review review = new Review(dto.getComment(), dto.getCommenterName(), dto.getRating(), book);
		if (dto.getIdReview() != null) {
			review.setIdReview(dto.getIdReview());
			Review reviewFromBase = reviewFacade.findById(dto.getIdReview());
			if (reviewFromBase != null) {
				review.setCreatedDate(reviewFromBase.getCreatedDate());
				review.setBook(bookFacade.findById(dto.getIdBook()));
			}
		} else {
			review.setBook(bookFacade.findById(dto.getIdBook()));
		}
		log.info("The method done. Convertation from DTO to Review successful.");
		return review;
	}

	@Override
	public ReviewDTO convertToDTO(Review object) {
		ReviewDTO dto = new ReviewDTO(object.getIdreview(), object.getComment(), object.getCommenterName(),
				object.getBook().getIdBook(), object.getRating());
		log.info("The method done. Convertation from Review to ReviewDTO successful.");
		return dto;

	}

	@Override
	public List<Review> convertToListEntities(List<ReviewDTO> listDTO) {
		List<Review> list = new ArrayList<Review>();
		for (ReviewDTO revD : listDTO) {
			list.add(convertToEntity(revD));
		}
		return list;
	}

	@Override
	public List<ReviewDTO> convertToListDTO(List<Review> list) {
		List<ReviewDTO> listDTO = new ArrayList<ReviewDTO>();
		for (Review r : list) {
			listDTO.add(convertToDTO(r));
		}
		return listDTO;
	}

}
