package com.softserveinc.model.persist.backing;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


import com.softserveinc.model.persist.entity.Author;
import com.softserveinc.model.persist.service.AuthorService;

@ManagedBean
@RequestScoped
public class HomeManagedBean {


	private AuthorService userService;

	@EJB
	public void setUserService(AuthorService userService) {
		this.userService = userService;

	}

	private Author user;
	private List<Author> list;

	public HomeManagedBean() {

	}

	@PostConstruct
	public void init() {

		list = userService.findAll();
	}

	public Author getUser() {
		return user;
	}

	public void setUser(Author user) {
		this.user = user;
	}

	public List<Author> getList() {
		return list;
	}

	public void setList(List<Author> list) {
		this.list = list;
	}
}
