package com.softserveinc.action.managebook;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.softserveinc.model.persist.entity.AuthorWrapper;
import com.softserveinc.model.persist.entity.BookWrapper;

@ManagedBean
@ApplicationScoped
public class DataTableConstants {
	
	private BookWrapper bookWrapperUI;
	private AuthorWrapper authorWrapperUI;
	
	
	public DataTableConstants() {
		super();
		this.bookWrapperUI = BookWrapper.BOOK_UI_WRAPPER;
		this.authorWrapperUI = AuthorWrapper.AUTHOR_UI_WRAPPER;
	}
	public BookWrapper getBookWrapperUI() {
		return bookWrapperUI;
	}
	public AuthorWrapper getAuthorWrapperUI() {
		return authorWrapperUI;
	}
	
	

}
