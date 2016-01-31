package com.softserveinc.booklibrary.action.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.booklibrary.action.util.BookUIWrapper;
import com.softserveinc.booklibrary.action.util.DataTableHelper;
import com.softserveinc.booklibrary.exception.BookManagerException;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.session.manager.BookManagerLocal;
import com.softserveinc.booklibrary.session.persist.facade.BookFacadeLocal;
import com.softserveinc.booklibrary.session.util.holders.BookFieldHolder;
import com.softserveinc.booklibrary.session.util.holders.EntityFieldHolder;

/**
 * This class is a action bean for UI page manageBooks.
 * Class extended from DataTableHelper, which provides operations
 * with dataTable: pagination, filtering, sorting. Class used for
 * loading Books for dataTable in according to current UI requirements,
 * removing and bulk removing Books.
 *
 */
@ManagedBean
@SessionScoped
public class ManageBookAction extends DataTableHelper<BookUIWrapper> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5789658216108827517L;

	private static Logger log = LoggerFactory.getLogger(ManageBookAction.class);
	
	
	@EJB
	BookManagerLocal bookManager;

	@EJB
	BookFacadeLocal bookFacade;

	public ManageBookAction() {
		super();
	}
	
	
	public void getBooksByRating(Integer rating) {
		cleanFilters();
		getFilterValues().put(BookFieldHolder.RATING, String.valueOf(rating));
		refreshPage();
	}
	
	@Override
	public EntityFieldHolder [] getEntityConstantInstances() {
		return BookFieldHolder.values();
	}

	@Override
	public void getEntitiesForCurrentPage() {
		List<Book> booksList = bookFacade.findBooksForDataTable(getCurrentRequirementsForDataTable());
		cleanListEntities();
		for (Book b : booksList) {
			BookUIWrapper wrapp = new BookUIWrapper(b);
			getEntities().add(wrapp);
		}

		log.info("Method finished.");
	}

	@Override
	public int getCountEntitiesForCurrentRequirements() {
		int count = bookFacade.findCountBooksForDataTable(getCurrentRequirementsForDataTable());
		log.info("Method finished.");
		return count;
	}

	@Override
	public void deleteEntity() {
		System.out.println("Method DELETE ROW");
		Book book = bookFacade.findById(getIdEntityToDelete());
		try {
			bookManager.deleteBook(book.getIdBook());
		} catch (BookManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		load();
		log.info("done");
	}
	

	@Override
	public void deleteListEntities() {
		List<Book> list = new ArrayList<Book>();
		for (BookUIWrapper w: getListEntitiesToDelete()) {
			list.add(w.getBook());
		}
		try {
			bookManager.bulkDelete(list);
		} catch (BookManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		refreshPage();

	}


	@Override
	public EntityFieldHolder getFieldHolderForColumn(String column) {
		return BookFieldHolder.valueOf(column);
	}

}