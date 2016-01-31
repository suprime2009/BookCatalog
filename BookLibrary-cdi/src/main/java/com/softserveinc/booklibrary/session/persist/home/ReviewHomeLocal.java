package com.softserveinc.booklibrary.session.persist.home;

import javax.ejb.Local;

import com.softserveinc.booklibrary.model.entity.Review;



/**
 * The {@code ReviewHomeLocal} an interface, extended from {@link IHome}
 * interface. It provides basic CRUD and all write operations for {@link Review}
 * entity. This interface is a local.
 *
 */
@Local
public interface ReviewHomeLocal extends IHome<Review> {

}
