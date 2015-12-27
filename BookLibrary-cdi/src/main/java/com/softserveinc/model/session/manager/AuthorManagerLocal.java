package com.softserveinc.model.session.manager;

import javax.ejb.Local;

@Local(AuthorManagerLocal.class)
public interface AuthorManagerLocal extends IManagerAuthor{

}
