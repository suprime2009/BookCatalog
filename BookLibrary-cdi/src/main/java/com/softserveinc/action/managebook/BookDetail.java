package com.softserveinc.action.managebook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.richfaces.model.ArrangeableState;

import com.softserveinc.action.util.DataTableHelper;
import com.softserveinc.model.persist.entity.Book;

import com.softserveinc.model.persist.entity.EntityConstant;

import com.softserveinc.model.persist.entity.Review;
import com.softserveinc.model.persist.facade.ReviewFacadeLocal;
import com.softserveinc.model.persist.home.ReviewHomeLocal;
import com.softserveinc.model.session.manager.BookManagerLocal;
import com.softserveinc.model.session.manager.ReviewManagerLocal;
import com.softserveinc.model.session.util.DataModelHelper;

@ManagedBean
@ViewScoped
public class BookDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7988905522264627284L;

	@EJB
	private BookManagerLocal bookManager;

	@EJB
	public ReviewFacadeLocal reviewFacade;

	@EJB
	private ReviewManagerLocal reviewManager;

	private String selectedId;
	private Book book;
	private String selectedIdReview;
	private Review review;


	public String getSelectedIdReview() {
		System.out.println("method get id to delete id==" + selectedIdReview);
		return selectedIdReview;
	}

	public void setSelectedIdReview(String selectedIdReview) {
		System.out.println("method set id to delete id==" + selectedIdReview);
		this.selectedIdReview = selectedIdReview;
	}

	public BookDetail() {
		review = new Review();
		System.out.println("create BookDetail");

	}

	public void submitDeleteReview() {
		boolean result = reviewManager.deleteReview(selectedIdReview);
		if (result) {
			String message = "Review has been successfully deleted.";
			showMessageOnUI(message);
		} else {
			String message = "Review has not been deleted.";
			showMessageOnUI(message);
		}
	}

	public void submitCreateReview() {
		review.setBook(book);
		boolean result = reviewManager.createReview(review);
		cleanAfterSubmit();
		if (result) {
			String message = "Review has been successfully created.";
			showMessageOnUI(message);
		
		} else {
			String message = "Review has not been created.";
			showMessageOnUI(message);
		}
	}
	
	public void submitUpdateReview(FacesContext context, UIComponent component, Object value) {
		
	}
	
	public boolean getReviewToUpdate(String idReview) {
		System.out.println("getReviewToUpdate method start");
		review = reviewFacade.findById(idReview);
		System.out.println("method done getReviewToUpdate ====" + review.toString());
		return true;
	}
	
	private void showMessageOnUI(String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage(message);
		context.addMessage(null, facesMessage);
	}
	
	public void cleanAfterSubmit() {
		review = null;
		review = new Review();
	}

	public Object getDataModel() {

		return new ReviewDataModel();
	}

	public void loadBook() {
		book = bookManager.getBookByID(selectedId);
	}

	public Double getBookRating() {
		System.out.println("getBookRating" + book);
		return reviewFacade.findAverageRatingForBook(book);
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
	
	public Review getReview() {
		return review;
	}

	private final class ReviewDataModel extends DataModelHelper<Review> implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -1656885033253044691L;
		private ArrangeableState arrangeableState = getArrangeableState();

		@Override
		public void walk(FacesContext context, DataVisitor visitor, Range range, Object argument) {

			System.out.println("walk start");
			SequenceRange sequenceRange = (SequenceRange) range;

			List<Review> list = reviewFacade.findReviewsForBook(book, sequenceRange.getFirstRow(),
					sequenceRange.getRows());
			System.out.println(list.size());

			for (Review r : list) {
				visitor.process(context, r.getIdreview(), argument);
			}

			System.out.println("walk end");
		}

		@Override
		public int getRowCount() {
			return reviewFacade.findCountReviewForBook(book);
		}

		@Override
		public Review getRowData() {
			return reviewFacade.findById((String) getRowKey());
		}
	}

}
