package com.softserveinc.model.persist.service;

import java.util.List;

import javax.ejb.Local;

import com.softserveinc.model.persist.entity.Author;

@Local
public interface AuthorService {
	
	List<Author> findAll();

}
