package com.softserveinc.action.managebook;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * Common interface for actions, that operate with ISBN number.
 * Interface provides validation on existing ISBN Number.
 *
 */
public interface ValidateISBN {

	/**
	 * Method check if passed form UI ISBN Number already exist.
	 * Method sets FacesMesage after finishing. 
	 * @param context
	 * @param comp
	 * @param value ISBN from UI.
	 */
	public void validateIfExistISBN(FacesContext context, UIComponent comp, Object value);
}
