package com.softserveinc.model.util;

public class TestFieldsHolder {

	public static final String CHRIS = "Chris";
	public static final String DEVIS = "Devis";
	public static final String LESS_TWO_CHARACTERS = "M";
	public static final String MORE_EIGHTY_CHARACTERS = appendCharacters(80);

	private static String appendCharacters(int count) {
		StringBuilder sb = new StringBuilder();
		sb.append("M");
		for (int i = 0; i < count - 1; i++) {
			sb.append("m");
		}
		return sb.toString();
	}

}
