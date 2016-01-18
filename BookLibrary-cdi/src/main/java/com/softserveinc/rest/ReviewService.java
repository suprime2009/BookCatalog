package com.softserveinc.rest;

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

import com.softserveinc.model.persist.entity.Review;

@Path("/reviews")
@Local
public interface ReviewService {
	
	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Response findById( String id);
	
	@POST
	@Consumes("application/json")
	public Response create(Review review);
	
	@PUT
	@Consumes("application/json")
	public Response update(Review student);
	
	@DELETE
	@Path("/delete/{id}")
	public Response deleteById(@PathParam("id") String id);

}