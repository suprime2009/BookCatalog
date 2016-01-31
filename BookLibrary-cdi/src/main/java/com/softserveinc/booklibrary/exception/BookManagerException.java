package com.softserveinc.booklibrary.exception;

/**
 * The class {@code BookManagerException} is a subclass of {@link BookCatalogException} 
 * that thrown to indicate that conditions of business logic for {@code Book} component
 * violated. This exception can be thrown from {@link BookManager}.
 *
 */
public class BookManagerException extends BookCatalogException {

	public BookManagerException() {
		super();
	}

	public BookManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	public BookManagerException(String message) {
		super(message);
	}

	public BookManagerException(Throwable cause) {
		super(cause);
	}
}
