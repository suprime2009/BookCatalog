package com.softserveinc.model.persist.home;

import javax.ejb.Remote;

import com.softserveinc.model.persist.entity.Review;

@Remote(ReviewHomeRemote.class)
public interface ReviewHomeRemote extends IHome<Review>{

}
