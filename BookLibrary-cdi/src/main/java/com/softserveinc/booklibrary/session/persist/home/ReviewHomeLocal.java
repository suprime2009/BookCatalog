package com.softserveinc.booklibrary.session.persist.home;

import java.util.List;

import javax.ejb.Local;

import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.model.entity.Review;



/**
 * The {@code ReviewHomeLocal} an interface, extended from {@link IHome}
 * interface. It provides basic CRUD and all write operations for {@link Review}
 * entity. This interface is a local.
 *
 */
@Local
public interface ReviewHomeLocal extends IHome<Review> {
	
	public int bulkRemoveByBook(List<Book> books);

}
