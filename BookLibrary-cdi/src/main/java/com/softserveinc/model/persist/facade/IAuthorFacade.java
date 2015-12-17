package com.softserveinc.model.persist.facade;

import java.util.List;

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;

/**
 * Describes all facade methods for Author entity.
 *
 */
public interface IAuthorFacade {
	
	/**
	 * Method gets authors of book from database.
	 * @param book entity
	 * @return List<Authors>
	 */
	List<Author> findAllAuthorsByBook(Book book);

	/**
	 * Method gets author from database by first name and second name.
	 * @param firstName
	 * @param secondName
	 * @return Author
	 */
	Author findAuthorByFullName(String firstName, String secondName);

	/**
	 * Method gets authors of books by average rating from reviews.
	 * @param rating
	 * @return
	 */
	List<Author> findAuthorsByAverageRating(String rating);
	
	Author findById(String id);
	
	List<Author> findAll();

}
