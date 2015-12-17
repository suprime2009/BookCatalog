package com.softserveinc.model.persist.facade;

import java.util.List;

import javax.ejb.Local;

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;

@Local
public interface BookFacadeLocal extends IBookFacade{
	
}
