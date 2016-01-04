package com.softserveinc.action.managebook;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.facade.AuthorFacadeLocal;

@ManagedBean(name = "authorDetailAction")
@RequestScoped
public class AuthorDetailAction implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8458126858022646610L;

	@EJB
	AuthorFacadeLocal authorFacade;
	
	private String selectedId;
	private Author author;
	
	public void loadAuthor() {
		author = authorFacade.findById(selectedId);
	}
	public String getSelectedId() {
		return selectedId;
	}
	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}

}
