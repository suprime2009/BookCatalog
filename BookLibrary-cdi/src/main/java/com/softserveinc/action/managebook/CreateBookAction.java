package com.softserveinc.action.managebook;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class CreateBookAction implements ValidateISBN{
	
	private Book book;
	private boolean createDone;
	private Book bookAfterCreate;
	
	public boolean getCreateDone() {
		System.out.println("getCreateDone");
		return createDone;	
	}
	
	@EJB
	private BookManagerLocal bookManager;
	
	@EJB
	private BookFacadeLocal bookfacade;
	
	public CreateBookAction() {
	book = new Book();
	}
	
	@PreDestroy
	public void destroy() {
		System.out.println("BEAN DESTROYED");
	}
	
	@Override
	public void validateISBN(FacesContext context, UIComponent comp,
            Object value) {
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
		bookAfterCreate = bookManager.createBook(book);
		if (bookAfterCreate != null) {
			createDone = true;
		}				
	}
	
	public String submitAndEdit() {
		System.out.println("submitAndEdit");
		bookAfterCreate = bookManager.createBook(book);
		if (bookAfterCreate != null) {
			return "editBook.xhtml?faces-redirect=true&amp;includeViewParams=true&id=" + bookAfterCreate.getIdBook() ;
		} else {
			return "";
		}		
	}

	public void reset() {
		System.out.println("RESET");
		book = new Book(); 
		bookAfterCreate = null;
	}

	public Book getBook() {
		return book;
	}
	
	public Book getBookAfterCreate() {
		System.out.println("getBookAfterCreate");
		
		return bookAfterCreate;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	public boolean isDisableParam() {
		System.out.println("isDisableParam");
		if (bookAfterCreate == null) {
			return true;
		} else {
			return false;
		}
	}
	
	
	


}
