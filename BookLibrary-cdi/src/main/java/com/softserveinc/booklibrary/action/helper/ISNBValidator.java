package com.softserveinc.booklibrary.action.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * This class is a Book ISBN validator. The class implements JSF interface
 * {@link Validator}. The class implemets method
 * {@code validate(FacesContext context, UIComponent component, Object value)},
 * this method runs after convert ISBN action and checks incoming ISBN in
 * accordance with the regex.
 *
 */
@FacesValidator("ISNBValidator")
public class ISNBValidator implements Validator {

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

			FacesMessage message = new FacesMessage("ISBN  valid " + val);
			System.out.println(true);
			context.addMessage(component.getClientId(context), message);
		}

	}

}
