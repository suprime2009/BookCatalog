package com.softserveinc.booklibrary.action.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.booklibrary.action.helper.BookUIWrapper;
import com.softserveinc.booklibrary.action.helper.ReviewRatingFieldsEnum;
import com.softserveinc.booklibrary.model.entity.Author;
import com.softserveinc.booklibrary.model.entity.Book;
import com.softserveinc.booklibrary.model.entity.Review;
import com.softserveinc.booklibrary.session.persist.facade.AuthorFacadeLocal;
import com.softserveinc.booklibrary.session.persist.facade.BookFacadeLocal;
import com.softserveinc.booklibrary.session.persist.facade.ReviewFacadeLocal;
import com.softserveinc.booklibrary.session.util.holders.BookFieldHolder;
import com.softserveinc.booklibrary.session.util.holders.EntityFieldHolder;

@ManagedBean
@ViewScoped
public class ManageReviewsAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8167926850911171468L;

	private static Logger log = LoggerFactory.getLogger(ManageReviewsAction.class);

	private List<List<BookUIWrapper>> topRatedBooks;

	public ManageReviewsAction() {

	}

	@EJB
	private ReviewFacadeLocal reviewFacade;

	@EJB
	private BookFacadeLocal bookFacade;

	@EJB
	private AuthorFacadeLocal authorFacade;

	public List<List<BookUIWrapper>> getTopRatedBooks() {
		if (topRatedBooks == null) {
			topRatedBooks = new ArrayList<List<BookUIWrapper>>();
			topRatedBooks.add(getDataForLatestBooks());
			topRatedBooks.add(getDataForTopRatedBooks());

		}
		return topRatedBooks;
	}

	public List<BookUIWrapper> getDataForLatestBooks() {
		List<Book> books = bookFacade.findMostPopularLatelyAddedBooks(10);
		List<BookUIWrapper> listDto = new ArrayList<BookUIWrapper>();
		for (Book b : books) {
			BookUIWrapper wrap = new BookUIWrapper(b, reviewFacade.findAverageRatingForBook(b),
					reviewFacade.findCountReviewForBook(b), reviewFacade.findLatestReviewForBook(b));
			listDto.add(wrap);
		}
		return listDto;
	}

	public List<BookUIWrapper> getDataForTopRatedBooks() {
		List<Book> books = bookFacade.findMostPopularBooks(10);
		List<BookUIWrapper> listDto = new ArrayList<BookUIWrapper>();
		for (Book b : books) {
			BookUIWrapper wrap = new BookUIWrapper(b, reviewFacade.findAverageRatingForBook(b),
					reviewFacade.findCountReviewForBook(b), reviewFacade.findLatestReviewForBook(b));
			listDto.add(wrap);
		}
		return listDto;
	}

	public Integer getCountBooksByrating(Integer rating) {
		return bookFacade.findCountBooksByRating(rating);
	}

	public ReviewRatingFieldsEnum[] getRatingEnum() {
		return ReviewRatingFieldsEnum.values();
	}

	public List<Author> getLatestAddedAuthors() {
		return authorFacade.findLatestAddedAuthors(5);
	}

	public List<Book> getLatestAddedBooks() {
		return bookFacade.findLatestAddedBooks(5);
	}

	public List<Review> getLatestAddedReviews() {
		return reviewFacade.findLatestAddedReviews(5);
	}

}
