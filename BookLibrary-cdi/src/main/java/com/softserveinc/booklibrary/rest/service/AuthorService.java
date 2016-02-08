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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.rest.dto.AuthorDTO;

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
	 * @return List of Author.
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

	@PUT
	@Consumes("application/json")
	public Response update(AuthorDTO authorDTO);

	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String id);

	@GET
	@Path("/{author_id}/books")
	@Produces("application/json")
	public Response getBooksByAuthor(@PathParam("author_id") String idAuthor);
	
	public Author convertToEntity(AuthorDTO dto);
	
	public  AuthorDTO convertToDTO(Author object);
	
	public  List<Author> convertToListEntities(Collection<AuthorDTO> listDTO);
	
	public  List<AuthorDTO> convertToListDTO(Collection<Author> list);

}
