package com.softserveinc.booklibrary.action.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.booklibrary.action.util.BookUIWrapper;
import com.softserveinc.booklibrary.action.util.DataTableHelper;
import com.softserveinc.booklibrary.action.util.ValidateISBN;
import com.softserveinc.booklibrary.exception.BookCatalogException;
import com.softserveinc.booklibrary.exception.BookManagerException;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.session.manager.BookManagerLocal;
import com.softserveinc.booklibrary.session.manager.impl.BookManager;
import com.softserveinc.booklibrary.session.persist.facade.BookFacadeLocal;
import com.softserveinc.booklibrary.session.util.holders.BookFieldHolder;
import com.softserveinc.booklibrary.session.util.holders.EntityFieldHolder;

/**
 * This class is a action bean for UI page manageBooks. Class extended from
 * DataTableHelper, which provides operations with dataTable: pagination,
 * filtering, sorting. Class used for loading Books for dataTable in according
 * to current UI requirements, removing and bulk removing Books.
 *
 */
@ManagedBean
@SessionScoped
public class ManageBookAction extends DataTableHelper<BookUIWrapper> implements Serializable, ValidateISBN {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5789658216108827517L;

	private static Logger log = LoggerFactory.getLogger(ManageBookAction.class);

	// book for create action
	private Book book;

	@EJB
	BookManagerLocal bookManager;

	@EJB
	BookFacadeLocal bookFacade;

	public ManageBookAction() {
		super();
		book = new Book();
	}

	@Override
	public void validateIfExistISBN(FacesContext context, UIComponent comp, Object value) {
		log.debug("Method start. Passed value to validate equals {}", value.toString());
		String val = (String) value;
		Book book = bookFacade.findBookByISNBN(val);
		if (book != null) {
			((UIInput) comp).setValid(false);
			FacesMessage message = new FacesMessage("ISBN already in use");
			context.addMessage(comp.getClientId(context), message);
			log.debug("Method finished. Passed value is not valid.");
		} else {
			log.debug("Method finished. Passed value is valid.");
		}
	}

	/**
	 * Method runs on add action. Method passed {@link Book} instance to
	 * {@link BookManager} for creation a new Book instance. After method done
	 * on manager and new instance is created, method shown message on UI about
	 * it.
	 * 
	 */
	public void submit() {
		try {
			bookManager.createBook(book);
			showGlobalMessageOnPage("Book has been successfully created.");
			reset();
			load();
			log.debug("Book instance has been created.");
		} catch (BookManagerException e) {
			showGlobalMessageOnPage(e.getMessage());
			log.error(e.getMessage());

		}
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
	 * Method runs on add action. Method passed {@link Book} instance to
	 * {@link BookManager} for creation a new Book instance. After method done
	 * on manager and new instance is created, method shown message on UI about
	 * it.
	 * 
	 * @return If Book instance has been created return URL for bookDetail page
	 *         for editing this book.
	 * @throws BookManagerException
	 */
	public String submitAndEdit() {
		try {
			bookManager.createBook(book);
			String idBook = book.getIdBook();
			reset();

			showGlobalMessageOnPage("Book has been successfully created.");
			load();
			log.debug("Book instance has been created.");
			return "bookDetails.xhtml?faces-redirect=true&amp;idBook=" + idBook + "&amp;action=edit";
		} catch (BookManagerException e) {
			showGlobalMessageOnPage(e.getMessage());
			return null;
		}

	}

	/**
	 * Method runs on action clean. Method cleans all inputed values in form.
	 */
	public void reset() {
		book = new Book();
		log.debug("Method done.");
	}

	public void getBooksByRating(Integer rating) {
		cleanFilters();
		getFilterValues().put(BookFieldHolder.RATING, String.valueOf(rating));
		refreshPage();
	}

	@Override
	public EntityFieldHolder[] getEntityConstantInstances() {
		return BookFieldHolder.values();
	}

	@Override
	public void deleteEmptyFilterValues() {
		for (Iterator<Map.Entry<EntityFieldHolder, String>> it = getFilterValues().entrySet().iterator(); it
				.hasNext();) {
			Map.Entry<EntityFieldHolder, String> entry = it.next();
			if (entry.getKey().equals(BookFieldHolder.RATING) && entry.getValue().equals("0")) {
				it.remove();
			}
			if (entry.getValue().equals("") || entry.getValue().startsWith(" ")) {
				it.remove();
			}
		}
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
		for (BookUIWrapper w : getListEntitiesToDelete()) {
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

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

}
