package com.softserveinc.booklibrary.rest.client;

import java.util.List;

import com.softserveinc.booklibrary.exception.BookCatalogException;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.rest.dto.AuthorDTO;
import com.softserveinc.booklibrary.rest.dto.BookDTO;

public interface AuthorClient {

	public AuthorDTO findById(String id) throws BookCatalogException;

	public List<AuthorDTO> findAll() throws BookCatalogException;

	public void create(AuthorDTO author) throws BookCatalogException;

	public void update(AuthorDTO author) throws BookCatalogException;

	public void deleteById(String idAuthor) throws BookCatalogException;

	public List<BookDTO> getBooksByAuthor(String authorId) throws BookCatalogException;

}
