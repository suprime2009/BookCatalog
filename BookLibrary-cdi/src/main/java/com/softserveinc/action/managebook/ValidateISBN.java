package com.softserveinc.action.managebook;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public interface ValidateISBN {

	public void validateISBN(FacesContext context, UIComponent comp, Object value);
}
