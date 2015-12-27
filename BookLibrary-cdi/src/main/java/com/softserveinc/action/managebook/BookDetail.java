package com.softserveinc.action.managebook;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.session.manager.BookManagerLocal;

@ManagedBean
@RequestScoped
public class BookDetail implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2063925001992083561L;

	@EJB
	BookManagerLocal bookManager;
	
	private String selectedId;
	private Book book;
	
	public void loadBook() {
		book = bookManager.getBookByID(selectedId);
	}
	public String getSelectedId() {
		return selectedId;
	}
	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	
	
	

}
