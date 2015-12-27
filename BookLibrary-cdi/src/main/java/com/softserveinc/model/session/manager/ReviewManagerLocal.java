package com.softserveinc.model.session.manager;

import javax.ejb.Local;

import com.softserveinc.model.persist.entity.Book;

@Local
public interface ReviewManagerLocal {
	
	double getAverageRating(Book book);
	

}
