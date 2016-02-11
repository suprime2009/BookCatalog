package com.softserveinc.booklibrary.rest.service;

import java.util.Collection;
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
import javax.ws.rs.core.Response;

import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.model.entity.Review;
import com.softserveinc.booklibrary.rest.dto.AuthorDTO;
import com.softserveinc.booklibrary.rest.dto.ReviewDTO;
import com.softserveinc.booklibrary.session.manager.impl.AuthorManager;

/**
 * This interface contains methods used JAX-RS web service for Authors entity.
 * These methods are available to web service clients and using them can perform
 * CRUD operations on {@code BookCatalog API}. This interface has path URL equal
 * {@code author} All these methods have their own path URL and defined HTTP
 * method. Also methods have specified type of returned data (json).
 *
 */
@Path("/author")
@Local
public interface AuthorService {

	/**
	 * The method returns Author entity in Json format by specified idAuthor
	 * passed by parameter. If passed id is null, method will return Status
	 * "Bad request". If the author is not found the method will return Status
	 * "Not found". Method used HTTP method "GET", URL path {@code /idAuthor}.
	 * 
	 * @param id
	 *            author in database.
	 * @return Author entity in Json format.
	 */
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response findById(@PathParam("id") String id);

	/**
	 * The method returns all Authors that present in database. Method used HTTP
	 * method "GET".
	 * 
	 * @return List of AuthorDTO.
	 */
	@GET
	@Path("/")
	@Produces("application/json")
	public Response findAll();

	/**
	 * The method provides possibility to create {@link Author} instance. The
	 * method gets in parameter {@code AuthorDTO} instance i Json format. If
	 * AuthorDTO object is null or has present field idAuthor, method will
	 * return Status "Bad request".
	 * 
	 * @param authorDTO
	 *            transfer object for {@link Author} entity.
	 * @return HTTP Status of operation.
	 * 
	 */
	@POST
	@Consumes("application/json")
	public Response create(AuthorDTO authorDTO);

	/**
	 * The method provides update operation for {@link Author} entity through
	 * REST eb service. The method used HTTP method "PUT" and expect
	 * {@link AuthorDTO} DTO entity in JSON format. The method sends response
	 * status "200" - "OK" if passed instance has been updated. If passed DTO
	 * instance doesn't have id or by passed id author not found the method
	 * sends status "404" - "Not Found" If update operation not successful and
	 * stoped on {@link AuthorManager}, method sends status "400" -
	 * "Bad request".
	 * 
	 * @param authorDTO
	 *            DTO instance for {@link Author} entity.
	 * @return HTTP response status.
	 */
	@PUT
	@Consumes("application/json")
	public Response update(AuthorDTO authorDTO);

	/**
	 * The method provides delete operation for {@link Author} entity through
	 * REST eb service. The method used HTTP method "DELETE" and expect id of
	 * author to delete. If passed id is null, method returns Status
	 * "Bad request". If the author is not found, the method returns Status
	 * "Not found". If delete operation is successful method returns Status
	 * "200" - "OK".
	 * 
	 * @param id
	 *            of {@link Author} to delete
	 * @return HTTP response status.
	 */
	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String id);

	/**
	 * The method returns {@link BookDTO} books in JSON format authored by
	 * author whoese id passed through parameter. If passed id is null, method
	 * returns Status "Bad request". If the author is not found, the method
	 * returns Status "Not found".
	 * 
	 * @param idAuthor
	 * @return List of {@link BookDTO} entities in JSON.
	 */
	@GET
	@Path("/{author_id}/books")
	@Produces("application/json")
	public Response getBooksByAuthor(@PathParam("author_id") String idAuthor);

	/**
	 * This method is a converter from DTO to entity. The method convert
	 * {@link AuthorDTO} dto to {@link Author} entity.
	 * 
	 * @param dto
	 * @return
	 */
	public Author convertToEntity(AuthorDTO dto);

	/**
	 * This method is a converter from entity to DTO. The method convert
	 * {@link Author} entity to {@link AuthorDTO} dto.
	 * 
	 * @param entity
	 *            {@link Author}
	 * @return dto {@link AuthorDTO}
	 */
	public AuthorDTO convertToDTO(Author object);

	public List<Author> convertToListEntities(Collection<AuthorDTO> listDTO);

	public List<AuthorDTO> convertToListDTO(Collection<Author> list);

}
