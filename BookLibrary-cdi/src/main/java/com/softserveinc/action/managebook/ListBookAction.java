package com.softserveinc.action.managebook;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.softserveinc.model.manager.BookManager;
import com.softserveinc.model.manager.BookManagerLocal;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.home.BookHomeLocal;

@ManagedBean(name = "allBooks")
@SessionScoped
public class ListBookAction {
	
	private BookManagerLocal bookManager;

	@EJB
	public void setBookManager(BookManagerLocal bookManager) {
		this.bookManager = bookManager;
		System.out.println("EJB ACTION DONE");
		System.out.println("EJB ACTION DONE");
		System.out.println("EJB ACTION DONE");
		System.out.println("EJB ACTION DONE");
		System.out.println("EJB ACTION DONE");
	}
	
	private List<Book> list;
	
	@PostConstruct
	public void init() {
		list = bookManager.getAllBooks();
		System.out.println("INIT DONE");
		System.out.println("INIT DONE");
		System.out.println("INIT DONE");
		System.out.println("INIT DONE");
		System.out.println("INIT DONE");
	}

	public List<Book> getList() {
		return list;
	}

	public void setList(List<Book> list) {
		this.list = list;
	}
	
	
	
	
	

}
