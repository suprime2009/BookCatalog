package com.softserveinc.action.managebook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.facade.BookFacadeLocal;

/**
 * Class 
 *
 */
@FacesConverter("ISNBValidatorController")
@FacesValidator("ISNBValidatorController")
public class ISNBValidatorController implements Converter, Validator {

	private final Character SEP = '-';
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
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

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String regex = "^(ISBN(-1[03]): )(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$";
		Pattern pattern = Pattern.compile(regex);
		String val = (String) value;
		Matcher matcher = pattern.matcher(val);
		if (!matcher.matches()) {
			((UIInput) component).setValid(false);
			FacesMessage message = new FacesMessage("ISBN not valid");
			context.addMessage(component.getClientId(context), message);
		} else {
				((UIInput) component).setValid(true);
				FacesMessage message = new FacesMessage("ISBN  valid " + val);
				System.out.println(true);
				context.addMessage(component.getClientId(context), message);
			}
		
	}

//	private boolean checkExistingISBNNumber(String isbn) {
//		System.out.println(isbn);
//		Book checkBook = bookFacade.findBookByISNBN(isbn);
//		if (checkBook == null) {
//			return false;
//		} else if (book != null && book.getIsbn().equals(isbn)) {
//			return false;
//		} else {
//			return true;
//		}
//
//	}

}
