package com.softserveinc.booklibrary.action.util;


import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.session.persist.facade.BookFacadeLocal;





@Named
@RequestScoped
public class BookConverter implements Converter {
	
	@EJB
	private BookFacadeLocal bookFacade;
	

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Book book = bookFacade.findById(value);
		return book;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Book book = (Book) value;
	return book.getIdBook();
	}
	
	

}
