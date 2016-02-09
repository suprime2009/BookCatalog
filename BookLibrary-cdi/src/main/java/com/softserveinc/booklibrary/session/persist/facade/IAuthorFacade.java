package com.softserveinc.booklibrary.session.persist.facade;

import java.util.List;

import com.softserveinc.booklibrary.action.helper.DataTableSearchHolder;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.model.entity.Review;


/**
 * {@code IAuthorFacade} an interface that describes all facade (read)
 * operations for {@link Author} entity.
 *
 */
public interface IAuthorFacade {

	public static final String PERSISTANCE_UNIT_PRIMARY = "primary";

	/**
	 * The method gets {@link Author} from database by his first name and second
	 * name.
	 * 
	 * @param firstName
	 *            String
	 * @param secondName
	 *            String
	 * @return author {@link Author} instance.
	 */
	Author findAuthorByFullName(String firstName, String secondName);

	/**
	 * The method gets author by id number from database.
	 * 
	 * @param id
	 *            number in database.
	 * @return Author.
	 */
	Author findById(String id);

	/**
	 * The method gets all authors from database.
	 * 
	 * @return List<Author>.
	 */
	List<Author> findAll();

	/**
	 * The method looking for {@link Author} whose firstName or secondName
	 * starts with {@code prefix} passed by parameter. if author firstName or
	 * author secondName starts with {@code prefix} method builds {@code String}
	 * fullName value separated by writespace, like "secondName firstName". That
	 * value added to List and returned by method. List are limited and returns
	 * result can't be more that 5.
	 * 
	 * @param prefix
	 *            String
	 * @return List
	 */
	List<String> findAuthorsFullNamesForAutocomplete(String prefix);

	/**
	 * Method gets {@code List<Author>} from database based on current dataTable
	 * requirements. DataTable requirements are a: number of first row in DB,
	 * count rows in result, name of Author field for sorting and Sort order,
	 * names Author fields and values for filtering. All that requirement
	 * described in {@link DataTableSearchHolder} instance. Based on this data
	 * method returns List of {@link Author}. Method returns {@code List
	 * <Object[]>}, that include Authors, their average rating, count their
	 * books and count of review on their books.
	 * 
	 * @param dataTableSearchHolder
	 *            {@link DataTableSearchHolder} describe current dataTable
	 *            requirements.
	 * @return List of Object[]
	 */
	List<Object[]> findAuthorsForDataTable(DataTableSearchHolder dataTableSearchHolder);

	/**
	 * Method gets count of authors from database based on current dataTable
	 * requirements. DataTable requirements are a: number of first row in DB,
	 * count rows in result, name of Author field for sorting and Sort order,
	 * names Author fields and values for filtering. All that requirement
	 * described in {@link DataTableSearchHolder} instance.
	 * 
	 * @param dataTableSearchHolder
	 *            {@link DataTableSearchHolder} describe current dataTable
	 *            requirements.
	 * @return int count of authors based on current dataTable requirements.
	 */
	int findCountAuthorsForDataTable(DataTableSearchHolder dataTableSearchHolder);

	/**
	 * The method finds average rating for authors, passed by parameter.
	 * Calculation of this rating based on rating of {@link Review} reviews for
	 * books, the authors of which they are.
	 * 
	 * 
	 * @param author
	 *            {@link Author}
	 * @return Double average rating.
	 */
	Double findAuthorAvegareRating(Author author);

	/**
	 * The method gets Authors from dataBase by collection of IDies, passed by
	 * parameter.
	 * 
	 * @param idForAuthor
	 *            List of id.
	 * @return List
	 */
	List<Author> findAuthorsByListId(List<String> idForAuthor);

	/**
	 * The method looking for authors who are authors of the book {@link Book},
	 * passed by parameter. Method returns List of Authors.
	 * 
	 * @param book
	 *            entity
	 * @return List.
	 */
	List<Author> findAllAuthorsByBook(Book book);
	
	List<Author> findLatestAddedAuthors(int limit);
	


}
