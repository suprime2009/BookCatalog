package com.softserveinc.action.managebook;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.softserveinc.model.manager.AuthorManagerLocal;
import com.softserveinc.model.manager.BookManager;
import com.softserveinc.model.manager.BookManagerLocal;
import com.softserveinc.model.manager.ReviewManagerLocal;
import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.home.BookHomeLocal;

@ManagedBean(name = "listBookAction")
@SessionScoped
public class ListBookAction implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -308609312926617997L;

	@Inject
	private BookManagerLocal bookManager;
	
	@Inject
	private ReviewManagerLocal reviewManager;
	

	private List<Book> booksList;
	
	public List<Book> getBooksList() {
		return booksList;
	}
	
	public void sortByBookNameAsk() {
		bookManager.sortByBookNameAsk(booksList);
	}
	
	public void sortByBookNameDesk() {
		bookManager.sortByBookNameDesk(booksList);
	}
	
	@PostConstruct
	public void init() {
		System.out.println("Post Construct ACTION");
		booksList = bookManager.getBooks();
	}
	
	
	private double rating;
	private boolean selected;
	private boolean selectAll;
	private Book book;
	
//	public void setAverageRatingFoRBook(Book book) {
//		this.averageRatingFoRBook = bookManager.getAverageRatingFoRBook(book);
//	}
	




	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}


	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}
	
	
	 
	 
	
	
}
