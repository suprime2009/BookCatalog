package com.softserveinc.model.persist.home;

import javax.ejb.Remote;
import com.softserveinc.model.persist.entity.Book;

/**
 * BookHomeRemote an interface extended from IHome interface and describes basic CRUD operation
 * for Book entity. This interface is remote and used in application only for testing.
 */
@Remote(BookHomeRemote.class)
public interface BookHomeRemote extends IHome<Book>{

}
