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
import com.softserveinc.exception.BookManagerException;
import com.softserveinc.exception.ReviewFacadeException;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 681604932693331609L;
	private static Logger log = LoggerFactory.getLogger(BookDetailAction.class);

	private Book book;
	private String selectedIdReview;
	private Review review;

	private SortOrder sortOrder;
	// true - add
	// false - edit
	private boolean addAction;
	private int countReviewsForBook;

	@EJB
	private BookFacadeLocal bookfacade;
	
	@EJB
	private BookManagerLocal bookManager;

	@EJB
	private ReviewFacadeLocal reviewFacade;

	@EJB
	private ReviewManagerLocal reviewManager;
	
	public BookDetailAction() {
		sortOrder = SortOrder.descending;
		review = new Review();
		log.debug("The bean has been created.");
	}
	
	public int getCountReviewsForCurrentBook() {
		countReviewsForBook = reviewFacade.findCountReviewForBook(book);
		return countReviewsForBook;
	}

	public void toogleSortOrder() {
		if (sortOrder == SortOrder.descending) {
			sortOrder = SortOrder.ascending;
		} else {
			sortOrder = SortOrder.descending;
		}
		log.info("The sortOrder has been toogled. Current order is {}", sortOrder);
	}

	public boolean getOrderStatus() {
		if (sortOrder.equals(SortOrder.ascending)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String deleteBook() {
		try {
			bookManager.deleteBook(book.getIdBook());
		} catch (BookManagerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/manageBooks.xhtml?faces-redirect=true";
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
		System.out.println("loadBook startMethod");
		FacesContext context = FacesContext.getCurrentInstance();
		String ibBook = (String) context.getExternalContext().getRequestParameterMap().get("idBook");
		book = bookfacade.findById(ibBook);
		log.info("The method done. Book {} has been loaded.", book);
	}

	public Double getBookRating() {
		Double rating = reviewFacade.findAverageRatingForBook(book);
		log.debug("The method done, rating for a book {} equals {}.", book, rating);
		return rating;
	}

	private final class ReviewDataModel extends DataModelHelper<Review> implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7733745802725088461L;
		private Logger log = LoggerFactory.getLogger(ReviewDataModel.class);

		@Override
		public void walk(FacesContext context, DataVisitor visitor, Range range, Object argument) {
			log.debug("The method starts.");
			SequenceRange sequenceRange = (SequenceRange) range;
			int firstRow = sequenceRange.getFirstRow();
			int countRows = sequenceRange.getRows();
			List<Review> list = null;
			try {
				list = reviewFacade.findReviewsForBook(book, firstRow, countRows, sortOrder);
			} catch (ReviewFacadeException e) {
				list = new ArrayList<Review>();
				String errorMessage = "Not executed. ";
				log.error(errorMessage + e.getMessage());
				showGlobalMessageOnPage(errorMessage);
			}

			for (Review r : list) {
				visitor.process(context, r.getIdreview(), argument);
			}
			log.info("The method finished. Has been found {} reviews.", list.size());
		}

		@Override
		public int getRowCount() {
			return countReviewsForBook;
		}

		@Override
		public Review getRowData() {
			return reviewFacade.findById((String) getRowKey());
		}
	}

	// getters and setters

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Review getReview() {
		return review;
	}

	public String getSelectedIdReview() {
		return selectedIdReview;
	}

	public void setSelectedIdReview(String selectedIdReview) {
		this.selectedIdReview = selectedIdReview;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}

	public boolean isAddAction() {
		return addAction;
	}

	public void setAddAction(boolean addAction) {
		this.addAction = addAction;
	}
}
