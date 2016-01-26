package com.softserveinc.model.persist.home;

import java.util.List;

import javax.ejb.Remote;
import com.softserveinc.model.persist.entity.Book;

/**
 * 
 * The {@code BookHomeRemote} an interface, extended from {@link IHome}
 * interface. It provides basic CRUD and all write operations for {@link Book}
 * entity. This interface is remote and used in application only for testing.
 */
@Remote(BookHomeRemote.class)
public interface BookHomeRemote extends IHome<Book> {

	/**
	 * The method provides delete operation in a database table for {@code List}
	 * of Book. After execution this method all books, passed by parameter will
	 * be deleted from the database.
	 * 
	 * @param List
	 *            of Book to delete
	 */
	void bulkRemove(List<Book> list);

}
