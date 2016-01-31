package com.softserveinc.booklibrary.exception;

/**
 * The class {@code ReviewManagerException} is a subclass of {@link BookCatalogException} 
 * that thrown to indicate that conditions of business logic for {@code Review} component
 * violated. This exception can be thrown from {@link ReviewManager}.
 *
 */
public class ReviewManagerException extends BookCatalogException {

	public ReviewManagerException() {
		super();
	}

	public ReviewManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReviewManagerException(String message) {
		super(message);
	}

	public ReviewManagerException(Throwable cause) {
		super(cause);
	}
	
	

}
