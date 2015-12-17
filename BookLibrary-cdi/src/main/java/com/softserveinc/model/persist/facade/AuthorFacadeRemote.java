package com.softserveinc.model.persist.facade;

import javax.ejb.Remote;
/**
 * javadoc!!!!!!!!!!!!!!!
 * @author Pavel
 *
 */
@Remote(AuthorFacadeRemote.class)
public interface AuthorFacadeRemote extends IAuthorFacade{

}
