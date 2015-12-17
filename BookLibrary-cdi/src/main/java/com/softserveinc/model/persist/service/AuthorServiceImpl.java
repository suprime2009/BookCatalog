package com.softserveinc.model.persist.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.home.AuthorHomeLocal;


@Stateless
public class AuthorServiceImpl implements AuthorService{
	
//	@Inject
//	Logger log;
	Logger log = LoggerFactory.getLogger(AuthorServiceImpl.class);
	
	
	private AuthorHomeLocal authorHomeLocal;
	
	public AuthorHomeLocal getAuthorHomeLocal() {
		return authorHomeLocal;
	}

	@EJB
	public void setAuthorHomeLocal(AuthorHomeLocal authorHomeLocal) {
		this.authorHomeLocal = authorHomeLocal;
		log.info("DAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		log.info("DAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		log.info("DAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		log.info("DAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		log.info("DAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
	}
	
	public List<Author> findAll() {
		return getAuthorHomeLocal().findAll();
	}

}
