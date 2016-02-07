package com.softserveinc.booklibrary.action.helper;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * This class is a converter for Book ISBN number. The method parsing incoming
 * String, looking for digits, check on correct contains of digits.
 *
 */
@FacesConverter("ISNBConverter")
public class ISBNConverter implements Converter {

	private final Character SEP = '-';

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		StringBuilder isbn = new StringBuilder();
		String isbnCode = getOnlyDigits((String) value);
		switch (isbnCode.length()) {
		case 10:
			isbn.append("ISBN-10: ").append(isbnCode.charAt(0)).append(SEP).append(isbnCode.substring(1, 4)).append(SEP)
					.append(isbnCode.substring(4, 9)).append(SEP).append(isbnCode.charAt(9));
			break;
		case 13:
			isbn.append("ISBN-13: ").append(isbnCode.substring(0, 3)).append(SEP).append(isbnCode.charAt(3)).append(SEP)
					.append(isbnCode.substring(4, 7)).append(SEP).append(isbnCode.substring(7, 12)).append(SEP)
					.append(isbnCode.charAt(12));
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

	/**
	 * This method parse string and looking for digits. The method used for ISBN
	 * number, method ignore first entry digits 10 and 13.
	 * 
	 * @param value
	 *            String
	 * @return String
	 */
	private String getOnlyDigits(String value) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < value.length(); i++) {
			char ch = value.charAt(i);
			if (Character.isDigit(ch)) {
				sb.append(ch);
			}
		}

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
