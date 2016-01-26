package com.softserveinc.model.persist.home;

import java.util.List;

import javax.ejb.Local;

import com.softserveinc.model.persist.entity.Book;

/**
 * The {@code BookHomeLocal} an interface, extended from {@link IHome}
 * interface. It provides basic CRUD and all write operations for {@link Book}
 * entity. This interface is a local.
 *
 */
@Local
public interface BookHomeLocal extends IHome<Book> {
	
	/**
	 * The method provides delete operation in a database table for 
	 * {@code List} of Book. After execution this method all books,
	 * passed by parameter will be deleted from the database.
	 * 
	 * @param  List of Book to delete
	 */
	void bulkRemove(List<Book> list);

}
