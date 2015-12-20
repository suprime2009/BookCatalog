package com.softserveinc.model.persist.facade;

import javax.ejb.Local;

/**
 * AuthorFacadeLocal an interface extended from IAuthorFacade interface and describes facade operations
 * for Author entity. This interface is local and used in application as main.
 *
 */
@Local
public interface AuthorFacadeLocal extends IAuthorFacade {
	
}
