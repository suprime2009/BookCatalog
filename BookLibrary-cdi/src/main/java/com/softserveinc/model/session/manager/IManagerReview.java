package com.softserveinc.model.session.manager;

import com.softserveinc.model.persist.entity.Review;

public interface IManagerReview {
	
	public boolean createReview(Review review);
	
	public boolean deleteReview(String idReview);
	
	public boolean updateReview(Review review);

}
