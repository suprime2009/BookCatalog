package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.richfaces.component.SortOrder;
import org.richfaces.model.ArrangeableState;
import org.richfaces.model.FilterField;
import org.richfaces.model.SortField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.softserveinc.action.managebook.ManageBookBean;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.OrderBy;
import com.softserveinc.model.persist.facade.BookFacadeLocal;
import com.softserveinc.model.session.manager.BookManagerLocal;
import com.softserveinc.model.session.util.DataModelHelper;



@ManagedBean
@SessionScoped
public class BooksBean {
	
	private static Logger log = LoggerFactory.getLogger(BooksBean.class);
	private String pageSize;
	private List<Book> books;
	
	public List<Book> getBooks() {
		return books;
	}
	
	@EJB
	BookFacadeLocal bookFacade;
	
	@EJB
	BookManagerLocal bookManager;
	
//	 private static final class BooksDataModel extends JPADataModel<Book> {
//	        private BooksDataModel(EntityManager entityManager) {
//	            super(entityManager, Book.class);
//	            log.info("created BookDataModel");
//	        }
//	 
//	        @Override
//	        protected Object getId(Book t) {
//	        	log.info("Book={}", t);
//	            return t.getIdbook();
//	        }
//	    }
	private final class BookDataModel extends DataModelHelper<Book> {
		private ArrangeableState arrangeableState = getArrangeableState();
		
		@Override
		public void walk(FacesContext context, DataVisitor visitor, Range range, Object argument) {
			System.out.println("walk start");
//	        CriteriaQuery<Book> criteriaQuery = createSelectCriteriaQuery();
//	        TypedQuery<Book> query = entityManager.createQuery(criteriaQuery);
//	 
	        SequenceRange sequenceRange = (SequenceRange) range;
//	        if (sequenceRange.getFirstRow() >= 0 && sequenceRange.getRows() > 0) {
//	        	
//	            query.setFirstResult(sequenceRange.getFirstRow());
//	            query.setMaxResults(sequenceRange.getRows());
//	        }
//	 
//	        books = query.getResultList();
//			books = bookManager.getBooksForDataTable(sequenceRange, arrangeableState, sortOrders, filterValues);
//	        for (Book t : books) {
//	            visitor.process(context, t.getIdbook(), argument);
//	        }
//	        System.out.println("walk finished");

		}

		@Override
		public int getRowCount() {
			return 0;
		}

		@Override
		public Book getRowData() {
			return bookFacade.findById((String) getRowKey());
		}
		
	}
	 
	    private Map<String, SortOrder> sortOrders = Maps.newHashMapWithExpectedSize(1);
	    private Map<String, String> filterValues = Maps.newHashMap();
	    private String sortProperty;
	 
	    public BooksBean() {
	    	books = new ArrayList<Book>();
	    	pageSize = "3";
	        log.info("Books bean created");
	    }
	 
	    @PersistenceContext(unitName = "primary")
		private EntityManager entityManager;
	 
	    public Map<String, SortOrder> getSortOrders() {
	    	log.info("BooksBean getSortOrders");
	        return sortOrders;
	    }
	 
	    public Map<String, String> getFilterValues() {
	    	log.info("BooksBean getFilterValues");
	        return filterValues;
	    }
	 
	    public String getSortProperty() {
	    	log.info("BooksBean getSortProperty, sortProperty={}", sortProperty);
	        return sortProperty;
	    }
	 
	    public void setSortProperty(String sortPropety) {
	    	log.info("BooksBean setSortProperty, sortProperty={}", sortPropety);
	        this.sortProperty = sortPropety;
	    }
	 
	    public void toggleSort(String column) {
	    	
	    	for (Map.Entry<String, SortOrder> pair : sortOrders.entrySet()) {
	    		if (pair.getKey().equals(column)) {
	    			if (pair.getValue().equals(SortOrder.ascending)) {
	    				pair.setValue(SortOrder.descending);
	    			} else {
	    				pair.setValue(SortOrder.ascending);
	    			}
	    		}
	    		else {
	    			sortOrders.clear();
	    			break;
	    		}
	    	}
	    	
	    	if (sortOrders.isEmpty()) {
	    		sortOrders.put(column, SortOrder.ascending);
	    	}
//	        for (Entry<String, SortOrder> entry : sortOrders.entrySet()) {
//	            SortOrder newOrder;
//	 
//	            if (entry.getKey().equals(sortProperty)) {
//	                if (entry.getValue() == SortOrder.ascending) {
//	                    newOrder = SortOrder.descending;
//	                } else {
//	                    newOrder = SortOrder.ascending;
//	                }
//	            } else {
//	                newOrder = SortOrder.unsorted;
//	            }
//	 
//	            entry.setValue(newOrder);
//	        }
	    	
	        log.info("toggleSort method done");
	    }
	 
	    public Object getDataModel() {
	    	log.info("getDataModel");
	        return new BookDataModel();
	    }
	    
		public String getPageSize() {
			System.out.println("getter");
			return pageSize;
		}

		public void setPageSize(String pageSize) {
			System.out.println("setter");
			this.pageSize = pageSize;
		}
		
		public Object actionForm() {
			books.clear();
			return getDataModel();
			//return books;
		}


		
		
		
		

}
