package com.softserveinc.booklibrary.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.booklibrary.exception.BookCatalogException;
import com.softserveinc.booklibrary.exception.ReviewManagerException;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.model.entity.Review;
import com.softserveinc.booklibrary.rest.dto.ReviewDTO;
import com.softserveinc.booklibrary.session.manager.ReviewManagerLocal;
import com.softserveinc.booklibrary.session.persist.facade.BookFacadeLocal;
import com.softserveinc.booklibrary.session.persist.facade.ReviewFacadeLocal;


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
			System.out.println("BAD REQUEST");
			return Response.status(Status.BAD_REQUEST).build();
		}

		Review review = convertToEntity(reviewDTO);		
		try {
			reviewManager.createReview(review);
			builder = Response.status(Status.CREATED);
		} catch (ReviewManagerException e) {
			builder = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage());

		} catch (BookCatalogException e) {
			builder = Response.status(Response.Status.NOT_IMPLEMENTED).entity(e.getMessage());
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
			System.out.println("BAD REQUEST");
			return Response.status(Status.BAD_REQUEST).build();
		}
		Review review = null;
		if (reviewDTO.getIdBook() == null) {
			review = reviewFacade.findById(reviewDTO.getIdReview());
			reviewDTO.setIdBook(review.getBook().getIdBook());
		}
		
		review = convertToEntity(reviewDTO);	
		try {
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
		String idReview = dto.getIdReview();
		String comment = dto.getComment();
		String commenterName = dto.getCommenterName();
		Book book = bookFacade.findById(dto.getIdBook());
		Integer rating = dto.getRating();
		Review review = new Review(comment, commenterName, rating, book);
		review.setIdReview(idReview);
		log.info("The method done. Convertation from DTO to Review successful.");

		return review;
	}

	@Override
	public ReviewDTO convertToDTO(Review object) {
		String idReview = object.getIdreview();
		String comment = object.getComment();
		String commenterName = object.getCommenterName();
		String idBook = object.getBook().getIdBook();
		Integer rating = object.getRating();
		ReviewDTO dto = new ReviewDTO(idReview, comment, commenterName, idBook, rating);
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
