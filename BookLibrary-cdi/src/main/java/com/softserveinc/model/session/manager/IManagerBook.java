package com.softserveinc.model.session.manager;

import java.util.List;
import java.util.Map;

import org.ajax4jsf.model.SequenceRange;
import org.richfaces.component.SortOrder;
import org.richfaces.model.ArrangeableState;

import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.OrderBy;
import com.softserveinc.model.session.util.DataTableSearchHolder;

/**
 *  * IManagerBook is an interface that describes all business methods for Book entity.
 *
 */
public interface IManagerBook {
	
	Book getBookByID(String id);
	
	List<Book> getAllBooks();
	
	void setRatingForBooks(List<Book> list);
	
	void sortByRating(List<Book> list, final OrderBy order);
	
	void sortByBookName(List<Book> list, final OrderBy order);
	
	void sortByYearPublished(List<Book> list, final OrderBy order);
	
	void sortByPublisher(List<Book> list, final OrderBy order);
	
	void sortByCreatedDate(List<Book> list);
	
	List<Book> getBooksForDataTable( SequenceRange sequenceRange, ArrangeableState arrangeableState, 
			Map<String, SortOrder> sortOrders, Map<String, String> filterValues );
	
	/**
	 * This method starts on load and on change Book DataTable. Method gets in argument instance of 
	 * DataTableHelper, which describe requirements for query, include current dataTable requirements like first row, count rows on page,
	 * column for sorting with order and columns for filtering with values. On this data, method creates a query for 
	 * database and returns List<Book> for current page in dataTable and count of found rows to those  requirements. 
	 * That two statements added to List<Object>. 
	 * @param DataTableSearchHolder dataTableHelper 
	 * @return List<Object>
	 */
	List<Object> getBookForDataTable(DataTableSearchHolder dataTableHelper);
	
	

}
