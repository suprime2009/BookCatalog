package com.softserveinc.action.managebook;

import java.io.Serializable;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.Review;
import com.softserveinc.model.persist.facade.BookFacade;
import com.softserveinc.model.persist.facade.BookFacadeLocal;
import com.softserveinc.model.persist.facade.ReviewFacadeLocal;
import com.softserveinc.model.session.manager.ReviewManagerLocal;

@ManagedBean
@RequestScoped
public class CreateReviewAction implements Serializable {
	
	private String commenterName;
	private String comment;
	private Integer rating;
	private String idBook;
	
	
	
	@EJB
	public ReviewFacadeLocal reviewFacade;
	
	@EJB
	private ReviewManagerLocal reviewManager;
	
	@EJB
	BookFacadeLocal bookFacade;

	
	public CreateReviewAction() {

		System.out.println("CreateReviewAction bean created");
	}
	
	public void submitComment() {
		System.out.println("submitComment start");
		System.out.println(commenterName);
		System.out.println(comment);
		System.out.println(rating);
		Book book = bookFacade.findById(idBook);
		Review review = new Review(comment, commenterName, rating, book);

		reviewManager.addNewReview(review);
		System.out.println("submitComment done");
	}
	
	public void getBookForReview(ActionEvent event){
		 System.out.println("attrListener method");
		idBook = (String)event.getComponent().getAttributes().get("reviewedBook");
	 
	}

	
	
	
	public String getCommenterName() {
		return commenterName;
	}



	public void setCommenterName(String commenterName) {
		this.commenterName = commenterName;
	}



	public String getComment() {
		return comment;
	}



	public void setComment(String comment) {
		this.comment = comment;
	}



	public Integer getRating() {
		return rating;
	}



	public void setRating(Integer rating) {
		this.rating = rating;
	}
}
