package com.softserveinc.model.persist.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Review.class)
public abstract class Review_ {

	public static volatile SingularAttribute<Review, Date> createdDate;
	public static volatile SingularAttribute<Review, String> commenterName;
	public static volatile SingularAttribute<Review, Book> book;
	public static volatile SingularAttribute<Review, Integer> rating;
	public static volatile SingularAttribute<Review, String> idreview;
	public static volatile SingularAttribute<Review, String> comment;

}

