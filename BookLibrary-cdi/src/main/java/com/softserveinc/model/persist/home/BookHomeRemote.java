package com.softserveinc.model.persist.home;

import javax.ejb.Remote;
import com.softserveinc.model.persist.entity.Book;

@Remote(BookHomeRemote.class)
public interface BookHomeRemote extends IHome<Book>{

}
