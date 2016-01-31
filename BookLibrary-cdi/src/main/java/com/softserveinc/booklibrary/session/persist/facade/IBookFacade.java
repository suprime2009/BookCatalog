package com.softserveinc.booklibrary.session.persist.facade;

import java.util.List;

import com.softserveinc.booklibrary.action.util.DataTableSearchHolder;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.model.entity.Book;


/**
 * IBookFacade is an interface that describes all facade (read) operations for
 * {@link Book} entity.
 *
 */
public interface IBookFacade {

	public static final String PERSISTANCE_UNIT_PRIMARY = "primary";

	/**
	 * Method returns {@link Book} from database table by book name.
	 * 
	 * @param Book
	 *            name.
	 * @return List of Books.
	 */
	List<Book> findBookByName(String name);

	/**
	 * Method returns book from database table by ISBN number.
	 * 
	 * @param ISBN
	 *            unique book number.
	 * @return Book entity.
	 */
	Book findBookByISNBN(String isbn);

	/**
	 * Method returns books by author from database.
	 * 
	 * @param author
	 *            {@link Author} entity.
	 * @return list {@code List<Book>}.
	 */
	List<Book> findBooksByAuthor(Author author);

	/**
	 * Method returns book by id number from database.
	 * 
	 * @param id
	 *            number from database.
	 * @return Book.
	 */
	Book findById(String id);

	/**
	 * Method returns all books from database.
	 * 
	 * @return List<Book>.
	 */
	List<Book> findAll();

	/**
	 * Method returns count of all books from database.
	 * 
	 * @return {@code int} count of all books.
	 */
	int findCountAllBooks();

	/**
	 * Method returns {@code List<Book>} from database based on current dataTable
	 * requirements. DataTable requirements are a: number of first row in DB,
	 * count rows in result, name of Book field for sorting and Sort order,
	 * names Book fields and values for filtering. All that requirement
	 * described in {@link DataTableSearchHolder} instance. Based on this data
	 * method returns List of {@link Book}.
	 * 
	 * @param dataTableSearchHolder
	 *            {@link DataTableSearchHolder} describe current dataTable
	 *            requirements.
	 * @return List
	 */
	List<Book> findBooksForDataTable(DataTableSearchHolder dataTableSearchHolder);

	/**
	 * Method returns count of books from database based on current dataTable
	 * requirements. DataTable requirements are a: number of first row in DB,
	 * count rows in result, name of Book field for sorting and Sort order,
	 * names Book fields and values for filtering. All that requirement
	 * described in {@link DataTableSearchHolder} instance.
	 * 
	 * @param dataTableSearchHolder
	 *            {@link DataTableSearchHolder} describe current dataTable
	 *            requirements.
	 * @return int count of books based on current dataTable requirements.
	 */
	int findCountBooksForDataTable(DataTableSearchHolder dataTableSearchHolder);

	/**
	 * This method returns Books from database by {@link Book} property book name,
	 * that starts with value passed by parameter. Method looking for books,
	 * which name starts with {@code value}.
	 * 
	 * @param value
	 *            String
	 * @return List
	 */
	List<Book> findBooksByBookNameForAutocomplete(String value);

	/**
	 * The method returns Books from dataBase by collection of IDies, passed by
	 * parameter.
	 * 
	 * @param idForBooks
	 *            List of id.
	 * @return List
	 */
	List<Book> findBooksByListId(List<String> idForBooks);

	/**
	 * Method returns Books from database, where average rating for {@link Book}
	 * (based on {@code Review} reviews) equals to {@code rating} value passed
	 * by parameter.
	 * 
	 * @param rating
	 *            Integer value.
	 * @return List of Book.
	 * 
	 * @throws IllegalArgumentException
	 *             if rating not in range between 1 and 5.
	 */
	List<Book> findBooksByRating(Integer rating);

	/**
	 * Method returns count of books, that have average rating for {@link Book}
	 * (based on {@code Review} reviews) equals to {@code rating} value passed
	 * by parameter.
	 * 
	 * @param rating
	 * @return
	 * @throws IllegalArgumentException
	 *             if rating not in range between 1 and 5.
	 */
	Integer findCountBooksByRating(Integer rating);

}
