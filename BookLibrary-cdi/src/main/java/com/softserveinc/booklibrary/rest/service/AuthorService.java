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


@Path("/author")
@Local
public interface AuthorService {
	
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
	
	public AuthorDTO convertToDTO(Author object);
	
	public List<Author> convertToListEntities(Collection<AuthorDTO> listDTO);
	
	public List<AuthorDTO> convertToListDTO(Collection<Author> list);

}
