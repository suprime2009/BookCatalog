package com.softserveinc.booklibrary.session.persist.home;

import java.util.List;

import javax.ejb.Remote;

import com.softserveinc.booklibrary.model.entity.Author;



/**
 * 
 * The {@code AuthorHomeRemote} an interface, extended from {@link IHome}
 * interface. It provides basic CRUD and all write operations for {@link Author}
 * entity. This interface is remote and used in application only for testing.
 *
 */
@Remote(AuthorHomeRemote.class)
public interface AuthorHomeRemote extends IHome<Author> {

	/**
	 * The method provides delete operation in a database table for {@code List}
	 * of Author. After execution this method all authors, passed by parameter
	 * will be deleted from the database.
	 * 
	 * @param List
	 *            of Author to delete
	 */
	void bulkRemove(List<Author> list);

}
