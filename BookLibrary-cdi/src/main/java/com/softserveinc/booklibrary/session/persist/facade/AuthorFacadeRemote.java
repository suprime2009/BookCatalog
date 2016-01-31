package com.softserveinc.booklibrary.session.persist.facade;

import javax.ejb.Remote;
/**
 * AuthorFacadeRemote an interface extended from IAuthorFacade interface and describes describes facade operations
 * for Author entity. This interface is remote and used in application only for testing.
 *
 */
@Remote(AuthorFacadeRemote.class)
public interface AuthorFacadeRemote extends IAuthorFacade{

}
