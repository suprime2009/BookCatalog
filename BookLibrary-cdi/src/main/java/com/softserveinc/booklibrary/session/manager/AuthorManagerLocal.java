package com.softserveinc.booklibrary.session.manager;

import javax.ejb.Local;

@Local(AuthorManagerLocal.class)
public interface AuthorManagerLocal extends IManagerAuthor{

}
