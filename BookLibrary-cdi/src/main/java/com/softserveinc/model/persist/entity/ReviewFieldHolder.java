package com.softserveinc.model.persist.entity;

public enum ReviewFieldHolder implements EntityFieldHolder{
	
	RATING("rating", "Rating");

	private String BUSINESS_VIEW;
	private String UI_VIEW;
	
	private ReviewFieldHolder(String businessView, String uIView) {
		this.BUSINESS_VIEW = businessView;
		this.UI_VIEW = uIView;
	}
	
	@Override
	public String getBusinessView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUIView() {
		// TODO Auto-generated method stub
		return null;
	}

}
