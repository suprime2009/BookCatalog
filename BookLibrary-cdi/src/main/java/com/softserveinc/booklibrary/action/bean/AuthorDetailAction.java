package com.softserveinc.booklibrary.action.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.commons.collections.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.booklibrary.exception.AuthorManagerException;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.session.manager.AuthorManagerLocal;
import com.softserveinc.booklibrary.session.persist.facade.AuthorFacadeLocal;
import com.softserveinc.booklibrary.session.persist.facade.BookFacadeLocal;

@ManagedBean(name = "authorDetailAction")
@ViewScoped
public class AuthorDetailAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8458126858022646610L;

	private static Logger log = LoggerFactory.getLogger(AuthorDetailAction.class);

	private String selectedId;
	private Author author;
	private String autocompleteBooks;
	private List<Book> avaibleBooks;
	
	private List<Book> books;

	@EJB
	private AuthorFacadeLocal authorFacade;

	@EJB
	private AuthorManagerLocal authorManager;

	@EJB
	private BookFacadeLocal bookFacade;

	public AuthorDetailAction() {
		avaibleBooks = new ArrayList<Book>();
		books = new ArrayList<Book>();
	}

	@SuppressWarnings("unchecked")
	public void lookUpForBooks(ValueChangeEvent event) {
		List<Book> autocompleteList = bookFacade.findBooksByBookNameForAutocomplete((String) event.getNewValue());
		System.out.println(avaibleBooks.size());
		System.out.println("authocomplete list:");
		for (Book b : autocompleteList) {
			System.out.println(b);
		}

		avaibleBooks = ListUtils.sum(author.getBooks(), autocompleteList);

		System.out.println("author books:");
		for (Book b : author.getBooks()) {
			System.out.println(b);
		}
		System.out.println();
		System.out.println("avaible books:");
		for (Book b : avaibleBooks) {
			System.out.println(b);
		}
		log.debug("The method finished. Has beed found {} books", autocompleteList.size());
	}

	public void loadAuthor() {
		author = authorFacade.findById(selectedId);
		autocompleteBooks = null;
		avaibleBooks.clear();
		avaibleBooks.addAll(author.getBooks());
		log.debug("By id={} has been loaded author={}", selectedId, author);
	}

	public void submitEdit() {
		System.out.println("submit edit");
		try {
			authorManager.updateAuthor(author);
			loadAuthor();
			
			log.debug("The method done. Author has been updated.");
		} catch (AuthorManagerException e) {
			showGlobalMessageOnPage(e.getMessage());
			log.error(e.getMessage());
		}

	}

	public String deleteAuthor() {
		try {
			authorManager.deleteAuthor(author.getIdAuthor());
			log.debug("The method done. Author has been removed.");
			return "manageAuthors.xhtml?faces-redirect=true&amp;";
		} catch (AuthorManagerException e) {
			showGlobalMessageOnPage(e.getMessage());
			log.error(e.getMessage());
			return null;
		}
	}

	public void reset() {
		loadAuthor();
	}

	public Double getAverageRating() {
		return authorFacade.findAuthorAvegareRating(author);
	}

	/**
	 * The method shows the global messages on UI. The method gets the text of
	 * message through parameter.
	 * 
	 * @param message
	 *            String
	 */
	private void showGlobalMessageOnPage(String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage(message);
		context.addMessage(null, facesMessage);
	}

	// getters and setters
	public String getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getAutocompleteBooks() {
		return autocompleteBooks;
	}

	public void setAutocompleteBooks(String autocompleteBooks) {
		this.autocompleteBooks = autocompleteBooks;
	}

	public List<Book> getAvaibleBooks() {
		return avaibleBooks;
	}

	public void setAvaibleBooks(List<Book> avaibleBooks) {
		this.avaibleBooks = avaibleBooks;
	}

}
