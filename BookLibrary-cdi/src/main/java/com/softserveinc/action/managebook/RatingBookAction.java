package com.softserveinc.action.managebook;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class RatingBookAction {
	
	private double rating;
	
	@PostConstruct
	public void init() {
		rating = 2.0;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

}
