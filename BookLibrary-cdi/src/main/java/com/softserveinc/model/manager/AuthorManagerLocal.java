package com.softserveinc.model.manager;

import javax.ejb.Local;

@Local(AuthorManagerLocal.class)
public interface AuthorManagerLocal extends IManagerAuthor{

}
