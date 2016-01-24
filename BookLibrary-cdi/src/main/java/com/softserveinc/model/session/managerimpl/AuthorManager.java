package com.softserveinc.model.session.managerimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.exception.AuthorManagerException;
import com.softserveinc.exception.BookCatalogException;
import com.softserveinc.exception.ReviewManagerException;
import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Review;
import com.softserveinc.model.persist.facade.AuthorFacadeLocal;
import com.softserveinc.model.persist.home.AuthorHomeLocal;
import com.softserveinc.model.session.manager.AuthorManagerLocal;
import com.softserveinc.model.session.manager.AuthorManagerRemote;

@Stateless
public class AuthorManager implements AuthorManagerLocal, AuthorManagerRemote {

	private static Logger log = LoggerFactory.getLogger(AuthorManager.class);

	@EJB
	AuthorHomeLocal authorHome;

	@EJB
	AuthorFacadeLocal authorFacadeLocal;

	@Override
	public void createAuthor(Author author) throws AuthorManagerException, BookCatalogException {
		long startMethodTime = System.currentTimeMillis();
		log.debug("Method starts. Author to create ={}", author);
		String errorMessage = "";
		if (author == null) {
			errorMessage = "Passed author to create is null.";
			log.error(errorMessage);
			throw new AuthorManagerException(errorMessage);
		}
		validateAuthor(author);
		authorHome.create(author);
		if (author.getIdAuthor() == null) {
			errorMessage = "Creation of Author is not successful. Unexpected exception.";
			log.error(errorMessage);
			throw new BookCatalogException(errorMessage);
		}
		long endMethodTime = System.currentTimeMillis();
		log.info("Method successfully finished. That took {} milliseconds. Author {} has been created.",
				(endMethodTime - startMethodTime), author);
	}

	@Override
	public void updateAuthor(Author author) throws AuthorManagerException {

		log.debug("The method starts. Author to update ={}", author);
		validateAuthor(author);
		authorHome.update(author);
		log.info("The method finished. Author {} has been successfully updated.", author);
	}

	@Override
	public void deleteAuthor(String idAuthor) throws AuthorManagerException {

		log.info("Method starts. The author id ={}", idAuthor);
		String errorMessage = "";
		if (idAuthor == null) {
			errorMessage = "Passed id cannot be null.";
			log.error(errorMessage);
			throw new AuthorManagerException(errorMessage);
		}
		Author author = authorFacadeLocal.findById(idAuthor);
		System.out.println(author);
		if (author == null) {
			errorMessage = String.format("The Author by id= %s hasn't found.", idAuthor);
			log.error(errorMessage);
			throw new AuthorManagerException(errorMessage);
		}
		System.out.println("before del");
		authorHome.delete(author);
		System.out.println("after del");
		log.info("Method finished. The Author {} has been deleted.", author);

	}

	@Override
	public void deleteAuthorWithNoBooks(String idAuthor) throws AuthorManagerException {
		log.debug("Method starts. The author id ={}", idAuthor);
		String errorMessage = "";
		if (idAuthor == null) {
			errorMessage = "Passed id cannot be null.";
			log.error(errorMessage);
			throw new AuthorManagerException(errorMessage);
		}
		Author author = authorFacadeLocal.findById(idAuthor);
		if (author == null) {
			errorMessage = String.format("The Author by id= %s hasn't found.", idAuthor);
			log.error(errorMessage);
			throw new AuthorManagerException(errorMessage);
		}
		if (author.getBooks() != null && !author.getBooks().isEmpty()) {
			errorMessage = String.format(
					"The Author: %s, %s has %s books. The author can not be removed until he is the author of books.",
					author.getFirstName(), author.getSecondName(), author.getBooks().size());
			log.error(errorMessage);
			throw new AuthorManagerException(errorMessage);
		}
		authorHome.delete(author);
		log.info("Method finished. The Author {} has been deleted.", author);

	}

	private void validateAuthor(Author author) throws AuthorManagerException {
		String firstName = author.getFirstName().trim();
		String secondName = author.getSecondName().trim();
		String errorMessage = "";
		if (firstName == null || firstName.length() < 2 || firstName.length() > 80) {
			errorMessage = "Author first name name can't be null. Author first name cannot be less then 2 and more than 80 characters.";
			log.error(errorMessage);
			throw new AuthorManagerException(errorMessage);
		}
		if (secondName == null || secondName.length() < 2 || secondName.length() > 80) {
			errorMessage = "Author second name name can't be null. Author second name cannot be less then 2 and more than 80 characters.";
			log.error(errorMessage);
			throw new AuthorManagerException(errorMessage);
		}
		String regex = "([A-Z][a-z]*)([\\s\\\'-][A-Z][a-z]*)*";
		Pattern pattern = Pattern.compile(regex);
		Matcher firstNameMacher = pattern.matcher(firstName);
		Matcher secondNameMacher = pattern.matcher(secondName);
		if (!firstNameMacher.matches()) {
			errorMessage = "Author first name not valid.";
			log.error(errorMessage);
			throw new AuthorManagerException(errorMessage);
		}
		if (!secondNameMacher.matches()) {
			errorMessage = "Author second name not valid.";
			log.error(errorMessage);
			throw new AuthorManagerException(errorMessage);
		}
	}

	@Override
	public void bulkDeleteAuthorsWithNoBooks(List<Author> list) throws AuthorManagerException {
		String errorMessage = "";
		if (list == null || list.isEmpty()) {
			errorMessage = "List Authors to delete is empty.";
			log.error(errorMessage);
			throw new AuthorManagerException(errorMessage);
		}
		List<Author> listAuthorsWithNoBooks = new ArrayList<Author>();
		for (Author a: list) {
			if (a.getBooks() == null || a.getBooks().isEmpty()) {
				listAuthorsWithNoBooks.add(a);
			}
		}
		authorHome.bulkRemove(listAuthorsWithNoBooks);
		
	}

}
