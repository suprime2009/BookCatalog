package com.softserveinc.action.managebook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.exception.BookCatalogException;
import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.facade.AuthorFacade;
import com.softserveinc.model.persist.facade.AuthorFacadeLocal;
import com.softserveinc.model.persist.facade.BookFacadeLocal;
import com.softserveinc.model.session.manager.BookManagerLocal;

@ManagedBean
@ViewScoped
public class EditBookAction implements ValidateISBN{
	
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
        log.debug("Method done. Found {} rows.", list.size());
        return list;
    }
	
	public void checkIfAuthorExist() {
		FacesContext context = FacesContext.getCurrentInstance();
		UIComponent comp = UIComponent.getCurrentComponent(context);

		try {
			authorFullName = authorFullName.trim();
			int index = StringUtils.indexOfAny(authorFullName, " ");
			if (index == (-1)) {
				context.addMessage(comp.getClientId(context), new FacesMessage("Please enter the first and last name separated by a space."));
				throw new BookCatalogException("Not valid format for author name autocomplete field.");
			}
			String secondName = authorFullName.substring(0, index);
			String firstName = authorFullName.substring(index + 1);
			author = authorFacade.findAuthorByFullName(secondName, firstName);
			if (author == null) {
				context.addMessage(comp.getClientId(context), new FacesMessage("Author has not been found."));
				log.debug("Author by firstName= {}, secondName= {} has not found.", secondName, firstName);
			} else {
				context.addMessage(comp.getClientId(context), new FacesMessage("Author has been found."));
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
	

	public void submit() {
		book.setAuthors(authors);
		bookManager.updateBook(book);
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
