package com.softserveinc.booklibrary.session.persist.facade;

import javax.ejb.Local;

/**
 * 
 * ReviewFacadeLocal an interface extended from IReviewFacade interface and
 * describes facade operations for Review entity. This interface is local and
 * used in application as main.
 *
 */
@Local
public interface ReviewFacadeLocal extends IReviewFacade {

}
