package com.softserveinc.booklibrary.rest.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.softserveinc.booklibrary.rest.util.JsonFieldsHolder;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReviewDTO implements Serializable, JsonFieldsHolder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7197950692820470317L;

	private String idReview;
	private String comment;
	private String commenterName;
	private String idBook;
	private Integer rating;

	@JsonCreator
	public ReviewDTO(@JsonProperty(REVIEW_ID) String idReview, @JsonProperty(COMMENT) String comment,
			@JsonProperty(COMMENTER_NAME) String commenterName, @JsonProperty(BOOK_ID) String idBook,
			@JsonProperty(RATING) Integer rating) {
		super();
		this.idReview = idReview;
		this.comment = comment;
		this.commenterName = commenterName;
		this.idBook = idBook;
		this.rating = rating;
	}

	@JsonGetter(REVIEW_ID)
	public String getIdReview() {
		return idReview;
	}

	@JsonGetter(COMMENT)
	public String getComment() {
		return comment;
	}

	@JsonSetter(COMMENT)
	public void setComment(String comment) {
		this.comment = comment;
	}

	@JsonGetter(COMMENTER_NAME)
	public String getCommenterName() {
		return commenterName;
	}

	@JsonSetter(COMMENTER_NAME)
	public void setCommenterName(String commenterName) {
		this.commenterName = commenterName;
	}

	@JsonGetter(BOOK_ID)
	public String getIdBook() {
		return idBook;
	}
	
	@JsonSetter(BOOK_ID)
	public void setIdBook(String idBook) {
		this.idBook = idBook;
	}

	@JsonGetter(RATING)
	public Integer getRating() {
		return rating;
	}

	@JsonSetter(RATING)
	public void setRating(Integer rating) {
		this.rating = rating;
	}

}
