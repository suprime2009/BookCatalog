package com.softserveinc.action.managebook;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
@ViewScoped
public class CreateBookAction {
	
	private String bookName;
	private String publisher;
	private Integer yearPublished;
	private String isbn;
	private Set<Author> authors;
	private String firstName;
	private String secondName;
	private Author author;
	
	private Book bookAfterCreate = null;
	
	private List<Author> authorAutocomplete;
	
	@EJB
	private AuthorFacadeLocal authorFacade;
	
	@EJB
	private BookManagerLocal bookManager;
	
	@EJB
	private BookFacadeLocal bookfacade;
	
	public CreateBookAction() {
		authors = new HashSet<Author>();

		System.out.println("BEAN CREATED");
	}
	
	public List<String> autocompleteSecondName(String prefix) {
		secondName = prefix;
		authorAutocomplete = authorFacade.findAuthorsBySecondName(prefix);
        List<String> result = new ArrayList<String>();
        if ((prefix == null) || (prefix.length() == 0)) {
            for (int i = 0; i < 10; i++) {
                result.add(authorAutocomplete.get(i).getSecondName());
            }
        } else {
            Iterator<Author> iterator = authorAutocomplete.iterator();
            while (iterator.hasNext()) {
                Author elem = ((Author) iterator.next());
                if ((elem.getSecondName() != null && elem.getSecondName().toLowerCase().indexOf(prefix.toLowerCase()) == 0)
                    || "".equals(prefix)) {
                    result.add(elem.getSecondName());
                }
            }
        }
 
        return result;
    }
	
	public List<String> autocompleteFirstName(String prefix) { 
	    List<String> result = new ArrayList<String>();
        if ((prefix == null) || (prefix.length() == 0)) {
            for (int i = 0; i < 10; i++) {
                result.add(authorAutocomplete.get(i).getFirstName());
            }
        } else {
            Iterator<Author> iterator = authorAutocomplete.iterator();
            while (iterator.hasNext()) {
                Author elem = ((Author) iterator.next());
                if ((elem.getFirstName() != null && elem.getFirstName().toLowerCase().indexOf(prefix.toLowerCase()) == 0)
                    || "".equals(prefix)) {
                    result.add(elem.getFirstName());
                }
            }
        }
 
        return result;
	}
	
	public Book getBookAfterCreate() {
		return bookAfterCreate;
	}
		
	public void checkIfAuthorExist(ActionEvent event) throws AbortProcessingException  {
		FacesContext context = FacesContext.getCurrentInstance();
		
		System.out.println(firstName);
		System.out.println(secondName);
        
		 author = authorFacade.findAuthorByFullName(firstName, secondName);
		if (author == null) {
			context.addMessage(null, new FacesMessage("Author not finded"));
		} else {
			context.addMessage(null, new FacesMessage("Author  finded"));
			addAuthor();
		}
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
	
	public void addAuthor() {
		System.out.println("ADDED FIELD");
		
		authors.add(author);
		System.out.println(firstName);
		System.out.println(secondName);
	}
	
	public void submit() {
		System.out.println("SUBMIT");
		System.out.println(bookName);
		System.out.println(publisher);
		System.out.println(yearPublished);
		System.out.println(isbn);
		Book book = new Book(bookName, isbn, publisher, yearPublished, authors);
		bookAfterCreate = bookManager.createBook(book);
		
	}

	
	public void reset() {
		bookName = null;
		publisher = null;
		yearPublished = null;
		isbn = null;
		authors = null;
		firstName = null;
		secondName = null;
		author = null;
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

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

}
