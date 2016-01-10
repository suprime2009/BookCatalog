package com.softserveinc.action.managebook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
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
public class EditBookAction {
	
	private String idBook;
	private Book book;
	
	private String firstName;
	private Set<Author> authors;
	private String secondName;
	private Author author;
	
	private List<Author> authorAutocomplete;
	
	@EJB
	private BookFacadeLocal bookFacade;
	
	@EJB
	private BookManagerLocal bookManager;
	
	@EJB
	AuthorFacadeLocal authorFacade;
	
	public EditBookAction(){
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
	
	public void addAuthor() {
		System.out.println("ADDED FIELD");
		
		authors.add(author);
		System.out.println(firstName);
		System.out.println(secondName);
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
	
	public void loadBook() {
		book = bookFacade.findById(idBook);
		if (book.getAuthors() != null) {
			authors = book.getAuthors();
		} else {
			authors = new HashSet<Author>();
		}
	}
	
	public void submit() {
		book.setAuthors(authors);
		bookManager.updateBook(book);
		System.out.println("sumbit");
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getIdBook() {
		return idBook;
	}

	public void setIdBook(String idBook) {
		this.idBook = idBook;
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
	

	
	

}
