package com.softserveinc.booklibrary.action.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

/**
 * This class is a action bean for UI page authorDetails page. The action bean
 * provides operations for showing author details on page, average author
 * rating. Also this bean provides possibility for editing authors.
 *
 */
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

 private List<Book> booksForEdit;

 @EJB
 private AuthorFacadeLocal authorFacade;

 @EJB
 private AuthorManagerLocal authorManager;

 @EJB
 private BookFacadeLocal bookFacade;

 public AuthorDetailAction() {
  avaibleBooks = new ArrayList<Book>();
  booksForEdit = new ArrayList<Book>();
 }

 /**
  * This method is {@link ValueChangeEvent} action. Method used for looking
  * for book by entered value. By this value method looking for books, which
  * names starts with this value.
  * 
  * @param event
  *            {@link ValueChangeEvent}
  */
 @SuppressWarnings("unchecked")
 public void lookUpForBooks(ValueChangeEvent event) {
  List<Book> autocompleteList = bookFacade.findBooksByBookNameForAutocomplete((String) event.getNewValue());
  avaibleBooks = ListUtils.sum(booksForEdit, autocompleteList);
  log.debug("The method finished. Has beed found {} books", autocompleteList.size());
 }

 /**
  * The method loads Author by passed id.
  */
 public void loadAuthor() {
  author = authorFacade.findById(selectedId);
  booksForEdit = bookFacade.findBooksByAuthor(author);
  autocompleteBooks = null;
  avaibleBooks.clear();
  avaibleBooks.addAll(booksForEdit);
  log.debug("By id={} has been loaded author={}", selectedId, author);
 }

 /**
  * The method is executed on edit action for author. The method calls update
  * method on {@code AuthorManager}. If the author has been successfully
  * updated, the method will show message on UI about it. Otherwise will be
  * displayed the message that author hasn't been updated.
  */
 public void submitEdit() {
  try {
   author.setBooks(booksForEdit);
   authorManager.updateAuthor(author);
   loadAuthor();
   log.debug("The method done. Author has been updated.");
  } catch (AuthorManagerException e) {
   showGlobalMessageOnPage(e.getMessage());
   log.error(e.getMessage());
  }

 }

 /**
  * The method is executed on delete action for author. The method calls
  * delete method on {@code AuthorManger}. The method deletes author with all
  * communications with him book.
  *
  */
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

 /**
  * The method resets input fields for editing.
  */
 public void reset() {
  loadAuthor();
 }

 /**
  * The method returns average rating for Author.
  * 
  * @return
  */
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

 public List<Book> getBooksForEdit() {
  return booksForEdit;
 }

 public void setBooksForEdit(List<Book> booksForEdit) {
  this.booksForEdit = booksForEdit;
 }

}