package com.softserveinc.model.manager;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.facade.BookFacadeLocal;
import com.softserveinc.model.persist.facade.ReviewFacadeRemote;
import com.softserveinc.model.persist.home.BookHomeLocal;

@Stateless
public class BookManager implements BookManagerLocal, BookManagerRemote {
	
	private BookHomeLocal bookHomeLocal;
	
	
	public BookHomeLocal getBookHomeLocal() {
		return bookHomeLocal;
	}

	@EJB
	public void setBookHomeLocal(BookHomeLocal bookHomeLocal) {
		this.bookHomeLocal = bookHomeLocal;
	}

	@Override
	public List<Book> getAllBooks() {
		System.out.println("getAllBooks() {    BookManager");
		System.out.println("getAllBooks() {    BookManager");
		System.out.println("getAllBooks() {    BookManager");
		
		System.out.println("getAllBooks() {    BookManager");
		System.out.println("getAllBooks() {    BookManager");
		System.out.println("getAllBooks() {    BookManager");
		return bookHomeLocal.findAll();
	}

	@Override
	public Book getBookByID(String id) {
		// TODO Auto-generated method stub
		return bookHomeLocal.findByID(id);
	}

}
