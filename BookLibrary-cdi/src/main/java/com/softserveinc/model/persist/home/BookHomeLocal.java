package com.softserveinc.model.persist.home;

import javax.ejb.Local;

import com.softserveinc.model.persist.entity.Book;

/**
 * BookHomeLocal an interface extended from IHome interface and describes basic CRUD operation
 * for Book entity. This interface is local and used in application as main.
 *
 */
@Local
public interface BookHomeLocal extends IHome<Book>{

}
