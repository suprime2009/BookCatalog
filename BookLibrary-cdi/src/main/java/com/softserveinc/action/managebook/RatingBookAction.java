package com.softserveinc.action.managebook;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.softserveinc.model.manager.ReviewManager;
import com.softserveinc.model.manager.ReviewManagerLocal;
import com.softserveinc.model.persist.entity.Book;


@ManagedBean
@SessionScoped
public class RatingBookAction {
	
	@EJB
	private ReviewManagerLocal reviewManager;
	
	public double getRating(Book book) {
		return reviewManager.getAverageRating(book);
	}

}
