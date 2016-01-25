package com.softserveinc.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.exception.AuthorManagerException;
import com.softserveinc.exception.BookCatalogException;
import com.softserveinc.exception.BookManagerException;
import com.softserveinc.exception.ReviewManagerException;
import com.softserveinc.model.persist.dto.AuthorDTO;
import com.softserveinc.model.persist.dto.BookDTO;
import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.Review;
import com.softserveinc.model.persist.facade.AuthorFacadeLocal;
import com.softserveinc.model.persist.facade.BookFacadeLocal;
import com.softserveinc.model.session.manager.AuthorManagerLocal;

@Stateless
public class AuthorServiceImpl implements AuthorService {

	private static Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);

	@EJB
	private AuthorFacadeLocal authorFacade;

	@EJB
	private BookFacadeLocal bookFacade;

	@EJB
	private AuthorManagerLocal authorManager;

	@Override
	public Response findById(String id) {
		if (id == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Author author = authorFacade.findById(id);
		if (author == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		AuthorDTO dto = convertToDTO(author);
		return Response.ok(dto).build();
	}

	@Override
	public Response findAll() {
		List<Author> list = authorFacade.findAll();
		List<AuthorDTO> dto = convertToListDTO(list);
		return Response.ok(dto).build();
	}

	@Override
	public Response create(AuthorDTO authorDTO) {
		Response.ResponseBuilder builder = null;
		if (authorDTO == null || authorDTO.getIdAuthor() != null) {
			System.out.println("BAD REQUEST");
			return Response.status(Status.BAD_REQUEST).build();
		}

		Author author = convertToEntity(authorDTO);
		try {
			authorManager.createAuthor(author);
			builder = Response.status(Status.CREATED);
		} catch (AuthorManagerException e) {
			builder = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage());
		} catch (BookCatalogException e) {
			builder = Response.status(Response.Status.NOT_IMPLEMENTED).entity(e.getMessage());
		}
		return builder.build();
	}

	@Override
	public Response update(AuthorDTO authorDTO) {
		Response.ResponseBuilder builder = null;
		if (authorDTO == null || authorDTO.getIdAuthor() == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Author author = convertToEntity(authorDTO);
		try {
			authorManager.updateAuthor(author);
			builder = Response.status(Status.OK);
		} catch (AuthorManagerException e) {
			builder = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage());
		}
		return builder.build();
	}

	@Override
	public Response deleteById(String id) {
		try {
			authorManager.deleteAuthor(id);
		} catch (AuthorManagerException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

		return Response.ok().build();
	}

	@Override
	public Author convertToEntity(AuthorDTO dto) {
		Author author = new Author(dto.getFirstName(), dto.getSecondName());
		author.setIdAuthor(dto.getIdAuthor());
		List<String> idBooks = new ArrayList<String>();
		if (dto.getBooks() != null) {
			for (BookDTO b : dto.getBooks()) {
				idBooks.add(b.getIdBook());
			}
			List<Book> books = bookFacade.findBooksByListId(idBooks);
			author.setBooks(books);
		}
		return author;
	}

	@Override
	public AuthorDTO convertToDTO(Author object) {
		AuthorDTO authorDTO = new AuthorDTO(object.getIdAuthor(), object.getFirstName(), object.getSecondName());
		List<BookDTO> booksDto = new ArrayList<BookDTO>();
		for (Book b : object.getBooks()) {

			booksDto.add(
					new BookDTO(b.getIdBook(), b.getBookName(), b.getIsbn(), b.getPublisher(), b.getYearPublished()));
		}
		authorDTO.setBooks(booksDto);
		return authorDTO;
	}

	@Override
	public List<Author> convertToListEntities(Collection<AuthorDTO> listDTO) {
		List<Author> authors = new ArrayList<Author>();
		for (AuthorDTO d : listDTO) {
			authors.add(convertToEntity(d));
		}
		return authors;
	}

	@Override
	public List<AuthorDTO> convertToListDTO(Collection<Author> list) {
		List<AuthorDTO> authorsDto = new ArrayList<AuthorDTO>();
		for (Author a : list) {
			authorsDto.add(convertToDTO(a));
		}
		return authorsDto;
	}

}
