package com.softserveinc.action.managebook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.action.util.DataTableHelper;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.BookColumnsEnum;
import com.softserveinc.model.persist.entity.BookConstantsHolder;
import com.softserveinc.model.persist.facade.BookFacadeLocal;
import com.softserveinc.model.session.manager.BookManagerLocal;

@ManagedBean(name = "manageBookBean")
@SessionScoped
public class ManageBookBean extends DataTableHelper<BookUIWrapper> implements Serializable {

	private static Logger log = LoggerFactory.getLogger(ManageBookBean.class);

	public static BookColumnsEnum bookEnum = BookColumnsEnum.BOOK_BUSINESS_VIEW;
	
	public BookConstantsHolder getConstant(String column) {
		return BookConstantsHolder.getConsant(column);
	}

	@EJB
	BookManagerLocal bookManager;

	@EJB
	BookFacadeLocal bookFacade;

	public ManageBookBean() {
		super();
	}

	public BookColumnsEnum getBookEnum() {
		return bookEnum;
	}

	@Override
	public void getEntitiesForCurrentPage() {
		List<Book> booksList = bookFacade.findBooksForDataTable(getCurrentRequirementsForDataTable());
		cleanListEntities();
		for (Book b : booksList) {
			getEntities().add(new BookUIWrapper(b));
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
	public List<String> getColumns() {
		List<String> list = new ArrayList<String>();
		System.out.println(bookEnum.bookName);
		list.add(bookEnum.bookName);
		list.add(bookEnum.publisher);
		list.add(bookEnum.isbn);
		list.add(bookEnum.yearPublished);
		list.add(bookEnum.rating);
		list.add(bookEnum.authors);
		return list;
	}

	@Override
	public void selectAllAction() {
		System.out.println("select all action");
		for (BookUIWrapper b : getEntities()) {
			if (getSelectAll()) {
				b.setSelected(true);
			} else {
				b.setSelected(false);
			}

		}
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
		for (BookUIWrapper bookUI : getEntities()) {
			if (bookUI.isSelected()) {
				list.add(bookUI.getBook());
			}
		}
		bookManager.deleteListBooks(list);
		refreshPage();

	}
}
