package com.softserveinc.model.persist.entity;

import java.util.ArrayList;
import java.util.List;

public enum AuthorConstantsHolder implements EntityConstant {
	INSTANCE, ID("idAuthor", "idAuthor"), CREATED_DATE("createdDate", "Created date"), FIRST_NAME("firstName", "First name"), 
	SECOND_NAME("secondName", "Second name"), BOOKS("books", "Author books");
	
	private String BUSINESS_VIEW;
	private String UI_VIEW;
	
	public static EntityConstant getInstance() {
		return INSTANCE;
	}
	
	private AuthorConstantsHolder(String businessView, String uIView) {
		this.BUSINESS_VIEW = businessView;
		this.UI_VIEW = uIView;
	}
	
	private AuthorConstantsHolder() {
	}

	@Override
	public String getBusinessView() {
		return BUSINESS_VIEW;
	}

	@Override
	public String getUIView() {
		return UI_VIEW;
	}
}
