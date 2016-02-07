package com.softserveinc.booklibrary.session.manager;

import javax.ejb.Local;

/**
 * AuthorManagerLocal an interface extended from IManagerAuthor interface and
 * describes business operations for Author entity. This interface is local and
 * used in application as main.
 *
 */
@Local(AuthorManagerLocal.class)
public interface AuthorManagerLocal extends IManagerAuthor {

}
