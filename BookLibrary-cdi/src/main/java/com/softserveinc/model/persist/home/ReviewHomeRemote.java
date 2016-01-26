package com.softserveinc.model.persist.home;

import javax.ejb.Remote;

import com.softserveinc.model.persist.entity.Review;

/**
 * The {@code ReviewHomeRemote} an interface, extended from {@link IHome}
 * interface. It provides basic CRUD and all write operations for {@link Review}
 * entity. This interface is remote and used in application only for testing.
 *
 */
@Remote(ReviewHomeRemote.class)
public interface ReviewHomeRemote extends IHome<Review> {

}
