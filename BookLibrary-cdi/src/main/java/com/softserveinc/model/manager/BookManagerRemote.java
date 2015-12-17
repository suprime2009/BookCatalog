package com.softserveinc.model.manager;

import java.util.List;

import javax.ejb.Remote;

import com.softserveinc.model.persist.entity.Book;

@Remote(BookManagerRemote.class)
public interface BookManagerRemote {
	
	List<Book> getAllBooks() ;

}
