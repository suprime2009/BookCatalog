package com.softserveinc.booklibrary.session.persist.home;

import java.util.List;

/**
 * Interface that describes basic CRUD operations for entities.
 * 
 * @param <T>
 *            entity class
 */
public interface IHome<T> {
	
	public static final String PERSISTANCE_UNIT_PRIMARY = "primary";


	/**
	 * The method creates a record in a database.
	 * 
	 * @param object
	 *            to add to database
	 */
	void create(T object);

	/**
	 * The method updates a record in database.
	 * 
	 * @param object
	 *            to update in database
	 */
	void update(T object);

	/**
	 * The method deletes single record from database table.
	 * 
	 * @param object
	 *            to delete record from database table.
	 */
	void delete(T object);

	/**
	 * The method gets all records from database table.
	 * 
	 * @return list of entities
	 */
	List<T> findAll();

	/**
	 * The method finds a record in database table by id.
	 * 
	 * @param id
	 *            of entity
	 * @return single entity object
	 */
	T findByID(String id);

}