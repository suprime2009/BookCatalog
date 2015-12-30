package com.softserveinc.model.persist.facade;

import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 * BookFacadeLocal an interface extended from IBookFacade interface and describes facade operations
 * for Book entity. This interface is local and used in application as main.
 *
 */
@Local
public interface BookFacadeLocal extends IBookFacade{
	
	
}
