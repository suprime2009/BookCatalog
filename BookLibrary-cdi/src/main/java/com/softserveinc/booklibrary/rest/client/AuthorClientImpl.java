package com.softserveinc.booklibrary.rest.client;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.softserveinc.booklibrary.exception.BookCatalogException;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.rest.dto.AuthorDTO;
import com.softserveinc.booklibrary.rest.dto.BookDTO;

@Stateless
public class AuthorClientImpl implements AuthorClient {

	private static final String TARGET = "http://localhost:8080/BookLibrary-cdi/rest/author/";
	private static final Client CLIENT = ClientBuilder.newClient();

	@Override
	public AuthorDTO findById(String id) throws BookCatalogException {
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/BookLibrary-cdi/rest/author/");
		Response response = target.path(id).request().get();

		if (response.getStatus() == 200) {
			response.close();
			return response.readEntity(AuthorDTO.class);
			
		} else {
			response.close();
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
