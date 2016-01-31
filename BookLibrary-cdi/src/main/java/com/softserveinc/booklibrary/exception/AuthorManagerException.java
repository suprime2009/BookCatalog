package com.softserveinc.booklibrary.exception;

/**
 * The class {@code AuthorManagerException} is a subclass of {@link BookCatalogException} 
 * that thrown to indicate that conditions of business logic for {@code Author} component
 * violated. This exception can be thrown from {@link AuthorManager}.
 *
 */
public class AuthorManagerException extends BookCatalogException {

	public AuthorManagerException() {
		super();
	}

	public AuthorManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthorManagerException(String message) {
		super(message);
	}

	public AuthorManagerException(Throwable cause) {
		super(cause);
	}
}
