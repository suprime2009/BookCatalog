package com.softserveinc.model.session.manager;

import java.util.List;
import java.util.Map;

import org.ajax4jsf.model.SequenceRange;
import org.richfaces.component.SortOrder;
import org.richfaces.model.ArrangeableState;

import com.softserveinc.exception.BookCatalogException;
import com.softserveinc.exception.BookManagerException;
import com.softserveinc.exception.ReviewManagerException;
import com.softserveinc.model.persist.entity.Book;
import com.softserveinc.model.persist.entity.OrderBy;
import com.softserveinc.model.session.util.DataTableSearchHolder;

/**
 * * IManagerBook is an interface that describes all business methods for Book
 * entity.
 *
 */
public interface IManagerBook {

	void setRatingForBooks(List<Book> list);

	void bulkDelete(List<Book> list) throws BookManagerException;

	void createBook(Book book) throws BookManagerException, BookCatalogException;

	void updateBook(Book book) throws BookManagerException;

	void deleteBook(Book book) throws BookManagerException;

}
