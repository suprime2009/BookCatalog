package com.softserveinc.action.managebook;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class NavigationController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8941295572408114932L;


	public String moveToManageBooks() {
		return "manageBooks";
	}
	

}
