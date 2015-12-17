package com.softserveinc.model.persist.facade;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.entity.Book;


@Local(AuthorFacadeLocal.class)
public interface AuthorFacadeLocal extends IAuthorFacade {
	
}
