package com.softserveinc.action.managebook;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class NavigationAction {
	
	private String includedURL;
	
	public String getIncludedURL() {
		return includedURL;
	}
	
	public void setIncludedURL(String includedURL) {
		System.out.println("setIncludedURL");
		this.includedURL = includedURL;
	}
	
	public void createBookURL() {
		includedURL = "createBook.xhtml";
	}
	
	public void editBookURL() {
		includedURL = "editBook.xhtml";
	}
	
	public void hideIncludedURL() {
		System.out.println("hideIncludedURL");
		includedURL = null;
	}
	
	public String moveToEditBook() {
		return "editBook.xhtml";
	}
	
	public String moveToBookDetailPage() {
		return "bookDetails.xhtml";
	}
	
	public String moveToManageBooks() {
		return "manageBooks.xhtml?faces-redirect=true&amp;";
	}
	
	public String moveToHomePage() {
		return "welcome.xhtml";
	}
	
	public String moveToManageAuthors() {
		return "manageAuthors.xhtml";
	}
	
	public String moveToManageReviews() {
		return "manageReviews.xhtml";
	}
	public String moveToAuthorDetailPage(){
		return "authorDetail.xhtml";
	}

}
