package com.softserveinc.model.persist.facade;

import javax.ejb.Remote;

/**
 * BookFacadeRemote an interface extended from IBookFacade interface and describes describes facade operations
 * for Book entity. This interface is remote and used in application only for testing.
 *
 */
@Remote(BookFacadeRemote.class)
public interface BookFacadeRemote extends IBookFacade {

}
