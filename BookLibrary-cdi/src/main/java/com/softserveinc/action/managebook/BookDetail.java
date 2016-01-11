package com.softserveinc.action.managebook;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.richfaces.model.ArrangeableState;

import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.Review;
import com.softserveinc.model.persist.facade.ReviewFacadeLocal;
import com.softserveinc.model.session.manager.BookManagerLocal;
import com.softserveinc.model.session.util.DataModelHelper;


@ManagedBean
@RequestScoped
public class BookDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2063925001992083561L;

	@EJB
	BookManagerLocal bookManager;

	@EJB
	public ReviewFacadeLocal reviewFacade;

	private String selectedId;
	private Book book;

	private final class ReviewdataModel extends DataModelHelper<Review> {

		private ArrangeableState arrangeableState = getArrangeableState();

		@Override
		public void walk(FacesContext context, DataVisitor visitor, Range range, Object argument) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("walk start");
			SequenceRange sequenceRange = (SequenceRange) range;
			
			List<Review> list = reviewFacade.findReviewsForBook(book, sequenceRange.getFirstRow(), sequenceRange.getRows());
			System.out.println(list.size());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	public Object getDataModel() {

		return new ReviewdataModel();
	}

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

}
