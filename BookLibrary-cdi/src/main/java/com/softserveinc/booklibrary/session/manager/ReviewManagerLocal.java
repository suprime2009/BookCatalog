package com.softserveinc.booklibrary.session.manager;

import javax.ejb.Local;

/**
 * ReviewManagerLocal an interface extended from IManagerReview interface and
 * describes business operations for Review entity. This interface is local and
 * used in application as main.
 *
 */
@Local
public interface ReviewManagerLocal extends IManagerReview {

}
