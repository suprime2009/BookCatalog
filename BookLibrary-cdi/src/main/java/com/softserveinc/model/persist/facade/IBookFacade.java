package com.softserveinc.model.persist.facade;

import java.util.List;

import com.softserveinc.action.managebook.BookUIWrapper;
import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.session.util.DataTableSearchHolder;

/**
 * IBookFacade is an interface that describes all facade methods for Book entity.
 *
 */
public interface IBookFacade {
	
	/**
	 * Method gets books which have rating from review equal to passed in parameter.
	 * @param rating from review.
	 * @return List<Book>.
	 */
	//List<Book> findBooksWithRating(String rating);

	/**
	 * Method gets books from database table by book name.
	 * @param name of book.
	 * @return List<Book> list.
	 */
	List<Book> findBookByName(String name);

	/**
	 * Method gets book from database table by ISBN number.
	 * @param isbn unique book number.
	 * @return Book entity.
	 */
	Book findBookByISNBN(String isbn);

	/**
	 * Method gets books from database table by publisher.
	 * @param publisher of book.
	 * @return List<Book>.
	 */
	List<Book> findBooksByPublisher(String publisher);

	/**
	 * Method gets books of author from database.
	 * @param author Author entity.
	 * @return List<Book>.
	 */
	List<Book> findBooksByAuthor(Author author);
	
	/**
	 * Method gets book by id number from database.
	 * @param id number from database.
	 * @return Book.
	 */
	Book findById(String id);
	
	/**
	 * Method gets all books from database.
	 * @return List<Book>.
	 */
	List<Book> findAll();
	
	/**
	 * Method gets count of all books from database.
	 * @return int count of all books.
	 */
	int findCountAllBooks();
	
	/**
	 * Method gets List<Book> from database based on current dataTable requirements.
	 * @param DataTableSearchHolder dataTableSearchHolder describe current dataTable requirements. 
 	 * @return  List<Book>
	 */
	List<Book> findBooksForDataTable(DataTableSearchHolder dataTableSearchHolder);
	
	/**
	 * Method gets count of books from database based on current dataTable requirements.
	 * @param DataTableSearchHolder dataTableSearchHolder describe current dataTable requirements. 
	 * @return int count of books  based on current dataTable requirements..
	 */
	int findCountBooksForDataTable(DataTableSearchHolder dataTableSearchHolder);

}
