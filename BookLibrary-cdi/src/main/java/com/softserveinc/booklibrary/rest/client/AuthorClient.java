package com.softserveinc.booklibrary.rest.client;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.softserveinc.booklibrary.rest.dto.AuthorDTO;

@Stateless
public class AuthorClient implements AuthorClientRemote {

	public static final String REST_URL = "http://localhost:8080/BookLibrary-cdi/rest/";

	@Override
	public boolean createAuthor(AuthorDTO dto) {
		Client client = ClientBuilder.newClient();
		Response response = client.target(REST_URL + "author").request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON), Response.class);

		
		if (response.getStatus() == Status.CREATED.getStatusCode()) {
			return true;
		} else {
			return false;
		}

	}

}
