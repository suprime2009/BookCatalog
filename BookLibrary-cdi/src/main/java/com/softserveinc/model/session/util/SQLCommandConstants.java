package com.softserveinc.model.session.util;

/**
 * This interface is presentation of SQL commands is String format.
 * Interface may use in creation String SQL queries.
 *
 */
public interface SQLCommandConstants {
	
	public static final String SELECT ="SELECT ";
	public static final String FROM =" FROM ";
	public static final String WHERE =" WHERE ";
	public static final String JOIN =" JOIN ";
	public static final String LEFT_JOIN =" LEFT JOIN ";
	public static final String RIGHT_JOIN =" RIGHT JOIN ";
	public static final String AS ="AS";
	public static final String GROUP_BY =" GROUP BY ";
	public static final String ORDER_BY =" ORDER BY ";
	public static final String AND =" AND ";
	public static final String OR =" OR ";
	public static final String LIKE =" LIKE ";
	public static final String DESC =" DESC ";
	public static final String ASC =" ASC ";
	public static final String AVG =" AVG";
	public static final String COUNT =" COUNT";
	public static final String HAVING =" HAVING ";
	public static final String FLOOR =" FLOOR";
	public static final String FORMAT =" FORMAT";
	public static final String DISTINCT =" DISTINCT ";
	
	
	//Personal alias for Book, Author, Review and rating
	public static final String B =" b";
	public static final String A =" a";
	public static final String R =" r";
	public static final String RAT =" rat ";
	public static final String COMMA = ", ";
	
	
	//String templates
	public static final String LIKE_TEMPLATE = "LIKE '%s%%' ";
	public static final String AGREGATE_FUNC_TEMPLATE = " %s(%s.%s) ";
	public static final String AGREGATE_FUNC_DISTINCT_TEMPLATE = " %s( DISTINCT %s) ";
	public static final String FIELD_TEMPLATE = " %s.%s ";
	
	

}
