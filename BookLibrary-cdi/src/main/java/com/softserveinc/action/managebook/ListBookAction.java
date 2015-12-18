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

	@EJB
	private BookManagerLocal bookManager;
	
	@EJB
	private ReviewManagerLocal reviewManager;
	

	


//	@EJB
//	public void setBookManager(BookManager bookManager) {
//		this.bookManager = bookManager;
//		System.out.println("EJB ACTION DONE");
//		System.out.println("EJB ACTION DONE");
//		System.out.println("EJB ACTION DONE");
//		System.out.println("EJB ACTION DONE");
//		System.out.println("EJB ACTION DONE");
//	}
	
	private List<Book> books;
	private double rating;
	private boolean selected;
	private boolean selectAll;
	private Book book;
	
	
	@PostConstruct
	public void init() {
		books = bookManager.getAllBooks();
	}
	
//	public void setAverageRatingFoRBook(Book book) {
//		this.averageRatingFoRBook = bookManager.getAverageRatingFoRBook(book);
//	}
	


	public List<Book> getBooks() {
		return books;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public double stars(String bookId) {
		Book book = bookManager.getBookByID(bookId);
		return reviewManager.getAverageRating(book);
	}
	
	 public String getCurrentTime() {

	      return new SimpleDateFormat("dd MMM yyyy:HH:mm:SS").format(new java.util.Date().getTime()); // Older version, SimpleDateFormat is not thread safe
	      //return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM-yyyy HH:mm:ss"));  // Requires Java 8
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
