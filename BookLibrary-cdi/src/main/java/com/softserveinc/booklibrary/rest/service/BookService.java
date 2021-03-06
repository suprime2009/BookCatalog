package com.softserveinc.booklibrary.rest.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
import com.softserveinc.booklibrary.session.manager.impl.BookManager;

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

	/**
	 * The method provides create operation for {@link Book} entity through REST
	 * web service. The method used HTTP method "POST" and expect
	 * {@link BookDTO} DTO entity in JSON format. The method sends response
	 * status "201" - "Created" if passed instance has been persisted. If
	 * persisting operation not successful and stoped on {@link BookManager},
	 * method sends status "400" - "Bad request".
	 * 
	 * @param bookDTO
	 *            DTO instance for {@link Book} entity.
	 * @return HTTP response status.
	 */
	@POST
	@Consumes("application/json")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Response create(BookDTO bookDTO);

	/**
	 * The method provides update operation for {@link Book} entity through REST
	 * eb service. The method used HTTP method "PUT" and expect {@link BookDTO}
	 * DTO entity in JSON format. The method sends response status "200" - "OK"
	 * if passed instance has been updated. If passed DTO instance doesn't have
	 * id or by passed id no one book has been found the method sends status
	 * "404" - "Not Found" If update operation not successful and stoped on
	 * {@link BookManager}, method sends status "400" - "Bad request".
	 * 
	 * @param bookDTO
	 *            DTO instance for {@link Book} entity.
	 * @return HTTP response status.
	 */
	@PUT
	@Consumes("application/json")
	public Response update(BookDTO bookDTO);

	/**
	 * The method provides delete operation for {@link Book} entity through REST
	 * eb service. The method used HTTP method "DELETE" and expect id of book to
	 * delete. If passed id is null, method returns Status "Bad request". If the
	 * review is not found, the method returns Status "Not found". If delete
	 * operation is successful method returns Status "200" - "OK".
	 * 
	 * @param id
	 *            of {@link Book} to delete
	 * @return HTTP response status.
	 */
	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String id);

	/**
	 * The method returns a {@link BookDTO} books in JSON format authored by
	 * author whoese id passed through parameter. If passed id is null, method
	 * returns Status "Bad request". If the author is not found, the method
	 * returns Status "Not found".
	 * 
	 * @param idAuthor
	 * @return List of {@link BookDTO} entities in JSON.
	 */
	@GET
	@Path("/author")
	@Produces("application/json")
	public Response getBooksByAuthor(@QueryParam("id") String idAuthor);

	/**
	 * The method returns a {@link BookDTO} books in JSON format in which
	 * average rating equal to rating value passed through parameter. If passed
	 * value not in range between 1 and 5, method retuns HTTP Status "400" -
	 * "Bad request".
	 * 
	 * @param rating
	 *            int
	 * @return List of dto {@link BookDTO}.
	 */
	@GET
	@Path("/rating")
	@Produces("application/json")
	public Response getBooksByRating(@QueryParam("value") Integer rating);

	/**
	 * This method is a converter from DTO to entity. The method convert
	 * {@link BookDTO} dto to {@link Book} entity.
	 * 
	 * @param dto
	 *            {@link BookDTO}
	 * @return entity {@link Book}
	 */
	public Book convertToEntity(BookDTO dto);

	/**
	 * This method is a converter from entity to DTO. The method convert
	 * {@link Book} entity to {@link BookDTO} dto.
	 * 
	 * @param entity
	 *            {@link Book}
	 * @return dto {@link BookDTO}
	 */
	public BookDTO convertToDTO(Book object);

	public List<Book> convertToListEntities(List<BookDTO> listDTO);

	public List<BookDTO> convertToListDTO(List<Book> list);

}
