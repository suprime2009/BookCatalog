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
import com.softserveinc.booklibrary.rest.dto.BookDTO;

/**
 * This interface contains methods used JAX-RS web service for Book entity.
 * These methods are available to web service clients and using them can perform
 * CRUD operations on {@code BookCatalog API}. This interface has path URL equal
 * {@code book} All these methods have their own path URL and defined HTTP
 * method. Also methods have specified type of returned data (json).
 *
 */
@Path("/book")
@Local
public interface BookService {

	/**
	 * The method returns Book entity in Json format by specified idBook passed
	 * by parameter. If passed id is null, method will return Status
	 * "Bad request". If the book is not found, the method will return Status
	 * "Not found".
	 * 
	 * @param id
	 *            book in database.
	 * @return Book entity in Json format.
	 */
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response findById(@PathParam("id") String id);

	/**
	 * The method returns all Books that present in database. Method used HTTP
	 * method "GET".
	 * 
	 * @return List of Books.
	 */
	@GET
	@Path("/")
	@Produces("application/json")
	public Response findAll();


	@POST
	@Consumes("application/json")
	public Response create(BookDTO bookDTO);

	@PUT
	@Consumes("application/json")
	public Response update(BookDTO bookDTO);

	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String id);

	@GET
	@Path("/author")
	@Produces("application/json")
	public Response getBooksByAuthor(@QueryParam("id") String idAuthor);

	@GET
	@Path("/rating")
	@Produces("application/json")
	public Response getReviewsByRating(@QueryParam("value") Integer rating);

	public Book convertToEntity(BookDTO dto);

	public BookDTO convertToDTO(Book object);

	public List<Book> convertToListEntities(List<BookDTO> listDTO);

	public List<BookDTO> convertToListDTO(List<Book> list);

}
