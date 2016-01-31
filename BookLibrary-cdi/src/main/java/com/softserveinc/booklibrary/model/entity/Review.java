package com.softserveinc.booklibrary.model.entity;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.Date;
import java.util.UUID;


/**
 * The persistent class for the review database table.
 * 
 */
@Entity
@Table(name = "review")
@NamedQueries({
@NamedQuery(name = Review.FIND_ALL_REVIEWS, query="SELECT r FROM Review r"),
@NamedQuery(name = Review.FIND_ALL_REVIEWS_BY_COMMENTER_NAME, query="SELECT r FROM Review r WHERE r.commenterName like :par"),
@NamedQuery(name = Review.FIND_AVERAGE_RATING_FOR_BOOK, query="SELECT ROUND(AVG(r.rating),2) FROM Review r WHERE r.book.idBook LIKE :par"),
@NamedQuery(name = Review.FIND_REVIEWS_BY_BOOK_ORDER_DESC, query="SELECT DISTINCT r FROM Review r WHERE  r.book.idBook LIKE :book ORDER BY r.createdDate DESC"),
@NamedQuery(name = Review.FIND_REVIEWS_BY_BOOK_ORDER_ASC, query="SELECT DISTINCT r FROM Review r WHERE  r.book.idBook LIKE :book ORDER BY r.createdDate ASC"),
@NamedQuery(name = Review.FIND_COUNT_REVIEWS_FOR_BOOK, query="SELECT  COUNT(DISTINCT r) FROM Review r WHERE r.book.idBook LIKE :par"),
})
public class Review implements Serializable {
	

	public static final String FIND_ALL_REVIEWS = "Review.findAll";
	public static final String FIND_ALL_REVIEWS_BY_COMMENTER_NAME = "Review.findAllReviewsByCommenter";
	public static final String FIND_AVERAGE_RATING_FOR_BOOK = "Review.findAverageRatingForBook";
	public static final String FIND_REVIEWS_BY_BOOK_ORDER_ASC = "Review.findReviewsForBookOrderAsc";
	public static final String FIND_REVIEWS_BY_BOOK_ORDER_DESC = "Review.findReviewsForBookOrderDesc";
	public static final String FIND_COUNT_REVIEWS_FOR_BOOK = "Review.findCountReviewForBook";

	@Id
	@Column(name="review_id")
	private String idreview;

	@Column(name="comment_")
	private String comment;

	@Column(name="commenter_name")
	private String commenterName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;

	private Integer rating;

	@ManyToOne(optional=false)
	@JoinColumn(name="book_id")
	@JsonBackReference
	private Book book;

	public Review() {
	}
	
	public Review(String comment, String commenterName, Integer rating, Book book) {
		super();
		this.comment = comment;
		this.commenterName = commenterName;
		this.rating = rating;
		this.book = book;
	}
	
	@PrePersist
	private void generateId() {
		this.idreview = UUID.randomUUID().toString();
		this.createdDate = new Date();
	}

	public String getIdreview() {
		return this.idreview;
	}
	
	public void setIdReview(String idReview) {
		this.idreview = idReview;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommenterName() {
		return this.commenterName;
	}

	public void setCommenterName(String commenterName) {
		this.commenterName = commenterName;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public Integer getRating() {
		return this.rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Book getBook() {
		return this.book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(idreview, comment, commenterName, createdDate, rating, book);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Review review = (Review) obj;
		return Objects.equal(idreview, review.idreview) 
				&& Objects.equal(comment, review.comment)
				&& Objects.equal(commenterName, review.commenterName) 
				&& Objects.equal(createdDate, review.createdDate)
				&& Objects.equal(rating, review.rating) 
				&& Objects.equal(book, review.book);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).omitNullValues()
				.add("idreview", idreview)
				.add("comment", comment)
				.add("commenterName", commenterName)
				.add("createdDate", createdDate)
				.add("rating", rating)
				.add("book", book)
				.toString();
	}
}