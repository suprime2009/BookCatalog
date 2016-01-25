package com.softserveinc.rest;

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

import com.softserveinc.model.persist.dto.BookDTO;
import com.softserveinc.model.persist.dto.ReviewDTO;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.Review;

@Path("/book")
@Local
public interface BookService {
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response findById(@PathParam("id") String id);
	
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
