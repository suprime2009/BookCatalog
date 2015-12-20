package com.softserveinc.model.persist.home;

import javax.ejb.Remote;

import com.softserveinc.model.persist.entity.Review;

/**
 * ReviewHomeRemote an interface extended from IHome interface and describes basic CRUD operation
 * for Review entity. This interface is remote and used in application only for testing.
 *
 */
@Remote(ReviewHomeRemote.class)
public interface ReviewHomeRemote extends IHome<Review>{

}
