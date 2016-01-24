package com.softserveinc.action.managebook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.action.util.DataTableHelper;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.BookFieldHolder;
import com.softserveinc.model.persist.entity.EntityFieldHolder;
import com.softserveinc.model.persist.facade.BookFacadeLocal;
import com.softserveinc.model.session.manager.BookManagerLocal;

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

		log.info("Meyhod finished.");
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
		bookManager.deleteBook(book);
		load();
		log.info("done");
	}
	

	@Override
	public void deleteListEntities() {
		List<Book> list = new ArrayList<Book>();
		for (BookUIWrapper w: getListEntitiesToDelete()) {
			list.add(w.getBook());
		}
		bookManager.deleteListBooks(list);
		refreshPage();

	}


	@Override
	public EntityFieldHolder getFieldHolderForColumn(String column) {
		return BookFieldHolder.valueOf(column);
	}

}
