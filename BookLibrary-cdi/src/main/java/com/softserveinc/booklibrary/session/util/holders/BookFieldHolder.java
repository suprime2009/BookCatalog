package com.softserveinc.booklibrary.session.util.holders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum BookFieldHolder implements EntityFieldHolder {
	
	ID("idBook", "idBook"), BOOK_NAME("bookName", "Book name"), CREATED_DATE("createdDate", "Created date"), ISBN(
			"isbn", "ISBN Number"), YEAR_PUBLISHED("yearPublished", "Year of published"), PUBLISHER("publisher",
					"Publisher"), AUTHORS("authors", "Authors"), RATING("rating", "Average rating"), REVIEWS("reviews", "Reviews");
	
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

	private BookFieldHolder(String businessView, String uIView) {
		this.BUSINESS_VIEW = businessView;
		this.UI_VIEW = uIView;
	}
}
