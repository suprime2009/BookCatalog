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

import com.softserveinc.model.persist.dto.ReviewDTO;
import com.softserveinc.model.persist.entity.Review;

@Path("/review")
@Local
public interface ReviewService {
	
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
	
	
	
	public Review convertToEntity(ReviewDTO dto);
	
	public ReviewDTO convertToDTO(Review object);
	
	public List<Review> convertToListEntities(List<ReviewDTO> listDTO);
	
	public List<ReviewDTO> convertToListDTO(List<Review> list);

}
