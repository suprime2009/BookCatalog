package com.softserveinc.action.managebook;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * This class is a action bean
 *
 */
@ManagedBean
@RequestScoped
public class CreateAuthorAction {
	
	private String firstName;
	private String secondName;
	
	public CreateAuthorAction() {
		
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	
	public void sumbit() {
		
	}

}
