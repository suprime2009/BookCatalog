package com.softserveinc.model.manager;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.facade.ReviewFacadeLocal;

@Stateless
public class ReviewManager implements ReviewManagerLocal, ReviewManagerRemote{

	@EJB
	private ReviewFacadeLocal reviewFacade;
	
	@Override
	public double getAverageRating(Book book) {
		// TODO Auto-generated method stub
		return reviewFacade.findAverageRatingForBook(book);
	}

}
