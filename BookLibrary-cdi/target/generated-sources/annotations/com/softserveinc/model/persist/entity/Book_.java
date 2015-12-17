package com.softserveinc.model.persist.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Book.class)
public abstract class Book_ {

	public static volatile SingularAttribute<Book, Date> createdDate;
	public static volatile SingularAttribute<Book, String> isbn;
	public static volatile SingularAttribute<Book, String> idBook;
	public static volatile SingularAttribute<Book, String> publisher;
	public static volatile SingularAttribute<Book, Integer> yearPublished;
	public static volatile SingularAttribute<Book, String> bookName;
	public static volatile SetAttribute<Book, Author> authors;

}

