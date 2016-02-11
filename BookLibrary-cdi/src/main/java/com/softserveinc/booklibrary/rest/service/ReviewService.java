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

import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.model.entity.Review;
import com.softserveinc.booklibrary.rest.dto.ReviewDTO;
import com.softserveinc.booklibrary.session.manager.impl.ReviewManager;

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
	 * @return ReviewDTO instance in Json format.
	 */
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response findById(@PathParam("id") String id);

	/**
	 * The method returns all Reviews that present in database. Method used HTTP
	 * method "GET".
	 * 
	 * @return List of ReviewDTO.
	 */
	@GET
	@Path("/")
	@Produces("application/json")
	public Response findAll();

	/**
	 * The method provides create operation for {@link Review} entity through
	 * REST web service. The method used HTTP method "POST" and expect
	 * {@link ReviewDTO} DTO entity in JSON format. The method sends response
	 * status "201" - "Created" if passed instance has been persisted. If
	 * persisting operation not successful and stoped on {@link ReviewManager},
	 * method sends status "400" - "Bad request".
	 * 
	 * @param reviewDTO
	 *            DTO instance for {@link Review} entity.
	 * @return HTTP response status.
	 */
	@POST
	@Consumes("application/json")
	public Response create(ReviewDTO reviewDTO);

	/**
	 * The method provides update operation for {@link Review} entity through
	 * REST eb service. The method used HTTP method "PUT" and expect
	 * {@link ReviewDTO} DTO entity in JSON format. The method sends response
	 * status "200" - "OK" if passed instance has been updated. If passed DTO
	 * instance doesn't have id or by passed id review not found the method
	 * sends status "404" - "Not Found" If update operation not successful and
	 * stoped on {@link ReviewManager}, method sends status "400" -
	 * "Bad request".
	 * 
	 * @param reviewDTO
	 *            DTO instance for {@link Review} entity.
	 * @return HTTP response status.
	 */
	@PUT
	@Consumes("application/json")
	public Response update(ReviewDTO reviewDTO);

	/**
	 * The method provides delete operation for {@link Review} entity through
	 * REST eb service. The method used HTTP method "DELETE" and expect id of
	 * review to delete. If passed id is null, method returns Status
	 * "Bad request". If the review is not found, the method returns Status
	 * "Not found". If delete operation is successful method returns Status
	 * "200" - "OK".
	 * 
	 * @param id
	 *            of {@link Review} to delete
	 * @return HTTP response status.
	 */
	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String id);

	/**
	 * The method returns all {@link ReviewDTO} objects in JSON format for book
	 * {@link Book} whose id passed through parameter. If passed id is null,
	 * method returns Status "Bad request". If the book is not found, the method
	 * returns Status "Not found".
	 * 
	 * @param idBook
	 *            od {@link Book} entity.
	 * @return List of {@link ReviewDTO} objects.
	 */
	@GET
	@Path("/book")
	@Produces("application/json")
	public Response getReviewsByBook(@QueryParam("id") String idBook);

	/**
	 * This method is a converter from DTO to entity. The method convert
	 * {@link ReviewDTO} dto to {@link Review} entity.
	 * 
	 * @param dto
	 *            {@link ReviewDTO}
	 * @return entity {@link Review}
	 */
	public Review convertToEntity(ReviewDTO dto);

	/**
	 * This method is a converter from entity to DTO. The method convert
	 * {@link Review} entity to {@link ReviewDTO} dto.
	 * 
	 * @param entity
	 *            {@link Review}
	 * @return dto {@link ReviewDTO}
	 */
	public ReviewDTO convertToDTO(Review object);

	public List<Review> convertToListEntities(List<ReviewDTO> listDTO);

	public List<ReviewDTO> convertToListDTO(List<Review> list);

}
