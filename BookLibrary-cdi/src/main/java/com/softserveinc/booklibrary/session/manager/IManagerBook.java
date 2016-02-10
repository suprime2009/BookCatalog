package com.softserveinc.booklibrary.session.manager;

import java.util.List;

import com.softserveinc.booklibrary.exception.BookManagerException;
import com.softserveinc.booklibrary.model.entity.Book;


/**
 * This interface include methods, that describe business logic for write
 * operations for {@link Book} entity.
 *
 */
public interface IManagerBook {

	/**
	 * The method provide create operation for {@link Book} entity. Methods gets
	 * in parameter {@code book} entity that should be created in dataBase.
	 * Method provides validation of this entity.
	 * <p>
	 * Method throw {@link BookManagerException} if: passed entity is null or if
	 * book has present id number, ISBN number of book already present in
	 * dataBase, fields yearpublished, bookName, publisher, isbn don't meet
	 * requirements.
	 * <p>
	 * if method finish successfully, book will be passed to home method and
	 * persisted in database.
	 * 
	 * @param book
	 *            entity
	 * @throws BookManagerException
	 *             exception
	 */
	void createBook(Book book) throws BookManagerException;

	/**
	 * The method provide update operation for {@link Book} entity. Methods gets
	 * in parameter {@code book} entity that should be updated in dataBase.
	 * Method provides validation of this entity.
	 * <p>
	 * Method throw {@link BookManagerException} if: passed entity is null or if
	 * book hasn't present id number, ISBN number of book already present in
	 * dataBase, fields yearpublished, bookName, publisher, isbn don't meet
	 * requirements.
	 * <p>
	 * if method finish successfully, book will be passed to home method and
	 * merged in database.
	 * 
	 * @param book
	 *            entity
	 * @throws BookManagerException
	 *             exception
	 */
	void updateBook(Book book) throws BookManagerException;

	/**
	 * The method provide delete operation for {@link Book} entity. Methods gets
	 * in parameter {@code idBook} field. By that idBook should be found Book
	 * entity and deleted from database.
	 * <p>
	 * Method throw {@link BookManagerException} if: passed idBook is null, or
	 * by passed idBook not found book.
	 * <p>
	 * if method finish successfully, book will be passed to home method and
	 * removed from database.
	 * 
	 * @param idBook
	 *            String
	 * @throws BookManagerException
	 *             exception
	 */
	void deleteBook(String idBook) throws BookManagerException;

	/**
	 * The method provide delete operation for {@link Book} entities that passed
	 * by parameter.
	 * <p>
	 * Method throw {@link BookManagerException} if: passed List of books is
	 * null.
	 * <p>
	 * if method finish successfully, all passed books will be passed to home
	 * method and removed from database.
	 * 
	 * @param list
	 *            of {@link Book}
	 * @throws BookManagerException
	 *             exception
	 */
	void bulkDelete(List<Book> list) throws BookManagerException;
	
	public void validateAuthorsField(Book book) throws BookManagerException;

}
