package com.softserveinc.booklibrary.action.helper;

public enum ReviewRatingFieldsEnum {
	
 ONE_STAR("one star",1), 
	TWO_STARS("two stars",2), THREE_STAR("three stars",3), FOUR_STAR("four stars",4), FIVE_STAR("five stars",5);
	
	private String UIview;
	private int ratingValue;
	
	private ReviewRatingFieldsEnum(String UIview, int ratingValue) {
		this.UIview = UIview;
		this.ratingValue = ratingValue;
	}

	public String getRatingName() {
		return UIview;
	}

	public int getRatingValue() {
		return ratingValue;
	}
}
