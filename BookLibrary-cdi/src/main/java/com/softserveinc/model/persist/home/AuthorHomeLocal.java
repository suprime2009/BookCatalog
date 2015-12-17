package com.softserveinc.model.persist.home;



import javax.ejb.Local;
import com.softserveinc.model.persist.entity.Author;

@Local(AuthorHomeLocal.class)
public interface AuthorHomeLocal extends IHome<Author>{
	
}
