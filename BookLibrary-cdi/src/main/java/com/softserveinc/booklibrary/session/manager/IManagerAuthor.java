package com.softserveinc.booklibrary.session.manager;

import java.util.List;

import com.softserveinc.booklibrary.exception.AuthorManagerException;
import com.softserveinc.booklibrary.model.entity.Author;


/**
 * This interface include methods, that describe business logic for write
 * operations for {@link Author} entity.
 *
 */
public interface IManagerAuthor {

	/**
	 * The method provide create operation for {@link Author} entity. Methods
	 * gets in parameter {@code author} entity that should be created in
	 * dataBase. Method provides validation of this entity.
	 * <p>
	 * Method throw {@link AuthorManagerException} if: passed entity is null or
	 * if author has present id number, author firstName and secondName don't
	 * meet requirements.
	 * <p>
	 * if method finish successfully, author will be passed to home method and
	 * persisted in database.
	 * 
	 * @param author
	 *            entity
	 * @throws AuthorManagerException
	 *             exception
	 */
	void createAuthor(Author author) throws AuthorManagerException;

	/**
	 * The method provide create update for {@link Author} entity. Methods gets
	 * in parameter {@code author} entity that should be updated in dataBase.
	 * Method provides validation of this entity.
	 * <p>
	 * Method throw {@link AuthorManagerException} if: passed entity is null or
	 * if author hasn't present id number, author firstName and secondName don't
	 * meet requirements.
	 * <p>
	 * if method finish successfully, author will be passed to home method and
	 * merged in database.
	 * 
	 * @param author
	 *            entity
	 * @throws AuthorManagerException
	 *             exception
	 */
	void updateAuthor(Author author) throws AuthorManagerException;

	/**
	 * The method provide delete operation for {@link Author} entity. Methods
	 * gets in parameter {@code idAuthor} field. By that idAuthor should be
	 * found Author entity and deleted from database.
	 * <p>
	 * Method throw {@link AuthorManagerException} if: passed idAuthor is null,
	 * or by passed idAuthor not found author.
	 * <p>
	 * if method finish successfully, author will be passed to home method and
	 * removed from database.
	 * 
	 * @param idAuthor
	 *            String
	 * @throws AuthorManagerException
	 *             exception
	 */
	void deleteAuthor(String idAuthor) throws AuthorManagerException;

	/**
	 * The method provide delete operation for {@link Author} entity. Methods
	 * gets in parameter {@code idAuthor} field. By that idAuthor should be
	 * found Author entity and deleted from database only if author has no one
	 * book whose author he is.
	 * <p>
	 * Method throw {@link AuthorManagerException} if: passed idAuthor is null,
	 * or by passed idAuthor not found author.
	 * <p>
	 * if method finish successfully, author will be passed to home method and
	 * removed from database.
	 * 
	 * @param idAuthor
	 *            String
	 * @throws AuthorManagerException
	 *             exception
	 */
	void deleteAuthorWithNoBooks(String idAuthor) throws AuthorManagerException;

	/**
	 * The method provide delete operation for {@link Author} entities that
	 * passed by parameter. Method will delete only those authors from list,
	 * that have no books.
	 * <p>
	 * Method throw {@link AuthorManagerException} if: passed List of authors is
	 * null or empty and if field books not null or not empty.
	 * <p>
	 * if method finish successfully, all passed authors that have no books will
	 * be passed to home method and removed from database.
	 * 
	 * @param list
	 *            of {@link Author}
	 * @throws AuthorManagerException
	 *             exception
	 */
	void bulkDeleteAuthorsWithNoBooks(List<Author> list) throws AuthorManagerException;

}
