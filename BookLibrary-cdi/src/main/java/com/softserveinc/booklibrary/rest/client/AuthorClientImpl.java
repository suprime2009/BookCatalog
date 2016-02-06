package com.softserveinc.booklibrary.rest.client;

import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import com.softserveinc.booklibrary.exception.BookCatalogException;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.rest.dto.AuthorDTO;
import com.softserveinc.booklibrary.rest.dto.BookDTO;

@Named
@RequestScoped
public class AuthorClientImpl implements AuthorClient {

	private static final String TARGET = "http://localhost:8080/BookLibrary-cdi/rest/author";
	private static final Client CLIENT = ClientBuilder.newClient();

	@Override
	public AuthorDTO findById(String id) throws BookCatalogException {
		Response response = CLIENT.target(TARGET).path(id).request().get();

		if (response.getStatus() == 200) {
			return response.readEntity(AuthorDTO.class);
		} else {
			throw new BookCatalogException(response.getEntity().toString());
		}

	}
	
	@Override
	public List<AuthorDTO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(AuthorDTO author) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(AuthorDTO author) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(String idAuthor) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<BookDTO> getBooksByAuthor(String authorId) {
		// TODO Auto-generated method stub
		return null;
	}

}
