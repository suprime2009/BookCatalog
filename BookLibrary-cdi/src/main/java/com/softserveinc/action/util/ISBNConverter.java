package com.softserveinc.action.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("ISNBConverter")
public class ISBNConverter implements Converter {

private final Character SEP = '-';
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		System.out.println("getAsObject");
		System.out.println("getAsObject");
		System.out.println("getAsObject");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		String val = (String) value;
		StringBuilder isbn = new StringBuilder();
		String isbnCode = getOnlyDigits(val);
		
		switch (isbnCode.length()) {
		case 10:
			isbn.append("ISBN-10: ");
			isbn.append(isbnCode.charAt(0));
			isbn.append(SEP);
			isbn.append(isbnCode.substring(1, 4));
			isbn.append(SEP);
			isbn.append(isbnCode.substring(4, 9));
			isbn.append(SEP);
			isbn.append(isbnCode.charAt(9));

			break;
		case 13:
			isbn.append("ISBN-13: ");
			isbn.append(isbnCode.substring(0, 3));
			isbn.append(SEP);
			isbn.append(isbnCode.charAt(3));
			isbn.append(SEP);
			isbn.append(isbnCode.substring(4, 7));
			isbn.append(SEP);
			isbn.append(isbnCode.substring(7, 12));
			isbn.append(SEP);
			isbn.append(isbnCode.charAt(12));
			break;

		default:
			((UIInput) component).setValid(false);

			FacesMessage message = new FacesMessage("ISBN code must contains 10 or 13 digits. ");
			context.addMessage(component.getClientId(context), message);
		}
		return isbn.toString();
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value.toString();
	}

	private String getOnlyDigits(String value) {
		System.out.println("in");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < value.length(); i++) {
			char ch = value.charAt(i);
			if (Character.isDigit(ch)) {
				sb.append(ch);
			}
		}
		System.out.println("in");

		if (sb.length() > 2) {
		if (sb.charAt(0) == '1') {
			System.out.println("1");
			if (sb.charAt(1) == '0' || sb.charAt(1) == '3') {
				System.out.println("0 or 3");
				sb.deleteCharAt(0);
				sb.deleteCharAt(0);
			}
		}
		}

		return sb.toString();
	}

}
