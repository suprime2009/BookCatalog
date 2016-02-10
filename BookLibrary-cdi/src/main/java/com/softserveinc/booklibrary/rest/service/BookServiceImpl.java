package com.softserveinc.booklibrary.rest.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.booklibrary.exception.BookCatalogException;
import com.softserveinc.booklibrary.exception.BookManagerException;
import com.softserveinc.booklibrary.exception.RestDTOConvertException;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.rest.dto.AuthorDTO;
import com.softserveinc.booklibrary.rest.dto.BookDTO;
import com.softserveinc.booklibrary.session.manager.BookManagerLocal;
import com.softserveinc.booklibrary.session.persist.facade.AuthorFacadeLocal;
import com.softserveinc.booklibrary.session.persist.facade.BookFacadeLocal;

@Stateless
public class BookServiceImpl implements BookService {

	private static Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

	@EJB
	private BookFacadeLocal bookFacade;

	@EJB
	private BookManagerLocal bookManager;

	@EJB
	private AuthorFacadeLocal authorFacade;

	@EJB
	private AuthorService authorService;

	@Override
	public Response findById(String id) {
		if (id == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Book book = bookFacade.findById(id);
		if (book == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		BookDTO dto = convertToDTO(book);
		return Response.ok(dto).build();
	}

	@Override
	public Response findAll() {
		List<Book> list = bookFacade.findAll();
		List<BookDTO> dto = convertToListDTO(list);
		return Response.ok(dto).build();
	}

	@Override
	public Response create(BookDTO bookDTO) {

		Response.ResponseBuilder builder = null;

		if (bookDTO == null || bookDTO.getIdBook() != null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Book book = null;
		try {
			book = convertToEntity(bookDTO);
			bookManager.createBook(book);
			builder = Response.status(Status.CREATED);
		} catch (BookManagerException e) {
			builder = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage());
		} catch (RestDTOConvertException e) {
			builder = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage());
		}
		return builder.build();
	}

	@Override
	public Response update(BookDTO bookDTO) {
		Response.ResponseBuilder builder = null;
		if (bookDTO == null || bookDTO.getIdBook() == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Book book = null;
		try {
			book = convertToEntity(bookDTO);
			bookManager.updateBook(book);
			builder = Response.status(Status.OK);
		} catch (BookManagerException e) {
			builder = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage());
		} catch (RestDTOConvertException e) {
			builder = Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage());
		}
		return builder.build();
	}

	@Override
	public Response deleteById(String id) {
		try {
			bookManager.deleteBook(id);
		} catch (BookManagerException e) {
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
		List<BookDTO> dto = convertToListDTO(bookFacade.findBooksByAuthor(author));
		return Response.ok(dto).build();
	}

	@Override
	public Response getReviewsByRating(Integer rating) {
		if (rating == 0) {
			rating = null;
		} else {
			if (rating < 1 || rating > 5) {
				return Response.status(Status.BAD_REQUEST).build();
			}
		}
		List<BookDTO> dto = convertToListDTO(bookFacade.findBooksByRating(rating));
		return Response.ok(dto).build();
	}

	@Override
	public Book convertToEntity(BookDTO dto) throws RestDTOConvertException {
		String error = "";
		Book book = new Book(null, dto.getBookName(), dto.getIsbn(), dto.getPublisher(), dto.getYearPublished());
		if (dto.getIdBook() != null) {
			Book checkBook = bookFacade.findById(dto.getIdBook());
			if (checkBook == null) {
				error = String.format("By id %s no one book has been found.", dto.getIdBook());
				log.error(error);
				throw new RestDTOConvertException(error);
			} else {
				book.setCreatedDate(checkBook.getCreatedDate());
				book.setIdBook(dto.getIdBook());
			}
		}
		if (dto.getAuthors() != null) {
			book.setAuthors(new HashSet<Author>(convertAuthors(dto.getAuthors())));
		}
		log.debug("The method done. DTO has been converted to book = {}", book);
		return book;
	}

	private List<Author> convertAuthors(List<AuthorDTO> dto) throws RestDTOConvertException {
		String error = "";
		List<Author> authors = new ArrayList<Author>();
		Author author = null;
		for (AuthorDTO a : dto) {
			if (a == null || a.getIdAuthor() == null) {
				error = String.format("The author %s is null or missed id.", a);
				log.error(error);
				throw new RestDTOConvertException();
			}
			author = authorFacade.findById(a.getIdAuthor());
			if (author == null) {
				error = String.format("By id %s no one author has been found.", a.getIdAuthor());
				log.error(error);
				throw new RestDTOConvertException();
			}
			if (!author.getFirstName().equals(a.getFirstName()) || !author.getSecondName().equals(a.getSecondName())) {
				error = String.format("The author %s doesn't match current author in db.", a);
				log.error(error);
				throw new RestDTOConvertException();
			}
			authors.add(author);
		}
		return authors;
	}

	@Override
	public BookDTO convertToDTO(Book object) {

		List<AuthorDTO> authorDto = new ArrayList<AuthorDTO>();
		for (Author a : object.getAuthors()) {
			authorDto.add(new AuthorDTO(a.getIdAuthor(), a.getFirstName(), a.getSecondName()));
		}

		BookDTO dto = new BookDTO(object.getIdBook(), object.getBookName(), object.getIsbn(), object.getPublisher(),
				object.getYearPublished(), authorDto);
		return dto;
	}

	@Override
	public List<Book> convertToListEntities(List<BookDTO> listDTO) throws RestDTOConvertException {
		List<Book> books = new ArrayList<Book>();
		for (BookDTO d : listDTO) {
			books.add(convertToEntity(d));
		}
		return books;
	}

	@Override
	public List<BookDTO> convertToListDTO(List<Book> list) {
		List<BookDTO> authorDto = new ArrayList<>();
		for (Book b : list) {
			authorDto.add(convertToDTO(b));
		}
		return authorDto;
	}

}
