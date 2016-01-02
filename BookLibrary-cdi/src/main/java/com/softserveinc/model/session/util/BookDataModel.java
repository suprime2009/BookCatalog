package com.softserveinc.model.session.util;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.richfaces.component.SortOrder;
import org.richfaces.model.Arrangeable;
import org.richfaces.model.ArrangeableState;
import org.richfaces.model.FilterField;
import org.richfaces.model.SortField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.facade.BookFacadeLocal;
import com.softserveinc.model.session.manager.BookManagerLocal;

public class BookDataModel extends ExtendedDataModel<Book> implements Arrangeable {
	
	private static Logger log = LoggerFactory.getLogger(BookDataModel.class);

	private String bookId;
	private ArrangeableState arrangeableState;

	@EJB
	private BookFacadeLocal bookFacade;
	
	@EJB
	private BookManagerLocal bookManager;
	

	private EntityManager entityManager;
	
	public BookDataModel(EntityManager entityManager) {
		System.out.println("BookDataModel CONSTRUCTOR");
		this.entityManager = entityManager;
	}
	
	@Override
	public void arrange(FacesContext context, ArrangeableState state) {
		 arrangeableState = state;
		 System.out.println("state:");
	        System.out.println("arrangeableState" + arrangeableState.getFilterFields());
	        System.out.println("arrangeableState" + arrangeableState.getSortFields());
		 log.info("ArrangeableState got, method done");
		
	}



	 private CriteriaQuery<Book> createSelectCriteriaQuery() {
		 System.out.println(entityManager);
    	 System.out.println("JPADATAMODEL method createSelectCriteriaQuery method start");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        System.out.println("before root");
        Root<Book> root = criteriaQuery.from(Book.class);
        System.out.println("Before if");
 
        if (arrangeableState != null) {
        	 System.out.println("JPADATAMODEL method createSelectCriteriaQuery method arrangeableState != null");
            List<Order> orders = createOrders(criteriaBuilder, root);
            System.out.print(orders.size());
            System.out.println(" ORDER LIST SIZE !!!!!!!!!!!");
            if (!orders.isEmpty()) {
                criteriaQuery.orderBy(orders);
            }
 
            Expression<Boolean> filterCriteria = createFilterCriteria(criteriaBuilder, root);
            System.out.println("JPADATAMODEL method createSelectCriteriaQuery method done");
            if (filterCriteria != null) {
            	 System.out.println("JPADATAMODEL method createSelectCriteriaQuery method filterCriteria != null");
                criteriaQuery.where(filterCriteria);
            }
        }
 
        return criteriaQuery;
    }
 
    private List<Order> createOrders(CriteriaBuilder criteriaBuilder, Root<Book> root) {
    	 System.out.println("JPADATAMODEL method createOrders method start");
        List<Order> orders = Lists.newArrayList();
        List<SortField> sortFields = arrangeableState.getSortFields();
        System.out.println(arrangeableState.getSortFields());
        System.out.println(sortFields.size());
        if (sortFields != null && !sortFields.isEmpty()) {
        	 System.out.println("JPADATAMODEL method createOrders method sortFields != null && !sortFields.isEmpty()");
            FacesContext facesContext = FacesContext.getCurrentInstance();
 
            for (SortField sortField : sortFields) {
                String propertyName = (String) sortField.getSortBy().getValue(facesContext.getELContext());
 
                Path<Object> expression = root.get(propertyName);
 
                Order jpaOrder;
                SortOrder sortOrder = sortField.getSortOrder();
                if (sortOrder == SortOrder.ascending) {
                    jpaOrder = criteriaBuilder.asc(expression);
                } else if (sortOrder == SortOrder.descending) {
                    jpaOrder = criteriaBuilder.desc(expression);
                } else {
                    throw new IllegalArgumentException(sortOrder.toString());
                }
 
                orders.add(jpaOrder);
            }
        }
 
        return orders;
    }
 
    protected Expression<Boolean> createFilterCriteriaForField(String propertyName, Object filterValue, Root<Book> root,
            CriteriaBuilder criteriaBuilder) {
    	  System.out.println("JPADATAMODEL method createFilterCriteriaForField method start");
        String stringFilterValue = (String) filterValue;
        if (Strings.isNullOrEmpty(stringFilterValue)) {
        	  System.out.println("JPADATAMODEL method createFilterCriteriaForField method return null");
            return null;
        }
 
        stringFilterValue = stringFilterValue.toLowerCase(arrangeableState.getLocale());
 
        Path<String> expression = root.get(propertyName);
        Expression<Integer> locator = criteriaBuilder.locate(criteriaBuilder.lower(expression), stringFilterValue, 1);
        return criteriaBuilder.gt(locator, 0);
    }
 
    private Expression<Boolean> createFilterCriteria(CriteriaBuilder criteriaBuilder, Root<Book> root) {
    	 System.out.println("JPADATAMODEL method createFilterCriteria start");
        Expression<Boolean> filterCriteria = null;
        List<FilterField> filterFields = arrangeableState.getFilterFields();
        if (filterFields != null && !filterFields.isEmpty()) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            System.out.println("JPADATAMODEL method createFilterCriteria FacesContext.getCurrentInstance() done");
 
            for (FilterField filterField : filterFields) {
                String propertyName = (String) filterField.getFilterExpression().getValue(facesContext.getELContext());
                Object filterValue = filterField.getFilterValue();
 
                Expression<Boolean> predicate = createFilterCriteriaForField(propertyName, filterValue, root, criteriaBuilder);
 
                if (predicate == null) {
                	  System.out.println("JPADATAMODEL method createFilterCriteria continue");
                    continue;
                }
 
                if (filterCriteria == null) {
                	  System.out.println("JPADATAMODEL method createFilterCriteria filterCriteria == null");
                    filterCriteria = predicate.as(Boolean.class);
                } else {
                    filterCriteria = criteriaBuilder.and(filterCriteria, predicate.as(Boolean.class));
                    System.out.println("JPADATAMODEL method createFilterCriteria filterCriteria =criteriaBuilder.and(filterCriteria, predicate.as(Boolean.class)");
                }
            }
        }
        return filterCriteria;
    }
 
    @Override
    public void walk(FacesContext context, DataVisitor visitor, Range range, Object argument) {
        CriteriaQuery<Book> criteriaQuery = createSelectCriteriaQuery();
        TypedQuery<Book> query = entityManager.createQuery(criteriaQuery);
        System.out.println("JPADATAMODEL method walk start");
 
        SequenceRange sequenceRange = (SequenceRange) range;
        System.out.println("RANGE=" + range);
        System.out.println("SequenceRange= firstRow()==" + sequenceRange.getFirstRow());
        System.out.println("SequenceRange= gerRows()" + sequenceRange.getRows());
        if (sequenceRange.getFirstRow() >= 0 && sequenceRange.getRows() > 0) {
            query.setFirstResult(sequenceRange.getFirstRow());
            query.setMaxResults(sequenceRange.getRows());
            System.out.println("JPADATAMODEL method walk if body");
        }
 
        List<Book> data = query.getResultList();
        System.out.println("DATA SIZE !!!! " + data.size());
        System.out.println("DATA SIZE !!!! " + data.size());
        System.out.println("DATA SIZE !!!! " + data.size());
        for (Book t : data) {
            visitor.process(context, t.getIdBook(), argument);
        }
    }

	@Override
	public boolean isRowAvailable() {
		log.info("Method returns= {}", (bookId != null));
		return bookId != null;
	}

	@Override
	public int getRowCount() {
		log.info("method start");
		//int count = bookFacade.findCountBooks();
		//log.info("gerRowCount done! Has been found {} books", count);
		return 7;
	}

	@Override
	public Book getRowData() {
		log.info("getRowData done! Has been found book {}" );
		return entityManager.find(Book.class, bookId);
	}

	@Override
	public int getRowIndex() {
		log.info("Method getRowIndex() done!");
		return -1;
	}
	
	@Override
	public Object getRowKey() {
		return bookId;
	}

	@Override
	public void setRowKey(Object bookId) {
		this.bookId =(String) bookId;
	}

	@Override
	public void setRowIndex(int arg0) {
		throw new UnsupportedOperationException();

	}

	@Override
	public Object getWrappedData() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setWrappedData(Object arg0) {
		throw new UnsupportedOperationException();

	}



}
