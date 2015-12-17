package com.softserveinc.model.persist.facade;

import javax.ejb.Remote;

@Remote(ReviewFacadeRemote.class)
public interface ReviewFacadeRemote extends IReviewFacade {

}
