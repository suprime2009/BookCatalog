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
import com.softserveinc.model.persist.entity.BookConstantsHolder;
import com.softserveinc.model.persist.entity.EntityConstant;
import com.softserveinc.model.persist.facade.BookFacadeLocal;
import com.softserveinc.model.session.manager.BookManagerLocal;

@ManagedBean(name = "manageBookBean")
@SessionScoped
public class ManageBookBean extends DataTableHelper<BookUIWrapper> implements Serializable {

	private static Logger log = LoggerFactory.getLogger(ManageBookBean.class);
	
	@EJB
	BookManagerLocal bookManager;

	@EJB
	BookFacadeLocal bookFacade;

	public ManageBookBean() {
		super();
	}
	
	@Override
	public EntityConstant getEntityConstantInstance() {
		return BookConstantsHolder.INSTANCE;
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
		for (BookUIWrapper bookUI : getEntities()) {
			if (bookUI.isSelected()) {
				list.add((Book) bookUI.getBook());
			}
		}
		bookManager.deleteListBooks(list);
		refreshPage();

	}

}
