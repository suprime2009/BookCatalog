package com.softserveinc.action.managebook;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.facade.AuthorFacadeLocal;
import com.softserveinc.model.persist.facade.BookFacadeLocal;
import com.softserveinc.model.session.manager.BookManagerLocal;

@ManagedBean
@RequestScoped
public class CreateBookAction {
	
	private String bookName;
	private String publisher;
	private Integer yearPublished;
	private String isbn;
	

	
	
	private boolean createDone;
	
	public boolean getCreateDone() {
		return createDone;
		
	}
	
	private Book bookAfterCreate = null;
	

	
	@EJB
	private AuthorFacadeLocal authorFacade;
	
	@EJB
	private BookManagerLocal bookManager;
	
	@EJB
	private BookFacadeLocal bookfacade;
	
	public CreateBookAction() {
	

		System.out.println("BEAN CREATED");
	}
	
	@PreDestroy
	public void destroy() {
		System.out.println("BEAN DESTROYED");
	}
	
	public void validateISBN(FacesContext context, UIComponent comp,
            Object value) {
		 System.out.println("inside validate method");
		 String val = (String) value;
		 Book book = bookfacade.findBookByISNBN(val);
		 if (book != null) {
			 ((UIInput) comp).setValid(false);
			 
	            FacesMessage message = new FacesMessage(
	                    "ISBN already in use");
	            context.addMessage(comp.getClientId(context), message);
		 }
	}
		
	public void submit() {
		System.out.println("SUBMIT");
		System.out.println(bookName);
		System.out.println(publisher);
		System.out.println(yearPublished);
		System.out.println(isbn);
		Book book = new Book(bookName, isbn, publisher, yearPublished, null);
		bookAfterCreate = bookManager.createBook(book);
		if (bookAfterCreate != null) {
			createDone = true;
		}				
	}

	
	public void reset() {
		System.out.println("RESET");
		bookName = null;
		publisher = null;
		yearPublished = null;
		isbn = null;
		bookAfterCreate = null;
		System.out.println("RESET");
	}
	
	public String getBookName() {
		return bookName;
	}
	
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Integer getYearPublished() {
		return yearPublished;
	}

	public void setYearPublished(Integer yearPublished) {
		this.yearPublished = yearPublished;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}



	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

}
