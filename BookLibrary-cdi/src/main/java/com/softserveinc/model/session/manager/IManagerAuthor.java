package com.softserveinc.model.session.manager;

import java.util.List;

import com.softserveinc.exception.AuthorManagerException;
import com.softserveinc.exception.BookCatalogException;
import com.softserveinc.model.persist.entity.Author;

public interface IManagerAuthor {
	
	void createAuthor(Author author) throws AuthorManagerException, BookCatalogException;
	
	void updateAuthor(Author author) throws AuthorManagerException;
	
	void deleteAuthor(String idAuthor) throws AuthorManagerException;
	
	void deleteAuthorWithNoBooks(String idAuthor) throws AuthorManagerException;
	
	void bulkDeleteAuthorsWithNoBooks(List<Author> list) throws AuthorManagerException;
		
}
