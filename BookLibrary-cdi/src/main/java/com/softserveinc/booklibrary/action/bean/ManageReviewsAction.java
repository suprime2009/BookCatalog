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

import com.softserveinc.booklibrary.action.helper.MostPopularBooksDTO;
import com.softserveinc.booklibrary.action.helper.ReviewRatingFieldsEnum;
import com.softserveinc.booklibrary.model.entity.Book;
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

	private List<List<MostPopularBooksDTO>> topRatedBooks;

	public ManageReviewsAction() {

	}

	@EJB
	private ReviewFacadeLocal reviewFacade;

	@EJB
	private BookFacadeLocal bookFacade;

	public List<List<MostPopularBooksDTO>> getTopRatedBooks() {
		if (topRatedBooks == null) {
			topRatedBooks = new ArrayList<List<MostPopularBooksDTO>>();
			topRatedBooks.add(getDataForLatestBooks());
			topRatedBooks.add(getDataForTopRatedBooks());

		}
		return topRatedBooks;
	}

	public List<MostPopularBooksDTO> getDataForLatestBooks() {
		List<Book> books = bookFacade.findMostPopularLatelyAddedBooks(10);
		List<MostPopularBooksDTO> listDto = new ArrayList<MostPopularBooksDTO>();
		for (Book b : books) {
			MostPopularBooksDTO dto = new MostPopularBooksDTO(b, reviewFacade.findAverageRatingForBook(b),
					reviewFacade.findCountReviewForBook(b), reviewFacade.findLatestReviewForBook(b));
			listDto.add(dto);
		}
		return listDto;
	}

	public List<MostPopularBooksDTO> getDataForTopRatedBooks() {
		List<Book> books = bookFacade.findMostPopularBooks(10);
		List<MostPopularBooksDTO> listDto = new ArrayList<MostPopularBooksDTO>();
		for (Book b : books) {
			MostPopularBooksDTO dto = new MostPopularBooksDTO(b, reviewFacade.findAverageRatingForBook(b),
					reviewFacade.findCountReviewForBook(b), reviewFacade.findLatestReviewForBook(b));
			listDto.add(dto);
		}
		return listDto;
	}

	public Integer getCountBooksByrating(Integer rating) {
		return bookFacade.findCountBooksByRating(rating);
	}

	public ReviewRatingFieldsEnum[] getRatingEnum() {
		return ReviewRatingFieldsEnum.values();
	}
}
