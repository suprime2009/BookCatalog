package com.softserveinc.booklibrary.exception;

/**
 * The class {@code BookCatalogException} is a subclass of {@link Exception} is a global
 * exception for Book Catalog application. This exception can be thrown to indicate 
 * that some global application conditions violated. 
 *
 */
public class BookCatalogException extends Exception {

	public BookCatalogException() {
		super();
	}

	public BookCatalogException(String message, Throwable cause) {
		super(message, cause);
	}

	public BookCatalogException(String message) {
		super(message);
	}

	public BookCatalogException(Throwable cause) {
		super(cause);
	}
}
