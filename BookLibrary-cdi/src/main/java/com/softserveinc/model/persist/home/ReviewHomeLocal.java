package com.softserveinc.model.persist.home;

import javax.ejb.Local;

import com.softserveinc.model.persist.entity.Review;

@Local(ReviewHomeLocal.class)
public interface ReviewHomeLocal extends IHome<Review>{

}
