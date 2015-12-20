package com.softserveinc.model.manager;

import java.util.List;

import javax.ejb.Local;

import com.softserveinc.model.persist.entity.Book;

@Local
public interface BookManagerLocal {
	
	Book getBookByID(String id);
	
	List<Book> getBooks();
	
	void sortByBookNameAsk(List<Book> list);
	
	void sortByBookNameDesk(List<Book> list);

}
