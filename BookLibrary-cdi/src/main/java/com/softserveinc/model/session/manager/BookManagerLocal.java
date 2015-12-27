package com.softserveinc.model.session.manager;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import org.ajax4jsf.model.SequenceRange;
import org.richfaces.component.SortOrder;
import org.richfaces.model.ArrangeableState;

import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.OrderBy;
import com.softserveinc.model.session.util.DataTableHelper;

@Local
public interface BookManagerLocal {
	
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
	
	List<Book> getBookForDataTable(DataTableHelper dataTableHelper);
	

}
