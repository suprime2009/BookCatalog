package com.softserveinc.model.persist.facade;

import javax.ejb.Remote;

/**
 * ReviewFacadeRemote an interface extended from IReviewFacade interface and describes describes facade operations
 * for Review entity. This interface is remote and used in application only for testing.
 *
 */
@Remote(ReviewFacadeRemote.class)
public interface ReviewFacadeRemote extends IReviewFacade {

}
