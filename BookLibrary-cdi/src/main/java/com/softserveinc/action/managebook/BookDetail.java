package com.softserveinc.action.managebook;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.softserveinc.action.util.DataTableHelper;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.EntityConstant;
import com.softserveinc.model.persist.entity.Review;
import com.softserveinc.model.persist.facade.ReviewFacadeLocal;
import com.softserveinc.model.session.manager.BookManagerLocal;

@ManagedBean
@RequestScoped
public class BookDetail extends DataTableHelper<Review> implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2063925001992083561L;

	@EJB
	BookManagerLocal bookManager;
	
	@EJB
	private ReviewFacadeLocal reviewFacade;
	
	private String selectedId;
	private Book book;

	
	public void loadBook() {
		book = bookManager.getBookByID(selectedId);
	}
	
	public String getSelectedId() {
		return selectedId;
	}
	
	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}
	
	public Book getBook() {
		return book;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public EntityConstant getEntityConstantInstance() {
		return null;
	}

	@Override
	public void deleteEntity() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteListEntities() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getEntitiesForCurrentPage() {
		List<Review> list = reviewFacade.findReviewsForBook(getCurrentRequirementsForDataTable(), book);
		
	}

	@Override
	public int getCountEntitiesForCurrentRequirements() {
		// TODO Auto-generated method stub
		return 0;
	}
}
