package com.softserveinc.action.managebook;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.session.manager.BookManagerLocal;
import com.softserveinc.model.session.manager.ReviewManagerLocal;
import com.softserveinc.model.session.managerImpl.ReviewManager;


@ManagedBean
@SessionScoped
public class RatingBookAction {
	
	@EJB
	private ReviewManagerLocal reviewManager;
	
	@EJB
	private BookManagerLocal bookManagerLocal;
	
	public double getRating(Book book) {
		return reviewManager.getAverageRating(book);
	}
	

}
