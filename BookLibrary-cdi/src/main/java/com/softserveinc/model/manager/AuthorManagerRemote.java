package com.softserveinc.model.manager;

import javax.ejb.Remote;

@Remote(AuthorManagerRemote.class)
public interface AuthorManagerRemote extends IManagerAuthor{

}
