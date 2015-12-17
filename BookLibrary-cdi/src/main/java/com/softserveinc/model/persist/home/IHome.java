package com.softserveinc.model.persist.home;

import java.util.List;

/**
 * Interface that describes basic CRUD operations for Home methods
 * for each entity.
 * 
 * @param <T> Entity
 */
public interface IHome<T> {
	
	/**
	 * Method creates a record in a database.
	 * 
	 * @param object to add to database
	 */
	T create(T object);
	
	/**
	 * Method updates a record in database.
	 * 
	 * @param object to update in database
	 */
	void update(T object);
	
	/**
	 * Method deletes a record from database.
	 * 
	 * @param object to delete record from database
	 */
	void delete(T object);
	
	/**
	 * Method gets all records from database table.
	 * 
	 * @return list of entities
	 */
	List<T> findAll();
	
	/**
	 * Method finds a record in database table by id.
	 * 
	 * @param id of entity
	 * @return single entity object
	 */
	T findByID(String id);
	
}