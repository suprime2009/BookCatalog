package com.softserveinc.exception;

/**
 * The class {@code ReviewFacadeException} is a subclass of {@link BookCatalogException} 
 * that thrown to indicate that conditions of business logic on Facade for {@code Review} component
 * violated. This exception can be thrown from {@link ReviewFacade}.
 *
 */
public class ReviewFacadeException extends BookCatalogException {

	public ReviewFacadeException() {
		super();
	}

	public ReviewFacadeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReviewFacadeException(String message) {
		super(message);
	}

	public ReviewFacadeException(Throwable cause) {
		super(cause);
	}
	
}
