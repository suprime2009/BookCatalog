package com.softserveinc.booklibrary.rest.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.booklibrary.exception.AuthorManagerException;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.rest.dto.AuthorDTO;
import com.softserveinc.booklibrary.rest.dto.BookDTO;
import com.softserveinc.booklibrary.session.manager.AuthorManagerLocal;
import com.softserveinc.booklibrary.session.persist.facade.AuthorFacadeLocal;
import com.softserveinc.booklibrary.session.persist.facade.BookFacadeLocal;

/**
 * This class is a implementation methods used JAX-RS web service for Authors
 * entity. These methods are available to web service clients and using them can
 * perform CRUD operations on {@code BookCatalog API}.
 *
 */
@Stateless
public class AuthorServiceImpl implements AuthorService {

	private static Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);

	@EJB
	private AuthorFacadeLocal authorFacade;

	@EJB
	private BookFacadeLocal bookFacade;

	@EJB
	private AuthorManagerLocal authorManager;

	@EJB
	private BookService bookService;

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
		if (authorDTO == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Author author;
		try {
			author = convertToEntity(authorDTO);
			authorManager.createAuthor(author);
			builder = Response.status(Status.CREATED);
		} catch (AuthorManagerException e) {
			builder = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage());
		}
		return builder.build();
	}

	@Override
	public Response update(AuthorDTO authorDTO) {
		Response.ResponseBuilder builder = null;
		if (authorDTO == null || authorDTO.getFirstName() == null || authorDTO.getSecondName() == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Author author = null;
		try {
			author = convertToEntity(authorDTO);
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
			authorManager.deleteAuthorWithNoBooks(id);
		} catch (AuthorManagerException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

		return Response.ok().build();
	}

	@Override
	public Response getBooksByAuthor(String idAuthor) {
		if (idAuthor == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Author author = authorFacade.findById(idAuthor);
		if (author == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		List<BookDTO> dto = bookService.convertToListDTO(bookFacade.findBooksByAuthor(author));
		return Response.ok(dto).build();
	}

	@Override
	public Author convertToEntity(AuthorDTO dto) {
		Author author = new Author(dto.getFirstName(), dto.getSecondName());
		if (dto.getIdAuthor() != null) {
			author.setIdAuthor(dto.getIdAuthor());
			Author authorForCreateDate = authorFacade.findById(dto.getIdAuthor());
			if (authorForCreateDate != null) {
				author.setCreatedDate(authorForCreateDate.getCreatedDate());
			}
		}
		return author;
	}

	@Override
	public AuthorDTO convertToDTO(Author object) {
		AuthorDTO authorDTO = new AuthorDTO(object.getIdAuthor(), object.getFirstName(), object.getSecondName());
		return authorDTO;
	}

	@Override
	public List<Author> convertToListEntities(Collection<AuthorDTO> listDTO) {
		List<Author> authors = new ArrayList<Author>();
		if (listDTO == null) {
			return authors;
		}
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
