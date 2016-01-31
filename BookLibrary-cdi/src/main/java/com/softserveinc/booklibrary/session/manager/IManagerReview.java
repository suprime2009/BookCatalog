package com.softserveinc.booklibrary.session.manager;

import com.softserveinc.booklibrary.exception.ReviewManagerException;
import com.softserveinc.booklibrary.model.entity.Review;


/**
 * This interface include methods, that describe business logic for write
 * operations for {@link Review} entity.
 */
public interface IManagerReview {

	/**
	 * The method provide create operation for {@link Review} entity. Methods
	 * gets in parameter {@code review} entity that should be created in
	 * dataBase. Method provides validation of this entity.
	 * <p>
	 * Method throw {@link ReviewManagerException} if: passed entity is null or
	 * if review has present id number of if field book is null, and if fields
	 * commenterName, comment, rating don't meet requirements.
	 * <p>
	 * if method finish successfully, review will be passed to home method and
	 * persisted in database.
	 * 
	 * @param review
	 *            entity
	 * @throws ReviewManagerException
	 *             exception
	 */
	public void createReview(Review review) throws ReviewManagerException;

	/**
	 * The method provide update operation for {@link Review} entity. Methods
	 * gets in parameter {@code review} entity that should be updated in
	 * dataBase. Method provides validation of this entity.
	 * <p>
	 * Method throw {@link ReviewManagerException} if: passed entity is null or
	 * if review hasn't present id number of if field book is null, and if
	 * fields commenterName, comment, rating don't meet requirements.
	 * <p>
	 * if method finish successfully, review will be passed to home method and
	 * merged in database.
	 * 
	 * @param review
	 *            entity
	 * @throws ReviewManagerException
	 *             exception
	 */
	public void updateReview(Review review) throws ReviewManagerException;

	/**
	 * The method provide delete operation for {@link Review} entity. Methods gets
	 * in parameter {@code idReview} field. By that idReview should be found Review
	 * entity and deleted from database.
	 * <p>
	 * Method throw {@link ReviewManagerException} if: passed idReview is null, or 
	 * by passed idReview not found review.
	 * <p>
	 * if method finish successfully, review will be passed to home method and
	 * removed from database.
	 * 
	 * @param idReview
	 *            String
	 * @throws ReviewManagerException
	 *             exception
	 */
	public void deleteReview(String idReview) throws ReviewManagerException;

}
