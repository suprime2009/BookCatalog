package com.softserveinc.booklibrary.rest.client;

import javax.ejb.Remote;

import com.softserveinc.booklibrary.rest.dto.AuthorDTO;
import com.softserveinc.booklibrary.session.persist.home.BookHomeRemote;

@Remote(AuthorClientRemote.class)
public interface AuthorClientRemote {
	
	public boolean createAuthor(AuthorDTO dto);
}
