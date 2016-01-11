package com.softserveinc.model.session.managerimpl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.facade.AuthorFacadeLocal;
import com.softserveinc.model.persist.home.AuthorHomeLocal;
import com.softserveinc.model.session.manager.AuthorManagerLocal;
import com.softserveinc.model.session.manager.AuthorManagerRemote;

@Stateless
public class AuthorManager implements AuthorManagerLocal, AuthorManagerRemote {
	
	@Inject
	AuthorHomeLocal authorHomeLocal;
	
	@Inject
	AuthorFacadeLocal authorFacadeLocal;

	@Override
	public void createAuthor(Author author) {
		authorHomeLocal.create(author);
	}

	@Override
	public void updateAuthor(Author author) {
		authorHomeLocal.update(author);
		
	}

	@Override
	public void removeAuthor(Author author) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Author getAuthorById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Author> getAllAuthors() {
		return authorHomeLocal.findAll();
	}

	@Override
	public List<Author> getAuthorsWithAverageRating(int rating) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAuthorWithNoBooks(Author author) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAll(List<Author> list) {
		// TODO Auto-generated method stub
		
	}

}
