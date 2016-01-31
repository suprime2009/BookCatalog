package com.softserveinc.booklibrary.action.bean;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.booklibrary.action.util.ValidateISBN;
import com.softserveinc.booklibrary.exception.BookCatalogException;
import com.softserveinc.booklibrary.exception.BookManagerException;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.session.manager.BookManagerLocal;
import com.softserveinc.booklibrary.session.persist.facade.AuthorFacadeLocal;
import com.softserveinc.booklibrary.session.persist.facade.BookFacadeLocal;


@ManagedBean
@ViewScoped
public class EditBookAction implements ValidateISBN {
	
	private static Logger log = LoggerFactory.getLogger(EditBookAction.class);

	private String isbnBefore;
	private Book book;
	private String authorFullName;
	private Set<Author> authors;
	private Author author;
		
	@EJB
	private BookFacadeLocal bookFacade;
	
	@EJB
	private BookManagerLocal bookManager;
	
	@EJB
	private AuthorFacadeLocal authorFacade;
	

	public EditBookAction(){
		log.debug("Bean has bean created.");
	}
	
	public List<String> autocomplete(String prefix) {
        List<String> list = authorFacade.findAuthorsFullNamesForAutocomplete(prefix);
        log.info("Method done. Found {} rows.", list.size());
        return list;
    }
	
	public void checkIfAuthorExist() {
		FacesContext context = FacesContext.getCurrentInstance();
		UIComponent comp = UIComponent.getCurrentComponent(context);
		String message = "";

		try {
			authorFullName = authorFullName.trim();
			int index = StringUtils.indexOfAny(authorFullName, " ");
			if (index == (-1)) {
				message = "Please enter the first and last name of author separated by a space.";
				context.addMessage(comp.getClientId(context), new FacesMessage(message));
				throw new BookCatalogException("Not valid format for author name autocomplete field.");
			}
			String secondName = authorFullName.substring(0, index);
			String firstName = authorFullName.substring(index + 1);
			author = authorFacade.findAuthorByFullName(secondName, firstName);
			if (author == null) {
				message = "Author has not been found.";
				context.addMessage(comp.getClientId(context), new FacesMessage(message));
				log.debug("Author by firstName= {}, secondName= {} has not found.", secondName, firstName);
			} else {
				message = "Author has been found.";
				context.addMessage(comp.getClientId(context), new FacesMessage(message));
				authors.add(author);
				authorFullName = "";
				log.debug("Author by firstName= {}, secondName= {} has been found and added for book.", secondName, firstName);
			}
		} catch (BookCatalogException e) {
			log.error("Passed value {} not valid. {}", authorFullName, e.getMessage());
		}
	}
	
	@Override
	public void validateIfExistISBN(FacesContext context, UIComponent comp, Object value) {
		String val = (String) value;
		 Book book = bookFacade.findBookByISNBN(val);
		 if (book != null && !isbnBefore.equals(val)) {
			 ((UIInput) comp).setValid(false);
	            FacesMessage message = new FacesMessage(
	                    "ISBN already in use");

	            context.addMessage(comp.getClientId(context), message);
	            log.debug("Method finished. ISBN number= {} already exist in database.");
		 }
		 log.debug("Method finished. ISBN number= {} is avaible.");
	}
	
	public void removeAuthor(ActionEvent event) {
		String secondName = (String) event.getComponent().getAttributes().get("secondName");
		String firstName = (String) event.getComponent().getAttributes().get("firstName");
		Iterator it = authors.iterator();
		while (it.hasNext()) {
			Author au = (Author) it.next();
			if (au.getFirstName().equals(firstName) && au.getSecondName().equals(secondName)) {
				it.remove();
			}
		}
		log.debug("Method finished. Author {} {} has been removed from temporary list.", secondName, firstName);
	}
			
	public void loadBookForEdit(ActionEvent event) {
		String bookIdToEdit = (String) event.getComponent().getAttributes().get("bookId");
		book = bookFacade.findById(bookIdToEdit);
		isbnBefore = book.getIsbn();
		if (book.getAuthors() != null) {
			authors = book.getAuthors();
		} else {
			authors = new HashSet<Author>();
		}
		log.debug("Method finished. Book {} has been loaded.", book.toString());
	}
	public void loadBookForEdit() {
		FacesContext context = FacesContext.getCurrentInstance();
		String ibBook = (String) context.getExternalContext().getRequestParameterMap().get("idBook");
		book = bookFacade.findById(ibBook);
		isbnBefore = book.getIsbn();
		if (book.getAuthors() != null) {
			authors = book.getAuthors();
		} else {
			authors = new HashSet<Author>();
		}
		log.info("The method done. Book {} has been loaded.", book);
	}
	
	private void showGlobalMessageOnPage(String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage(message);
		context.addMessage(null, facesMessage);
	}
	
	public void reset() {
		book = bookFacade.findById(book.getIdBook());
		isbnBefore = book.getIsbn();
		authors = book.getAuthors();
		authorFullName = "";
		log.debug("The method done.");
	}
	

	public void submit() {
		book.setAuthors(authors);
		try {
			bookManager.updateBook(book);
			isbnBefore = book.getIsbn();
			authors = book.getAuthors();
			showGlobalMessageOnPage("Book has been successfully updated.");
		} catch (BookManagerException e) {
			showGlobalMessageOnPage(e.getMessage());
		}
		authorFullName = "";
		log.debug("Method done. Book {} has been updated.", book.toString());
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	public String getAuthorFullName() {
		return authorFullName;
	}

	public void setAuthorFullName(String authorFullName) {
		this.authorFullName = authorFullName;
	}

	
}
