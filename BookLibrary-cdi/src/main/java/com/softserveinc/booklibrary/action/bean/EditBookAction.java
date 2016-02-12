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

import com.softserveinc.booklibrary.action.helper.ValidateISBN;
import com.softserveinc.booklibrary.exception.BookCatalogException;
import com.softserveinc.booklibrary.exception.BookManagerException;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.session.manager.BookManagerLocal;
import com.softserveinc.booklibrary.session.persist.facade.AuthorFacadeLocal;
import com.softserveinc.booklibrary.session.persist.facade.BookFacadeLocal;

/**
 * This bean is a action for edit book operation.
 *
 */
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

	public EditBookAction() {
		log.debug("Bean has bean created.");
	}

	/**
	 * This method is autocomplete action on UI. By passed value, method looking
	 * for authors whose first name or second name starts with this value.
	 * 
	 * @param prefix
	 *            String
	 * @return List of author names
	 */
	public List<String> autocomplete(String prefix) {
		List<String> list = authorFacade.findAuthorsFullNamesForAutocomplete(prefix);
		log.info("Method done. Found {} rows.", list.size());
		return list;
	}

	/**
	 * The method gets a value with author name, parse this value and looking
	 * for authors with entered name. If author found method adds author to
	 * temporal collection which used for editing books. If author has not
	 * found, method shows message on UI about it.
	 */
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
			author = authorFacade.findAuthorByFullName(firstName, secondName);
			if (author == null) {
				message = "The author has not been found.";
				context.addMessage(comp.getClientId(context), new FacesMessage(message));
				log.debug("Author by firstName= {}, secondName= {} has not found.", secondName, firstName);
			} else {
				message = "The author has been found.";
				context.addMessage(comp.getClientId(context), new FacesMessage(message));
				authors.add(author);
				authorFullName = "";
				log.debug("Author by firstName= {}, secondName= {} has been found and added for book.", secondName,
						firstName);
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
			FacesMessage message = new FacesMessage("ISBN already in use");

			context.addMessage(comp.getClientId(context), message);
			log.debug("Method finished. ISBN number= {} already exist in database.");
		}
		log.debug("Method finished. ISBN number= {} is avaible.");
	}

	/**
	 * This method removes author for from collection for book. The collection
	 * is a collection of authors for a book, which is editing.
	 * 
	 * @param event
	 *            ActionEvent
	 */
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

	/**
	 * This method loads book for edit by id by Action event.
	 */
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

	/**
	 * This method loads book for edit by id passed from UI by parameter.
	 */
	public void loadBookForEdit() {
		FacesContext context = FacesContext.getCurrentInstance();
		book = bookFacade.findById((String) context.getExternalContext().getRequestParameterMap().get("idBook"));
		isbnBefore = book.getIsbn();
		if (book.getAuthors() != null) {
			authors = book.getAuthors();
		} else {
			authors = new HashSet<Author>();
		}
		log.info("The method done. Book {} has been loaded.", book);
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

	/**
	 * The method cleans all input fields and gets actual book.
	 */
	public void reset() {
		book = bookFacade.findById(book.getIdBook());
		isbnBefore = book.getIsbn();
		authors = book.getAuthors();
		authorFullName = "";
		log.debug("The method done.");
	}

	/**
	 * The method is executed on edit action for book. The method calls update
	 * method on {@code BookManager}. If the book has been successfully updated,
	 * the method will show message on UI about it. Otherwise will be displayed
	 * the message that review hasn't been updated.
	 */
	public void submit() {
		book.setAuthors(authors);
		try {
			bookManager.updateBook(book);
			reset();
			showGlobalMessageOnPage("Book has been successfully updated.");
		} catch (BookManagerException e) {
			showGlobalMessageOnPage(e.getMessage());
		}
		authorFullName = "";
		log.debug("Method done. Book {} has been updated.", book.toString());
	}

	// getters and setters /
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