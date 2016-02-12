package com.softserveinc.booklibrary.action.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.richfaces.component.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.booklibrary.action.helper.DataModelHelper;
import com.softserveinc.booklibrary.exception.ReviewManagerException;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.model.entity.Review;
import com.softserveinc.booklibrary.session.manager.ReviewManagerLocal;
import com.softserveinc.booklibrary.session.persist.facade.BookFacadeLocal;
import com.softserveinc.booklibrary.session.persist.facade.ReviewFacadeLocal;

/**
 * This class is a action bean for UI page bookDetails page. The action bean
 * provides operations for showing book details on page, average book rating and
 * operations with reviews for current book. According to reviews bean provides
 * possibility get reviews, sort reviews, create, update and delete reviews.
 *
 */
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
	private ReviewFacadeLocal reviewFacade;

	@EJB
	private ReviewManagerLocal reviewManager;

	public BookDetailAction() {
		sortOrder = SortOrder.descending;
		review = new Review();
		log.debug("The bean has been created.");
	}

	/**
	 * The method returns count of reviews for book shown on the page.
	 * 
	 * @return int count of reviews
	 */
	public int getCountReviewsForCurrentBook() {
		countReviewsForBook = reviewFacade.findCountReviewForBook(book);
		return countReviewsForBook;
	}

	/**
	 * The method switches sort order for createDate field of {@link Review}
	 * entity and load reviews in according with the sort order.
	 */
	public void toogleSortOrder() {
		if (sortOrder == SortOrder.descending) {
			sortOrder = SortOrder.ascending;
		} else {
			sortOrder = SortOrder.descending;
		}
		log.info("The sortOrder has been toogled. Current order is {}", sortOrder);
	}

	/**
	 * The method used to change the active button on UI for sorting. If sort
	 * order equals ascending method returns true and on UI will display
	 * component for sorting in descending order.
	 * 
	 * @return boolean
	 */
	public boolean getOrderStatus() {
		if (sortOrder.equals(SortOrder.ascending)) {
			return true;
		} else {
			return false;
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
	 * The method is executed on delete action for review. The method calls
	 * delete method on {@code ReviewManager}. If the review has been
	 * successfully removed from database, the method will show message on UI
	 * about it. Otherwise will be displayed the message that review hasn't been
	 * removed.
	 *
	 */
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

	/**
	 * The method is executed on create action for review. The method calls
	 * create method on {@code ReviewManager}. If the review has been
	 * successfully created, the method will show message on UI about it.
	 * Otherwise will be displayed the message that review hasn't been created.
	 */
	public void submitCreateReview() {
		try {
			review.setBook(book);
			reviewManager.createReview(review);
			showGlobalMessageOnPage("Review has been successfully created.");
			reset();
		} catch (ReviewManagerException e) {
			showGlobalMessageOnPage(e.getMessage());
			log.error("Review hasn't created. {}", e.getMessage());
		}
		log.debug("The method successfully finished. Review has been created.");
	}

	/**
	 * The method is executed on edit action for review. The method calls update
	 * method on {@code ReviewManager}. If the review has been successfully
	 * updated, the method will show message on UI about it. Otherwise will be
	 * displayed the message that review hasn't been updated.
	 */
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

	/**
	 * The method used for {@code tabPanel} UI component to set the name of tab
	 * that will be shown by default on the user page.
	 * 
	 * @return String id component tab
	 */
	public String getTabIdToAction() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		String action = params.get("action");
		if (action != null && action.equals("edit")) {
			return "editBookTab";
		} else {
			return "bookDetailTab";
		}
	}

	/**
	 * The method loads {@link Review} instance by id number, that should be
	 * updated.
	 * 
	 * @param idReview
	 */
	public void loadReviewForUpdate(String idReview) {
		review = reviewFacade.findById(idReview);
		log.debug("The review for update has been loaded.");
	}

	public void refresh() {
		book = bookfacade.findById(book.getIdBook());
	}

	/**
	 * The method calls on action {@code clean} on UI. The method will clean all
	 * input fields for add review form on UI.
	 */
	public void reset() {
		review = new Review();
	}

	/**
	 * The method loads @{ Book} instance that show will be shown on UI. Method
	 * runs on page load event.
	 */
	public void loadBook() {
		FacesContext context = FacesContext.getCurrentInstance();
		String ibBook = (String) context.getExternalContext().getRequestParameterMap().get("idBook");
		book = bookfacade.findById(ibBook);
		log.info("The method done. Book {} has been loaded.", book);
	}

	/**
	 * Method returns book average rating based on his reviews.
	 * 
	 * @return Double rating.
	 */
	public Double getBookRating() {
		Double rating = reviewFacade.findAverageRatingForBook(book);
		log.debug("The method done, rating for a book {} equals {}.", book, rating);
		return rating;
	}

	public Object getDataModel() {
		return new ReviewDataModel();
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

			List<Review> list = null;
			try {
				list = reviewFacade.findReviewsForBook(book, sequenceRange.getFirstRow(), sequenceRange.getRows(),
						sortOrder);
			} catch (IllegalArgumentException e) {
				list = new ArrayList<Review>();
				String errorMessage = "Not executed. System error.";
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
