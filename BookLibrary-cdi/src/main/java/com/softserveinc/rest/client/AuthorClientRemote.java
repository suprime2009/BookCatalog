package com.softserveinc.rest.client;

import javax.ejb.Remote;

import com.softserveinc.model.persist.dto.AuthorDTO;
import com.softserveinc.model.persist.home.BookHomeRemote;

@Remote(AuthorClientRemote.class)
public interface AuthorClientRemote {
	
	public boolean createAuthor(AuthorDTO dto);
}
