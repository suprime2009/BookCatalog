package com.softserveinc.model.persist.home;


import javax.ejb.Remote;
import com.softserveinc.model.persist.entity.Author;

@Remote(AuthorHomeRemote.class)
public interface AuthorHomeRemote extends IHome<Author>{
	
}
