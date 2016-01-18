package com.softserveinc.model.session.manager;

import java.util.List;

import com.softserveinc.model.persist.entity.Author;

public interface IManagerAuthor {
	
	void createAuthor(Author author);
	
	void updateAuthor(Author author);
	
	void removeAuthor(Author author);
	
	Author getAuthorById(String id);
	
	List<Author> getAllAuthors();
	
	List<Author> getAuthorsWithAverageRating(int rating);
	
	void removeAuthorWithNoBooks(Author author);
	
	void removeAll(List<Author> list);
	

	
	
	
}
