package com.softserveinc.booklibrary.action.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("CreateDateConverter")
public class CreateDateConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy hh:mm a", Locale.US);
		return dateFormat.format((Date) value);
	}

}
