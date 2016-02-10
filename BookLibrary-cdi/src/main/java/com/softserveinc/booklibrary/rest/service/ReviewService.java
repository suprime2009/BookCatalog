package com.softserveinc.booklibrary.rest.service;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.softserveinc.booklibrary.exception.RestDTOConvertException;
import com.softserveinc.booklibrary.model.entity.Review;
import com.softserveinc.booklibrary.rest.dto.ReviewDTO;

/**
 * This interface contains methods used JAX-RS web service for Review entity.
 * These methods are available to web service clients and using them can perform
 * CRUD operations on {@code BookCatalog API}. This interface has path URL equal
 * {@code review} All these methods have their own path URL and defined HTTP
 * method. Also methods have specified type of returned data (json).
 *
 */
@Path("/review")
@Local
public interface ReviewService {

	/**
	 * The method returns {@link Review} entity in Json format by specified
	 * idReview passed by parameter. If passed id is null, method will return
	 * Status "Bad request". If the review is not found, the method will return
	 * Status "Not found".
	 * 
	 * @param id
	 *            review in database.
	 * @return Review entity in Json format.
	 */
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response findById(@PathParam("id") String id);

	/**
	 * The method returns all Reviews that present in database. Method used HTTP
	 * method "GET".
	 * 
	 * @return List of Reviews.
	 */
	@GET
	@Path("/")
	@Produces("application/json")
	public Response findAll();

	@POST
	@Consumes("application/json")
	public Response create(ReviewDTO reviewDTO);

	@PUT
	@Consumes("application/json")
	public Response update(ReviewDTO reviewDTO);

	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String id);

	@GET
	@Path("/book")
	@Produces("application/json")
	public Response getReviewsByBook(@QueryParam("id") String idBook);

	public Review convertToEntity(ReviewDTO dto) throws RestDTOConvertException;

	public ReviewDTO convertToDTO(Review object);

	public List<Review> convertToListEntities(List<ReviewDTO> listDTO) throws RestDTOConvertException;

	public List<ReviewDTO> convertToListDTO(List<Review> list);

}
