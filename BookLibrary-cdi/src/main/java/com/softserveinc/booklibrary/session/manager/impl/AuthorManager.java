package com.softserveinc.booklibrary.session.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.booklibrary.exception.AuthorManagerException;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.session.manager.AuthorManagerLocal;
import com.softserveinc.booklibrary.session.manager.AuthorManagerRemote;
import com.softserveinc.booklibrary.session.persist.facade.AuthorFacadeLocal;
import com.softserveinc.booklibrary.session.persist.home.AuthorHomeLocal;

/**
 * The {@code AuthorManager} class is an implementation of business logic for
 * write operations for {@link Author} entity
 */
@Stateless
public class AuthorManager implements AuthorManagerLocal, AuthorManagerRemote {

	private static Logger log = LoggerFactory.getLogger(AuthorManager.class);

	@EJB
	AuthorHomeLocal authorHome;

	@EJB
	AuthorFacadeLocal authorFacadeLocal;

	@PostConstruct
	private void postConstruct() {
		log.debug("Bean has been created.");
	}

	@PreDestroy
	private void preDestroy() {
		log.debug("Bean has been destroyed.");
	}

	@Override
	public void createAuthor(Author author) throws AuthorManagerException {
		long startMethodTime = System.currentTimeMillis();
		log.debug("Method starts. Author to create ={}", author);
		String errorMessage = "";
		if (author == null) {
			errorMessage = "Passed author to create is null.";
			log.error(errorMessage);
			throw new AuthorManagerException(errorMessage);
		}
		if (author.getIdAuthor() != null) {
			errorMessage = "Passed author to create has id number and can't be created.";
			log.error(errorMessage);
			throw new AuthorManagerException(errorMessage);
		}
		validateAuthor(author);
		authorHome.create(author);
		if (author.getIdAuthor() == null) {
			errorMessage = "Creation of Author is not successful. Unexpected exception.";
			log.error(errorMessage);
			throw new AuthorManagerException(errorMessage);
		}
		log.info("Method successfully finished. That took {} milliseconds. Author {} has been created.",
				(System.currentTimeMillis() - startMethodTime), author);
	}

	@Override
	public void updateAuthor(Author author) throws AuthorManagerException {
		String errorMessage = "";
		log.debug("The method starts. Author to update ={}", author);
		if (author.getIdAuthor() == null) {
			errorMessage = "Passed author has no id number.";
			log.error(errorMessage);
			throw new AuthorManagerException(errorMessage);
		}
		Author checkAuthor = authorFacadeLocal.findById(author.getIdAuthor());

		if (checkAuthor == null) {
			errorMessage = "Author {} hasn't been found in database.";
			log.error(errorMessage);
			throw new AuthorManagerException(errorMessage);
		}
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
		if (author == null) {
			errorMessage = String.format("The Author by id= %s hasn't found.", idAuthor);
			log.error(errorMessage);
			throw new AuthorManagerException(errorMessage);
		}
		authorHome.delete(author);
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

	@Override
	public void bulkDeleteAuthorsWithNoBooks(List<Author> list) throws AuthorManagerException {
		String errorMessage = "";
		if (list == null || list.isEmpty()) {
			errorMessage = "List Authors to delete is empty.";
			log.error(errorMessage);
			throw new AuthorManagerException(errorMessage);
		}
		List<String> listIdAuthors = new ArrayList<String>();
		for (Author a : list) {
			listIdAuthors.add(a.getIdAuthor());
		}
		list = authorFacadeLocal.findAuthorsByListId(listIdAuthors);
		for (Author a : list) {
			if (a.getBooks() != null && !a.getBooks().isEmpty()) {
				errorMessage = String.format("the author %s has books and can't be removed.", a);
				log.error(errorMessage);
				throw new AuthorManagerException(errorMessage);
			}
		}
		authorHome.bulkRemove(list);
		log.info("The method finished. {} authors has been deleted.", list.size());
	}

	/**
	 * This method provide checking <code>Author</code> instance variables in
	 * accordance with the business requirements. <code>Author</code> instance
	 * passed from UI component and should be created in system or updated. If
	 * any conditions do not meet the requirements for business logic for Author
	 * instance, method throw {@link AuthorManagerException}.
	 * 
	 * @param review
	 *            {@link Author} instance that should be checked.
	 * @throws AuthorManagerException
	 *             exception
	 */
	private void validateAuthor(Author author) throws AuthorManagerException {
		String firstName = author.getFirstName();
		String secondName = author.getSecondName();
		String errorMessage = "";
		if (firstName == null || firstName.length() < 2 || firstName.length() >= 80) {
			errorMessage = "Author first name name can't be null. Author first name cannot be less then 2 and more than 80 characters.";
			log.error(errorMessage);
			throw new AuthorManagerException(errorMessage);
		}
		if (secondName == null || secondName.length() < 2 || secondName.length() >= 80) {
			errorMessage = "Author second name name can't be null. Author second name cannot be less then 2 and more than 80 characters.";
			log.error(errorMessage);
			throw new AuthorManagerException(errorMessage);
		}
		firstName = firstName.trim();
		secondName = secondName.trim();
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

}
