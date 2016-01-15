package com.softserveinc.action.managebook;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.softserveinc.model.persist.entity.Review;
import com.softserveinc.model.session.manager.ReviewManagerLocal;

@ManagedBean
@RequestScoped
public class ReviewAction implements Serializable {
	
//	private String commenterName;
//	private String comment;
//	private Integer rating;
//	private String idBook;
//	private String selectedReviewId;
//	
//	@EJB
//	private ReviewManagerLocal reviewManager;
//	
//	public void getBookForReview(ActionEvent event){
//		 System.out.println("attrListener method");
//		idBook = (String)event.getComponent().getAttributes().get("reviewedBook");
//	 
//	}
//	
//
//	
//	private void showMessageOnUI(String message) {
//		FacesContext context = FacesContext.getCurrentInstance();
//		FacesMessage facesMessage = new FacesMessage(message);
//		context.addMessage(null, facesMessage);
//	}
//
//	public String getCommenterName() {
//		return commenterName;
//	}
//
//	public void setCommenterName(String commenterName) {
//		this.commenterName = commenterName;
//	}
//
//	public String getComment() {
//		return comment;
//	}
//
//	public void setComment(String comment) {
//		this.comment = comment;
//	}
//
//	public Integer getRating() {
//		return rating;
//	}
//
//	public void setRating(Integer rating) {
//		this.rating = rating;
//	}
//
//	public String getSBookId() {
//		return idBook;
//	}
//
//	public void setBookId(String idBook) {
//		this.idBook = idBook;
//	}
//
//	public String getSelectedReviewId() {
//		return selectedReviewId;
//	}
//
//	public void setSelectedReviewId(String selectedReviewId) {
//		this.selectedReviewId = selectedReviewId;
//	}
//	
//	
//	
	

}
