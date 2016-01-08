package com.softserveinc.model.persist.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum BookConstantsHolder implements EntityConstant {
	
	INSTANCE,
	ID("idBook", "idBook"), BOOK_NAME("bookName", "Book name"), CREATED_DATE("createdDate", "Created date"), ISBN(
			"isbn", "ISBN Number"), YEAR_PUBLISHED("yearPublished", "Year of published"), PUBLISHER("publisher",
					"Publisher"), AUTHORS("authors", "Authors"), RATING("rating", "Average rating");
	
	private String BUSINESS_VIEW;
	private String UI_VIEW;
	
	public static EntityConstant getInstance() {
		return INSTANCE;
	}
	
	@Override
	public String getBusinessView() {
		return BUSINESS_VIEW;
	}

	@Override
	public String getUIView() {
		return UI_VIEW;
	}

	@Override
	public List<EntityConstant> getListConstants() {
		List<EntityConstant> list = new ArrayList<EntityConstant>();
		list.add(ID);
		list.add(BOOK_NAME);
		list.add(CREATED_DATE);
		list.add(ISBN);
		list.add(YEAR_PUBLISHED);
		list.add(PUBLISHER);
		list.add(AUTHORS);
		list.add(RATING);
		
		return list;
	}
	
	@Override
	public BookConstantsHolder getConsant(String constant) {
		switch (constant) {
		case "idBook":
			return ID;

		case "bookName":
			return BOOK_NAME;

		case "createdDate":
			return CREATED_DATE;

		case "isbn":
			return ISBN;

		case "yearPublished":
			return YEAR_PUBLISHED;

		case "publisher":
			return PUBLISHER;

		case "authors":
			return AUTHORS;

		case "rating":
			return RATING;
		default:
			throw new IllegalArgumentException();
		}

	}

//	public String businessView(BookConstantsHolder object) {
//		switch (object) {
//		case ID:
//			return ID.businessView;
//
//		case BOOK_NAME:
//			return BOOK_NAME.businessView;
//
//		case CREATED_DATE:
//			return CREATED_DATE.businessView;
//
//		case ISBN:
//			return ISBN.businessView;
//			
//		case YEAR_PUBLISHED:
//			return YEAR_PUBLISHED.businessView;
//
//		case PUBLISHER:
//			return PUBLISHER.businessView;
//
//		case AUTHORS:
//			return AUTHORS.businessView;
//
//		case RATING:
//			return RATING.businessView;
//			
//		default:
//			throw new IllegalArgumentException();
//		}
//
//	}
//
//
//	
//	public String uiView(BookConstantsHolder object) {
//		switch (object) {
//		case ID:
//			return ID.UIView;
//
//		case BOOK_NAME:
//			return BOOK_NAME.UIView;
//
//		case CREATED_DATE:
//			return CREATED_DATE.UIView;
//
//		case ISBN:
//			return ISBN.UIView;
//
//		case YEAR_PUBLISHED:
//			return YEAR_PUBLISHED.businessView;
//
//		case PUBLISHER:
//			return PUBLISHER.UIView;
//
//		case AUTHORS:
//			return AUTHORS.UIView;
//
//		case RATING:
//			return RATING.UIView;
//		default:
//			throw new IllegalArgumentException();
//		}
//
//	}


	private BookConstantsHolder(String businessView, String uIView) {
		this.BUSINESS_VIEW = businessView;
		this.UI_VIEW = uIView;
	}
	
	private BookConstantsHolder() {
	}
	

	
	

}
