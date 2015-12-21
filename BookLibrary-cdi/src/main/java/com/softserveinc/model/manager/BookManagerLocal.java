package com.softserveinc.model.manager;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.OrderBy;
import com.softserveinc.model.util.SortableDataModel;

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

}
