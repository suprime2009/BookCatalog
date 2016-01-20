package com.softserveinc.action.managebook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.richfaces.skin.*;

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
import org.richfaces.component.SortOrder;
import org.richfaces.model.ArrangeableState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.action.util.DataTableHelper;
import com.softserveinc.exception.BookCatalogException;
import com.softserveinc.exception.ReviewManagerException;
import com.softserveinc.model.persist.entity.Book;

import com.softserveinc.model.persist.entity.EntityFieldHolder;

import com.softserveinc.model.persist.entity.Review;
import com.softserveinc.model.persist.facade.BookFacadeLocal;
import com.softserveinc.model.persist.facade.ReviewFacadeLocal;
import com.softserveinc.model.persist.home.ReviewHomeLocal;
import com.softserveinc.model.session.manager.BookManagerLocal;
import com.softserveinc.model.session.manager.ReviewManagerLocal;
import com.softserveinc.model.session.managerimpl.ReviewManager;
import com.softserveinc.model.session.util.DataModelHelper;

@ManagedBean
@ViewScoped
public class BookDetailAction implements Serializable {

	private static Logger log = LoggerFactory.getLogger(BookDetailAction.class);

	private Book book;
	private String selectedIdReview;
	private Review review;
	
	//true - ascending
	//false - descending
	private SortOrder sortOrder;

	@EJB
	private BookFacadeLocal bookfacade;

	@EJB
	private ReviewFacadeLocal reviewFacade;

	@EJB
	private ReviewManagerLocal reviewManager;

	public BookDetailAction() {
		sortOrder = SortOrder.descending;
		review = new Review();
		log.debug("The bean has been created.");
	}
	
	public void toogleSortOrder() {
		if (sortOrder == SortOrder.descending) {
			sortOrder = SortOrder.ascending;
		} else {
			sortOrder = SortOrder.descending;
		}
	}
	
	public boolean getOrderStatus() {
		if (sortOrder.equals(SortOrder.ascending)) {
			return true;
		} else {
			return false;
		}
	}

	private void showGlobalMessageOnPage(String message) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage(message);
		context.addMessage(null, facesMessage);
	}

	public void submitDeleteReview() {
		try {
			reviewManager.deleteReview(selectedIdReview);
			showGlobalMessageOnPage("Review has been successfully deleted.");
		} catch (ReviewManagerException e) {
			showGlobalMessageOnPage(e.getMessage());
			log.error("Review  hasn't deleted. {}", e.getMessage());
		}
		log.debug("The method successfully finished. Review has been daleted.");
	}

	public void submitCreateReview() {
		try {
			review.setBook(book);
			reviewManager.createReview(review);
			showGlobalMessageOnPage("Review has been successfully created.");
			reset();
		} catch (ReviewManagerException e) {
			showGlobalMessageOnPage(e.getMessage());
			log.error("Review hasn't created. {}", e.getMessage());
		} catch (BookCatalogException e) {
			showGlobalMessageOnPage(e.getMessage());
			log.error("Review hasn't created. {}", e.getMessage());
		}
		log.debug("The method successfully finished. Review has been created.");
	}
	
	public int getCountReviewForBook() {
		return 4;
	}

	public void submitUpdateReview() {
		try {
			reviewManager.updateReview(review);
			showGlobalMessageOnPage("Review has been successfully updated.");
		} catch (ReviewManagerException e) {
			showGlobalMessageOnPage(e.getMessage());
			log.error("Review hasn't updated. {}", e.getMessage());
		}
		log.debug("The method successfully finished. Review has been updated.");
	}

	public void loadReviewForUpdate(String idReview) {
		review = reviewFacade.findById(idReview);
		log.debug("The review for update has been loaded.");
	}

	public void reset() {
		review = new Review();
	}

	public Object getDataModel() {
		return new ReviewDataModel();
	}

	public void loadBook() {
		FacesContext context = FacesContext.getCurrentInstance();
		String ibBook = (String) context.getExternalContext().getRequestParameterMap().get("idBook");
		book = bookfacade.findById(ibBook);
		log.debug("The method done. Book {} has been loaded.", book);
	}

	public Double getBookRating() {
		Double rating = reviewFacade.findAverageRatingForBook(book);
		log.debug("The method done, rating for a book {} equals {}.", book, rating);
		return rating;
	}

	private final class ReviewDataModel extends DataModelHelper<Review> implements Serializable {
		
		private  Logger log = LoggerFactory.getLogger(ReviewDataModel.class);
		
		@Override
		public void walk(FacesContext context, DataVisitor visitor, Range range, Object argument) {
			
			SequenceRange sequenceRange = (SequenceRange) range;
			int firstRow = sequenceRange.getFirstRow();
			int countRows = sequenceRange.getRows();
			List<Review> list = reviewFacade.findReviewsForBook(book, firstRow, countRows);
			System.out.println(list.size());

			for (Review r : list) {
				visitor.process(context, r.getIdreview(), argument);
			}
			log.info("The method finished. Has been found {} reviews.", list.size());
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

	// getters and setters

	public Book getBook() {return book;}

	public void setBook(Book book) {this.book = book;}

	public Review getReview() {return review;}

	public String getSelectedIdReview() {return selectedIdReview;}

	public void setSelectedIdReview(String selectedIdReview) {this.selectedIdReview = selectedIdReview;}

	public SortOrder getSortOrder() {return sortOrder;}

	public void setSortOrder(SortOrder sortOrder) {this.sortOrder = sortOrder;}
	
}
