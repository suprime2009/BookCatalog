package com.softserveinc.action.managebook;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.model.persist.facade.BookFacadeLocal;
import com.softserveinc.model.persist.facade.ReviewFacadeLocal;
import com.softserveinc.model.session.util.ReviewRatingFieldsEnum;

@ManagedBean
@ViewScoped
public class ManageReviewsAction implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8167926850911171468L;

	private static Logger log = LoggerFactory.getLogger(ManageReviewsAction.class);
	
	@EJB
	private ReviewFacadeLocal reviewFacade;
	
	@EJB
	private BookFacadeLocal bookFacade;
	
	private Map<Integer, Integer> countBooksByRating;
	
	public ManageReviewsAction() {
		countBooksByRating = new LinkedHashMap<>(5);
	}

	public Map<Integer, Integer> getCountBooksByRating() {
		return countBooksByRating;
	}
	
	public void load()  {
		log.info("Method start");
		System.out.println(reviewFacade);
		System.out.println(bookFacade);
		for (int i = 1; i<6 ; i++) {
			countBooksByRating.put(i, reviewFacade.findCountBooksByRating(i));
		}


		log.info("Method done");
	}
	
	

}
