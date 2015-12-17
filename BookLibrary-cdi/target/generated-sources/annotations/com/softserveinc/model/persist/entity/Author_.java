package com.softserveinc.model.persist.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Author.class)
public abstract class Author_ {

	public static volatile SingularAttribute<Author, String> firstName;
	public static volatile SingularAttribute<Author, Date> createdDate;
	public static volatile SetAttribute<Author, Book> books;
	public static volatile SingularAttribute<Author, String> idAuthor;
	public static volatile SingularAttribute<Author, String> secondName;

}

