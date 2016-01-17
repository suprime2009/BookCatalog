package resttest;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.facade.AuthorFacadeLocal;

@Path("/author")
@Stateless
@LocalBean
public class AuthorWs {
	
	@EJB
	private AuthorFacadeLocal authorFacade;
	
	@GET
    @Path("/{id}")
	 @Produces({"application/xml", "application/json"})
    public String getAuthor(@PathParam("id") String id) {
        return "HelloWorld";
    }

}
