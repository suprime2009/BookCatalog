package com.softserveinc.model.persist.home;

import java.util.List;

import javax.ejb.Local;
import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;

/**
 * The {@code AuthorHomeLocal} an interface, extended from {@link IHome}
 * interface. It provides basic CRUD and all write operations for {@link Author}
 * entity. This interface is a local.
 *
 */
@Local(AuthorHomeLocal.class)
public interface AuthorHomeLocal extends IHome<Author> {

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
