package com.softserveinc.action.util;

import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.*;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.facade.AuthorFacadeLocal;
import com.softserveinc.model.persist.facade.BookFacadeLocal;




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
