package com.softserveinc.booklibrary.session.util.holders;



public enum AuthorFieldHolder implements EntityFieldHolder {
	ID("idAuthor", "idAuthor"), CREATED_DATE("createdDate", "Created date"), FIRST_NAME("firstName",
			"First name"), SECOND_NAME("secondName", "Second name"), BOOKS("books", "Author books"), AVE_RATING("averageRating", "Average raing"),
	COUNT_BOOKS("countBooks", "Count books"), COUNT_REVIEWS("countReviews", "Count Reviews"), FULL_NAME("fullName", "Author name");

	private String BUSINESS_VIEW;
	private String UI_VIEW;

	private AuthorFieldHolder(String businessView, String uIView) {
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
