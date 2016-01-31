package com.softserveinc.booklibrary.session.util.holders;

public enum ReviewFieldHolder implements EntityFieldHolder {

	ID("idreview", "idreview"), RATING("rating", "Rating"), BOOK("book", "Book"), CREATED("createdDate",
			"Created: "), COMMENT("comment", "Comment"), COMMENTER_NAME("commenterName", "Commenter name");

	private String BUSINESS_VIEW;
	private String UI_VIEW;

	private ReviewFieldHolder(String businessView, String uIView) {
		this.BUSINESS_VIEW = businessView;
		this.UI_VIEW = uIView;
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
