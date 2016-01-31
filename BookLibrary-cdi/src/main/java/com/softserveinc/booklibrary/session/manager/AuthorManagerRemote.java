package com.softserveinc.booklibrary.session.manager;

import javax.ejb.Remote;

@Remote(AuthorManagerRemote.class)
public interface AuthorManagerRemote extends IManagerAuthor{

}
