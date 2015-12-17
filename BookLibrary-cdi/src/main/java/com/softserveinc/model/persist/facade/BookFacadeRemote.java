package com.softserveinc.model.persist.facade;

import javax.ejb.Remote;

@Remote(BookFacadeRemote.class)
public interface BookFacadeRemote extends IBookFacade {

}
