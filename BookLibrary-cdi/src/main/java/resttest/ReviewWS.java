package resttest;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.softserveinc.model.persist.entity.Review;
import com.softserveinc.model.persist.facade.ReviewFacadeLocal;

@Path("/review")
@Stateless
@LocalBean
public class ReviewWS {
	
	@EJB
	ReviewFacadeLocal reviewFacade;
	
	@GET
    @Path("/{id}")
	 @Produces({"application/json"})
    public Review getAuthor(@PathParam("id") String id) {
        return reviewFacade.findById(id);
    }

}
