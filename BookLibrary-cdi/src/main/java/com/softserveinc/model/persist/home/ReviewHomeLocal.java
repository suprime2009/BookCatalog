package com.softserveinc.model.persist.home;

import javax.ejb.Local;

import com.softserveinc.model.persist.entity.Review;

/**
 * ReviewHomeLocal an interface extended from IHome interface and describes basic CRUD operation
 * for Review entity. This interface is local and used in application as main.
 *
 */
@Local
public interface ReviewHomeLocal extends IHome<Review>{

}
