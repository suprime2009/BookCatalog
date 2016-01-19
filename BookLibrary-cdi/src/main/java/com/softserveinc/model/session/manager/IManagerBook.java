package com.softserveinc.model.session.manager;

import java.util.List;
import java.util.Map;

import org.ajax4jsf.model.SequenceRange;
import org.richfaces.component.SortOrder;
import org.richfaces.model.ArrangeableState;

import com.softserveinc.exception.ReviewManagerException;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.OrderBy;
import com.softserveinc.model.session.util.DataTableSearchHolder;

/**
 * * IManagerBook is an interface that describes all business methods for Book
 * entity.
 *
 */
public interface IManagerBook {

	void setRatingForBooks(List<Book> list);

	void deleteListBooks(List<Book> list);

	Book createBook(Book book);

	void updateBook(Book book);

	/**
	 * This method starts to load books in according to data table requirements.
	 * Method gets in argument instance of {@link DataTableSearchHolder}, which describe the
	 * requirements for query, include current dataTable requirements like first
	 * row, count rows on page, column for sorting by order and columns for
	 * filtering with values. Based on this data, method creates a query for the
	 * database and returns <code>List</code> for current page in dataTable and
	 * count of finding rows to those requirements. That two statements added to
	 * List<Object>.
	 * 
	 * @param  DataTableSearchHolder
	 *            dataTableSearchHolder instance
	 * @return List<Object> 
	 */
	List<Object> getBookForDataTable(DataTableSearchHolder dataTableHelper);

	void deleteBook(Book book);

}
