package com.softserveinc.model.persist.entity;

public class IsbnNumber {
	
	private String isbnDigits;

	public String getIsbnDigits() {
		return isbnDigits;
	}

	public void setIsbnDigits(String isbnDigits) {
		this.isbnDigits = isbnDigits;
	}

	public IsbnNumber(String isbnDigits) {
		super();
		this.isbnDigits = isbnDigits;
	}
	
	

}
