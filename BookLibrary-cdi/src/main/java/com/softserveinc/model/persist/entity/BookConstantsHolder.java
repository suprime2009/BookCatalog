package com.softserveinc.model.persist.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum BookConstantsHolder implements EntityConstant {
	
	ID("idBook", "idBook"), BOOK_NAME("bookName", "Book name"), CREATED_DATE("createdDate", "Created date"), ISBN(
			"isbn", "ISBN Number"), YEAR_PUBLISHED("yearPublished", "Year of published"), PUBLISHER("publisher",
					"Publisher"), AUTHORS("authors", "Authors"), RATING("rating", "Average rating");
	
	private String BUSINESS_VIEW;
	private String UI_VIEW;
	

	
	@Override
	public String getBusinessView() {
		return BUSINESS_VIEW;
	}

	@Override
	public String getUIView() {
		return UI_VIEW;
	}

	private BookConstantsHolder(String businessView, String uIView) {
		this.BUSINESS_VIEW = businessView;
		this.UI_VIEW = uIView;
	}
	
	private BookConstantsHolder() {
	}
	

	
	

}
