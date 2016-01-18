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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.facade.AuthorFacadeLocal;
import com.softserveinc.model.persist.facade.BookFacadeLocal;
import com.softserveinc.model.session.manager.BookManagerLocal;

/**
 * Class is a action bean for creation operation for Book entity.
 * 
 *
 */
@ManagedBean
@ViewScoped
public class CreateBookAction implements ValidateISBN {

	private static Logger log = LoggerFactory.getLogger(CreateBookAction.class);

	private Book book;
	private boolean createDone;
	private String idBookAfterCreate;

	public boolean getCreateDone() {
		return createDone;
	}

	@EJB
	private BookManagerLocal bookManager;

	@EJB
	private BookFacadeLocal bookfacade;

	public CreateBookAction() {
		book = new Book();
		log.info("Action bean has been created.");
	}

	@PreDestroy
	public void destroy() {
		log.debug("Action bean has been destroyed.");
	}

	@Override
	public void validateIfExistISBN(FacesContext context, UIComponent comp, Object value) {
		log.debug("Method start. Passed value to validate equals {}", value.toString());
		String val = (String) value;
		Book book = bookfacade.findBookByISNBN(val);
		if (book != null) {
			((UIInput) comp).setValid(false);
			FacesMessage message = new FacesMessage("ISBN already in use");
			context.addMessage(comp.getClientId(context), message);
			log.debug("Method finished. Passed value is not valid.");
		} else {
			log.debug("Method finished. Passed value is valid.");
		}
	}

	/**
	 * Method runs on add action. Method passed {@link Book} instance to
	 * {@link BookManager} for creation a new Book instance. After method done
	 * on manager, it returns new Book instance after persist. If persist
	 * successful this method sets {@value createDone} to true.
	 * 
	 */
	public void submit() {
		Book bookAfterCreate = bookManager.createBook(book);
		idBookAfterCreate = bookAfterCreate.getIdBook();
		if (bookAfterCreate != null) {
			createDone = true;
			log.info("Book instance has been successfully created.");
		} else {
			log.info("Book instance has not been created.");
		}
	}

	/**
	 * Method runs on add action. Method passed {@link Book} instance to
	 * {@link BookManager} for creation a new Book instance. After method done
	 * on manager, it returns new Book instance after persist. If persist
	 * successful this method sets {@value createDone} to true.
	 * 
	 * @return If Book instance has been created return URL for editBook page.
	 */
	public void submitAndEdit() {
		Book bookAfterCreate = bookManager.createBook(book);
		idBookAfterCreate = bookAfterCreate.getIdBook();
		if (bookAfterCreate != null) {
			log.debug("Book instance has been successfully created.");
		} else {
			log.debug("Book instance has not been created.");
		}
	}

	/**
	 * Method runs on action clean. Method cleans all inputed values in form.
	 */
	public void reset() {
		book = new Book();
		idBookAfterCreate = null;
		log.debug("Method done.");
	}

	public Book getBook() {
		return book;
	}

	public String getIdBookAfterCreate() {
		return idBookAfterCreate;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public boolean isDisableParam() {
		System.out.println("isDisableParam");
		if (idBookAfterCreate == null) {
			return true;
		} else {
			return false;
		}
	}

}
