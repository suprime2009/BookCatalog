package com.softserveinc.booklibrary.rest.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.rest.dto.AuthorDTO;

public class AuthorRestDTOConverter {

	public static Author convertToEntity(AuthorDTO dto) {
		Author author = new Author(dto.getFirstName(), dto.getSecondName());
		author.setIdAuthor(dto.getIdAuthor());
		return author;
	}

	public static AuthorDTO convertToDTO(Author object) {
		AuthorDTO authorDTO = new AuthorDTO(object.getIdAuthor(), object.getFirstName(), object.getSecondName());
		return authorDTO;
	}

	public static List<Author> convertToListEntities(Collection<AuthorDTO> listDTO) {
		List<Author> authors = new ArrayList<Author>();
		for (AuthorDTO d : listDTO) {
			authors.add(convertToEntity(d));
		}
		return authors;
	}

	public static List<AuthorDTO> convertToListDTO(Collection<Author> list) {
		List<AuthorDTO> authorsDto = new ArrayList<AuthorDTO>();
		for (Author a : list) {
			authorsDto.add(convertToDTO(a));
		}
		return authorsDto;
	}

}
