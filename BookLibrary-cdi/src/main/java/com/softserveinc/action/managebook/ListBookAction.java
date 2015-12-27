package com.softserveinc.action.managebook;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.OrderBy;
import com.softserveinc.model.session.manager.BookManagerLocal;

@ManagedBean(name = "listBookAction")
@SessionScoped
public class ListBookAction implements Serializable {

	private static final long serialVersionUID = -308609312926617997L;
	private static Logger log = LoggerFactory.getLogger(ListBookAction.class);

	private List<Book> booksList;

	private boolean sortAscendingBookName = true;
	private boolean sortAscendingRating = true;
	private boolean sortAscendingYearPublished = true;
	private boolean sortAscendingPublisher = true;

	@Inject
	private BookManagerLocal bookManager;
	
	@PostConstruct
	public void init() {
		booksList = bookManager.getAllBooks();
		bookManager.setRatingForBooks(booksList);
//		Book[] array = new Book[booksList.size()];
//		booksList.toArray(array);
//		sortableDataModel = new SortableDataModel<Book>(new ArrayDataModel<Book>());
		log.info("ListBookAction has been created.");
	}

	public List<Book> getBooksList() {
		return booksList;
	}
	

	public void sortByBookName() {
		if (sortAscendingBookName) {
			bookManager.sortByBookName(booksList, OrderBy.ASC);
			sortAscendingBookName = false;
			log.info("Sorting by bookName {} finished", OrderBy.ASC);
		} else {
			bookManager.sortByBookName(booksList, OrderBy.DESC);
			sortAscendingBookName = true;
			log.info("Sorting by bookName {} finished", OrderBy.DESC);
		}

	}

	public void sortByYearPublished() {
		if (sortAscendingYearPublished) {
			bookManager.sortByYearPublished(booksList, OrderBy.ASC);
			sortAscendingYearPublished = false;
			log.info("Sorting by yearPublished {} finished", OrderBy.ASC);
		} else {
			bookManager.sortByYearPublished(booksList, OrderBy.DESC);
			sortAscendingYearPublished = true;
			log.info("Sorting by yearPublished {} finished", OrderBy.DESC);
		}
	}

	public void sortByPublisher() {
		if (sortAscendingPublisher) {
			bookManager.sortByPublisher(booksList, OrderBy.ASC);
			sortAscendingPublisher = false;
			log.info("Sorting by publisher {} finished", OrderBy.ASC);
		} else {
			bookManager.sortByPublisher(booksList, OrderBy.DESC);
			sortAscendingPublisher = true;
			log.info("Sorting by publisher {} finished", OrderBy.DESC);
		}
	}
	
	public void sortByRating() {
		if (sortAscendingRating) {
			bookManager.sortByRating(booksList, OrderBy.ASC);
			sortAscendingRating = false;
			log.info("Sorting by rating {} finished", OrderBy.ASC);
		} else {
			bookManager.sortByRating(booksList, OrderBy.DESC);
			sortAscendingRating = true;
			log.info("Sorting by rating {} finished", OrderBy.DESC);
		}
	}



	public void sortByBookNameAsk(){}
	public void sortByBookNameDesk(){}
	
	private boolean selected;
	private boolean selectAll;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}
	


}
