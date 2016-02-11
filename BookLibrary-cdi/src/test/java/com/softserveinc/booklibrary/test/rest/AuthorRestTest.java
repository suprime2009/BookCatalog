package com.softserveinc.booklibrary.test.rest;

import static org.junit.Assert.*;


import org.testng.annotations.Test;

import com.softserveinc.booklibrary.exception.BookCatalogException;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.rest.dto.AuthorDTO;
import com.softserveinc.model.util.BaseTest;

public class AuthorRestTest extends BaseTest {
	
	@Test
	public void test() {
		try {
			AuthorDTO author = authorClient.findById("a1");
			assertEquals(author.getFirstName(), "Raul");
			assertEquals(author.getSecondName(), "Garsia");
			assertEquals(author.getIdAuthor(),"a1");

		} catch (BookCatalogException e) {
			e.printStackTrace();
		}
	}



}
