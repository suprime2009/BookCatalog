package com.softserveinc.action.managebook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.richfaces.component.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.softserveinc.action.util.PaginationHelper;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.BookColumnsEnum;
import com.softserveinc.model.persist.facade.BookFacadeLocal;
import com.softserveinc.model.session.manager.BookManagerLocal;
import com.softserveinc.model.session.util.DataTableSearchHolder;

@ManagedBean(name = "manageBookBean")
@SessionScoped
public class ManageBookBean extends PaginationHelper<Book> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -512489312362273643L;

	private static Logger log = LoggerFactory.getLogger(ManageBookBean.class);

	private List<BookUIWrapper> books;
	private Map<String, SortOrder> sortOrders = Maps.newHashMapWithExpectedSize(1);
	private Map<String, String> filterValues = Maps.newHashMap();
	private String sortProperty;
	private boolean selectAll;
	
	private String bookIdToDelete;
	
	

	public String getBookIdToDelete() {
		return bookIdToDelete;
	}

	public void setBookIdToDelete(String bookIdToDelete) {
		System.out.println("bookIdToDelete SETTER" + bookIdToDelete);
		this.bookIdToDelete = bookIdToDelete;
	}

	private BookColumnsEnum bookEnum = BookColumnsEnum.BOOK_BUSINESS_VIEW;

	@EJB
	BookManagerLocal bookManager;

	@EJB
	BookFacadeLocal bookFacade;
	


	public ManageBookBean() {
		super();
		
	}
	
	@PostConstruct
	public void init() {
		sortOrders.put(bookEnum.bookName, SortOrder.unsorted);
		sortOrders.put(bookEnum.publisher, SortOrder.unsorted);
		sortOrders.put(bookEnum.yearPublished, SortOrder.unsorted);
		sortOrders.put(bookEnum.isbn, SortOrder.unsorted);
		sortOrders.put(bookEnum.rating, SortOrder.unsorted);
		sortOrders.put(bookEnum.authors, SortOrder.unsorted);


	}

	public List<BookUIWrapper> getBooks() {
		log.error("getBooks");
		if (books == null) {
			load();
		}
		return books;
	}

	public boolean getSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	public BookColumnsEnum getBookEnum() {
		return bookEnum;
	}

	public void setBooks(List<BookUIWrapper> books) {
		this.books = books;
	}

	public BookManagerLocal getBookManager() {
		return bookManager;
	}

	public String getSortProperty() {
		log.error("getSortProperty");
		return sortProperty;
	}

	public void setSortProperty(String sortPropety) {
		this.sortProperty = sortPropety;
	}

	public Map<String, SortOrder> getSortOrders() {
		log.error("getSortOrders");
		return sortOrders;
	}

	public void selectAllAction() {
		System.out.println("select all action");
		for (BookUIWrapper b : books) {
			if (selectAll) {
				b.setSelected(true);
			} else {
				b.setSelected(false);
			}

		}

	}

	public Map<String, String> getFilterValues() {
		log.info("Current values for filtering = {}", filterValues.size());
		return filterValues;
	}
	
	public void deleteBook() {
		System.out.println("deleteBook");
		Book book = bookFacade.findById(bookIdToDelete);
		bookManager.deleteBook(book);
		books.remove(new BookUIWrapper(book));
	}

	public void deleteRow(String bookId) {
		System.out.println("Method DELETE ROW");
		Book book = bookFacade.findById(bookId);
		bookManager.deleteBook(book);
		books.remove(new BookUIWrapper(book));
		log.info("done");

	}
	
	public void deleteListBooks() {
		List<Book> list = new ArrayList<Book>();
		Iterator it =books.iterator();
		while(it.hasNext()) {
			BookUIWrapper bookUI = (BookUIWrapper) it.next();
			if (bookUI.isSelected()) {
				list.add((Book) bookUI);
				it.remove();
			}
		}
		bookManager.deleteListBooks(list);
	}

	public void cleanFilters() {
		filterValues.clear();
	}

	private void deleteValueEmptyMapsPairs() {
		for (Iterator<Map.Entry<String, String>> it = filterValues.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = it.next();
			if (entry.getValue().equals("") || entry.getValue().startsWith(" ")) {
				it.remove();
			}
		}
	}

	public void searchAction() {
		deleteValueEmptyMapsPairs();
		load();
	}

	public void toggleSort(String column) {
		log.error("toggleSort");
		sortProperty = column;
		for (Entry<String, SortOrder> entry : sortOrders.entrySet()) {
			SortOrder newOrder;

			if (entry.getKey().equals(sortProperty)) {
				if (entry.getValue() == SortOrder.ascending) {
					newOrder = SortOrder.descending;
				} else {
					newOrder = SortOrder.ascending;
				}
			} else {
				newOrder = SortOrder.unsorted;
			}

			entry.setValue(newOrder);
		}
		load();
	}

	public DataTableSearchHolder getCurrentRequirementsForDataTable() {

		DataTableSearchHolder datatableSearchHolder = new DataTableSearchHolder();
		datatableSearchHolder.setFirstRow(firstRow);
		datatableSearchHolder.setRowsPerPage(rowsPerPage);
		datatableSearchHolder.setSortColumn(sortProperty);
		datatableSearchHolder.setSortOrder(sortOrders.get(sortProperty));
		datatableSearchHolder.setFilterValues(filterValues);

		return datatableSearchHolder;
	}

	@Override
	public void getEntitiesForCurrentPage() {

		deleteValueEmptyMapsPairs();
		List<Book> booksList = bookFacade.findBooksForDataTable(getCurrentRequirementsForDataTable());
		books = new ArrayList<BookUIWrapper>();
		this.books.clear();
		for (Book b : booksList) {
			books.add(new BookUIWrapper(b));
		}
		
		log.info("Meyhod finished.");
	}

	@Override
	public int getCountEntitiesForCurrentRequirements() {

		int count = bookFacade.findCountBooksForDataTable(getCurrentRequirementsForDataTable());
		log.info("Method finished.");
		return count;
	}
}
