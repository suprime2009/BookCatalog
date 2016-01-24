package com.softserveinc.action.managebook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.apache.commons.collections.*;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.softserveinc.exception.AuthorManagerException;
import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.facade.AuthorFacadeLocal;
import com.softserveinc.model.persist.facade.BookFacadeLocal;
import com.softserveinc.model.session.manager.AuthorManagerLocal;

@ManagedBean(name = "authorDetailAction")
@ViewScoped
public class AuthorDetailAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8458126858022646610L;

	@EJB
	private AuthorFacadeLocal authorFacade;

	@EJB
	private AuthorManagerLocal authorManager;

	@EJB
	private BookFacadeLocal bookFacade;

	private String selectedId;
	private Author author;
	private Author authorForEdit;
	private String autocompleteBooks;
	private List<Book> avaibleBooks;

	public AuthorDetailAction() {

	}

	public void lookUpForBooks(ValueChangeEvent event) {
		List<Book> autocompleteList = bookFacade.findBooksByBookNameForAutocomplete((String) event.getNewValue());
		avaibleBooks = ListUtils.subtract(autocompleteList, authorForEdit.getBooks());
	}

	public Double getAverageRating() {
		return authorFacade.findAuthorAvegareRating(author);
	}

	public void loadAuthor() {
		author = authorFacade.findById(selectedId);
		authorForEdit = author;
		avaibleBooks = Lists.newArrayList();
	}

	public void submitEdit() {
		try {
			authorManager.updateAuthor(authorForEdit);
		} catch (AuthorManagerException e) {
			showGlobalMessageOnPage(e.getMessage());
		}
		loadAuthor();
		autocompleteBooks = null;
	}

	public String deleteAuthor() {
		System.out.println("deleteAuthor starts");
		try {
			authorManager.deleteAuthor(author.getIdAuthor());
			return "manageAuthors.xhtml?faces-redirect=true&amp;";
		} catch (AuthorManagerException e) {
			System.out.println(e.getMessage());
			showGlobalMessageOnPage(e.getMessage());
			return null;
		}
	}

	public void reset() {
		loadAuthor();
	}

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

	public Author getAuthorForEdit() {
		return authorForEdit;
	}

	public void setAuthorForEdit(Author authorForEdit) {
		this.authorForEdit = authorForEdit;
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
