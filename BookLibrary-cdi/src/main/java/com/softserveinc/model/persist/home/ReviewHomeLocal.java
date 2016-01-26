package com.softserveinc.model.persist.home;

import javax.ejb.Local;

import com.softserveinc.model.persist.entity.Review;

/**
 * The {@code ReviewHomeLocal} an interface, extended from {@link IHome}
 * interface. It provides basic CRUD and all write operations for {@link Review}
 * entity. This interface is a local.
 *
 */
@Local
public interface ReviewHomeLocal extends IHome<Review> {

}
