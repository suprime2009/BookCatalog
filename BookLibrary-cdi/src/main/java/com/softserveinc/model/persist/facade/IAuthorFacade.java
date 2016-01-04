package com.softserveinc.model.persist.facade;

import java.util.List;

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;

/**
 * IAuthorFacade an interface that describes all facade methods for Author entity.
 *
 */
public interface IAuthorFacade {
	
	/**
	 * Method gets authors of book from database.
	 * @param book entity
	 * @return List<Authors>.
	 */
	List<Author> findAllAuthorsByBook(Book book);

	/**
	 * Method gets author from database by first name and second name.
	 * @param firstName
	 * @param secondName
	 * @return Author.
	 */
	Author findAuthorByFullName(String firstName, String secondName);

	/**
	 * Method gets authors of books by average rating from reviews.
	 * @param rating.
	 * @return List<Author>.
	 */
	//List<Author> findAuthorsByAverageRating(String rating);
	
	/**
	 * Method gets author by id number from database.
	 * @param id number in database.
	 * @return Author.
	 */
	Author findById(String id);
	
	/**
	 * Method gets all authors from database.
	 * @return List<Author>.
	 */
	List<Author> findAll();
	
	List<Author> findAuthorsBySecondName(String secondName);

}
