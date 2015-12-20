package com.softserveinc.model.manager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.facade.BookFacadeLocal;
import com.softserveinc.model.persist.home.BookHomeLocal;

@Stateless
public class BookManager implements BookManagerLocal, BookManagerRemote {
	
	public BookManager() {
		System.out.println("This is BokkMANAGER CONSTRUCTOR");
	}
	
	@PreDestroy
	public void preDestroy() {
		System.out.println("destroyed");
	}
	
	
	@EJB
	private BookHomeLocal bookHomeLocal;
	
	@EJB
	private BookFacadeLocal bookFacadeLocal;
	
	public BookFacadeLocal getBookFacadeLocal() {
		return bookFacadeLocal;
	}
	
	public List<Book> getBooks() {
		return bookFacadeLocal.findAll();
	}
	
	public BookHomeLocal getBookHomeLocal() {
		return bookHomeLocal;
	}

	@Override
	public Book getBookByID(String id) {
		return bookHomeLocal.findByID(id);
	}

	@Override
	public void sortByBookNameAsk(List<Book> list) {
		Collections.sort(list, new Comparator<Book>() {
			public int compare(Book b1, Book b2) {
				return b1.getBookName().compareToIgnoreCase(b2.getBookName());
			}
		});
		System.out.println("sorting done! after sortByBookNameAsk() bookManager");
	}

	@Override
	public void sortByBookNameDesk(List<Book> list) {
		Collections.sort(list, new Comparator<Book>() {
			public int compare(Book b1, Book b2) {
				return b2.getBookName().compareToIgnoreCase(b1.getBookName());
			}
		});
		System.out.println("sorting done! after sortByBookNameDesk() bookManager");
		
	}
}
