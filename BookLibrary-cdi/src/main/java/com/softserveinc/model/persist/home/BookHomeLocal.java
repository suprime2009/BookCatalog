package com.softserveinc.model.persist.home;

import javax.ejb.Local;

import com.softserveinc.model.persist.entity.Book;

@Local
public interface BookHomeLocal extends IHome<Book>{

}
