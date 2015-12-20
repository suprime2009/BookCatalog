package com.softserveinc.model.persist.home;


import javax.ejb.Remote;
import com.softserveinc.model.persist.entity.Author;

/**
 * AuthorHomeRemote an interface extended from IHome interface and describes basic CRUD operation
 * for Author entity. This interface is remote and used in application only for testing.
 *
 */
@Remote(AuthorHomeRemote.class)
public interface AuthorHomeRemote extends IHome<Author>{
	
}
