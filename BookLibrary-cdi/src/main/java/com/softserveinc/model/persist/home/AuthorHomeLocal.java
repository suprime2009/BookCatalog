package com.softserveinc.model.persist.home;



import javax.ejb.Local;
import com.softserveinc.model.persist.entity.Author;

/**
 * AuthorHomeLocal an interface extended from IHome interface and describes basic CRUD operation
 * for Author entity. This interface is local and used in application as main.
 *
 */
@Local(AuthorHomeLocal.class)
public interface AuthorHomeLocal extends IHome<Author>{
	
}
