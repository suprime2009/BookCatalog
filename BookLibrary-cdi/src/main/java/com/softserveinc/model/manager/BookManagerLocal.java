package com.softserveinc.model.manager;

import java.util.List;

import javax.ejb.Local;

import com.softserveinc.model.persist.entity.Book;

@Local
public interface BookManagerLocal {
	
	List<Book> getAllBooks() ;

}
